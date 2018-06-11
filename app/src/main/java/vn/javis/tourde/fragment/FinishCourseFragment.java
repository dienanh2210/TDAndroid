package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.customlayout.TourDeTabLayout;
import vn.javis.tourde.model.CourseData;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.BinaryConvert;
import vn.javis.tourde.utils.CameraUtils;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.view.CircleTransform;
import vn.javis.tourde.view.YourScrollableViewPager;

public class FinishCourseFragment extends BaseFragment {
    private int mCourseID;

    private CourseListActivity mActivity;
    @BindView(R.id.title_detail)
    TextView txtTitle;
    @BindView(R.id.txt_avarage_speed)
    TextView txtAvageSpeed;
    @BindView(R.id.txt_distance)
    TextView txtDistance;
    @BindView(R.id.txt_elevation)
    TextView txtElevation;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.img_course_finish)
    ImageView imgCourse;
    @BindView(R.id.img_post_user_detail)
    ImageView imgPostUser;
    @BindView(R.id.txt_btn_blue)
    TextView btnShowLoging;
    @BindView(R.id.txt_btn_gray)
    TextView btnToDetail;
    @BindView(R.id.layout_to_capture)
    LinearLayout viewCapture;


    boolean isFavourite;
    private CourseDetail mCourseDetail;
    private static String imageStoragePath;
    TabCourseFragment tabCourseFragment;
    TabCommentFragment tabCommentFragment;
    String url = "";
    String[] strCourseType = new String[]{"片道", "往復", "1周"};
    int indexTab;
    File photoFile;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        // testAPI();
        //     mCourseID = mActivity.getmCourseID();
        mCourseID = getArguments().getInt(CourseListActivity.COURSE_DETAIL_ID);
        final String avage_speed = getArguments().getString(CourseListActivity.AVARAGE_SPEED);
        final String time_finish = getArguments().getString(CourseListActivity.TIME_FINISH);

        GetCourseDataAPI.getCourseData(mCourseID, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) {
                JSONObject jsonObject = (JSONObject) response;
                if (!jsonObject.has("error")) {
                    mCourseDetail = new CourseDetail((JSONObject) response);
                    CourseData model = mCourseDetail.getmCourseData();
                    if (!model.getTopImage().isEmpty()) {
                        PicassoUtil.getSharedInstance(activity)
                                .load(model.getTopImage())
                                .resize(0, 400).onlyScaleDown()
                                .into(imgCourse);
                        PicassoUtil.getSharedInstance(activity).load(model.getPostUserImage()).resize(0, 100).onlyScaleDown().transform(new CircleTransform()).into(imgPostUser);
                    }
                    txtTitle.setText(model.getTitle());
                    txtDistance.setText(model.getDistance() + "km");
                    txtAvageSpeed.setText(avage_speed + "km/h");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                    Date date = new Date();
                    txtElevation.setText(model.getElevation() + "m");
                    txtDate.setText(dateFormat.format(date));
                    txtTime.setText(time_finish);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });


        btnShowLoging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showFragmentTabLayoutRunning();
            }
        });
        btnToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessDialog.showDialogConfirm(mActivity, "", "終了してコース画面へ 戻りますが宜しいですか？", new ProcessDialog.OnActionDialogClickOk() {
                    @Override
                    public void onOkClick() {
                        mActivity.ShowCourseDetail(mCourseID);
                    }
                });
            }
        });
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.finish_course_fragment, container, false);
    }

    public CourseDetail getmCourseDetail() {
        return mCourseDetail;
    }

    @OnClick(R.id.btn_save_image)
    void captureFinish() {
        photoFile =  new File(mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + System.currentTimeMillis() + ".jpg");
        imageStoragePath = photoFile.getAbsolutePath();
        Bitmap bitmap = CameraUtils.loadBitmapFromView(viewCapture);
        CameraUtils.storeImage(bitmap,photoFile);
        MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), bitmap, imageStoragePath, "" + new Date().getTime());
        Toast.makeText(mActivity, "Save result Image is successfully! " , Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.txt_btn_share)
    void shareSNS() {
        if (imageStoragePath != null) {
            //setupFacebookShareIntent();
            final Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setType("image/*");

            Uri uri = Uri.fromFile(photoFile);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            mActivity.startActivity(Intent.createChooser(shareIntent, "Share image using"));
        } else
            Toast.makeText(mActivity, "Please save the result before ", Toast.LENGTH_SHORT).show();

    }

    public void setupFacebookShareIntent() {
        Uri uri = Uri.fromFile(photoFile);
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(uri)
                .build();
        ShareDialog.show(mActivity, linkContent);
    }
}
