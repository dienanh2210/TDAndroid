package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
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
    boolean isFavourite;
    private CourseDetail mCourseDetail;

    TabCourseFragment tabCourseFragment;
    TabCommentFragment tabCommentFragment;
    String url = "";
    String[] strCourseType = new String[]{"片道", "往復", "1周"};
    int indexTab;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        // testAPI();
        //     mCourseID = mActivity.getmCourseID();
        mCourseID = getArguments().getInt(CourseListActivity.COURSE_DETAIL_ID);

        GetCourseDataAPI.getCourseData(mCourseID, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
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
                    txtDistance.setText(model.getDistance());
                    txtAvageSpeed.setText(model.getAveragePace());
                    txtElevation.setText(model.getElevation());
                    txtDate.setText(model.getDisplayDate());
                }
            }

            @Override
            public void onError(VolleyError error) {

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


}
