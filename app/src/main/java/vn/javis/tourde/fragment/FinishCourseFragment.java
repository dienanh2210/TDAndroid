package vn.javis.tourde.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

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
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.utils.BinaryConvert;
import vn.javis.tourde.utils.CameraUtils;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.view.CircleTransform;
import vn.javis.tourde.view.YourScrollableViewPager;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

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
    public boolean isFromMain;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        // testAPI();
        //     mCourseID = mActivity.getmCourseID();
        mCourseID = getArguments().getInt(CourseListActivity.COURSE_DETAIL_ID);
        final String avage_speed = getArguments().getString(CourseListActivity.AVARAGE_SPEED);
        final String time_finish = getArguments().getString(CourseListActivity.TIME_FINISH);
       showProgressDialog();
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
               hideProgressDialog();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });


        btnShowLoging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mActivity.showFragmentTabLayoutRunning();
                mActivity.popBackStack(FragmentTabLayoutRunning.class.getSimpleName());
            }
        });
        btnToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessDialog.showDialogConfirm(mActivity, "", "終了してコース画面へ 戻りますが宜しいですか？", new ProcessDialog.OnActionDialogClickOk() {
                    @Override
                    public void onOkClick() {
//                        mActivity.ShowCourseDetailById(mCourseID);
                        SharedPreferencesUtils.getInstance(mActivity).removeKey(FragmentTabLayoutRunning.KEY_SHARED_BASETIME);
                        SharedPreferencesUtils.getInstance(mActivity).removeKey(Constant.SAVED_COURSE_RUNNING);
                        SharedPreferencesUtils.getInstance(mActivity).removeKey(Constant.KEY_GOAL_PAGE);
                        mActivity.popBackStack(CourseDetailFragment.class.getSimpleName());
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
        photoFile = new File(mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + ".jpg");
        imageStoragePath = photoFile.getAbsolutePath();
        Bitmap bitmap = CameraUtils.loadBitmapFromView(viewCapture);
        CameraUtils.storeImage(bitmap, photoFile);
        if (CameraUtils.checkWriteToDiskPermissions(mActivity)) {
            ProcessDialog.showDialogConfirm(getContext(), "", "走行結果画面を\n" + "端末に保存しますか？", new ProcessDialog.OnActionDialogClickOk() {
                @Override
                public void onOkClick() {
                    CameraUtils.addImageToGallery(imageStoragePath, mActivity);
                }
            });

        } else {
            requestPermission();
        }

    }

    @OnClick(R.id.txt_btn_share)
    void shareSNS() {
        captureFinish();

        if (imageStoragePath != null) {
            final Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setType("image/*");

            Uri uri = Uri.fromFile(photoFile);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            mActivity.startActivity(Intent.createChooser(shareIntent, "Share image using"));
        } else
            Toast.makeText(mActivity, "Please save the result before ", Toast.LENGTH_SHORT).show();
        TourDeApplication.getInstance().trackEvent("tap_shared_course_id="+mCourseID,"tap","tap_shared_course_id="+mCourseID);
    }

    private void requestPermission() {
        Dexter.withActivity(mActivity)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            CameraUtils.addImageToGallery(imageStoragePath, mActivity);

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(mActivity);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}
