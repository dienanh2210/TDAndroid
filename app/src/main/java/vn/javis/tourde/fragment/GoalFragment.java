package vn.javis.tourde.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.services.TourDeApplication;

public class GoalFragment extends BaseFragment {

    @BindView(R.id.take_photo)
    Button takePhoto;
    @BindView(R.id.btn_to_finish)
    Button btnToFinish;
    int spotID;
    int mCourseId;
    String imgUrl;
    String title;
    CourseListActivity mActivity;

    public static GoalFragment newInstance(View.OnClickListener listener) {
        GoalFragment fragment = new GoalFragment();
        //  fragment.listener = (RenewPasswordPageFragment.OnFragmentInteractionListener) listener;
        return fragment;
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {

        return inflater.inflate(R.layout.goal_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mActivity = (CourseListActivity) getActivity();
        final String avage_speed = getArguments().getString(CourseListActivity.AVARAGE_SPEED);
        final String time_finish = getArguments().getString(CourseListActivity.TIME_FINISH);
        spotID = getArguments().getInt(CourseListActivity.SPOT_ID);
        mCourseId = mActivity.getmCourseID();
        imgUrl = getArguments().getString(CourseListActivity.STAMP_IMAGE);
        title = getArguments().getString(CourseListActivity.STAMP_TITLE);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TourDeApplication.getInstance().trackEvent("tap_goal_logging_course_id="+mCourseId,"tap","tap_goal_logging_course_id="+mCourseId);
                mActivity.showTakePhoto(spotID);
            }
        });
        btnToFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showCourseFinish(avage_speed,time_finish);
            }
        });
    }
}

