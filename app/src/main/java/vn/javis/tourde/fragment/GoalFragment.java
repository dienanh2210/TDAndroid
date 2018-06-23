package vn.javis.tourde.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.SharedPreferencesUtils;

import static com.facebook.FacebookSdk.getApplicationContext;

public class GoalFragment extends BaseFragment {

    @BindView(R.id.take_photo)
    Button takePhoto;
    @BindView(R.id.btn_to_finish)
    Button btnToFinish;
    @BindView(R.id.txtDesc)
    TextView txtView;
    Runnable runnable;
    Handler handler;
    ImageView imgView;
    int spotID;
    int mCourseId;
    String imgUrl;
    String title;
    String time;
    String distance;
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
        SharedPreferencesUtils.getInstance(getContext()).setBooleanValue(Constant.KEY_GOAL_PAGE, true);
        final String avage_speed = getArguments().getString(CourseListActivity.AVARAGE_SPEED);
        final String time_finish = getArguments().getString(CourseListActivity.TIME_FINISH);
        spotID = getArguments().getInt(CourseListActivity.SPOT_ID);
        mCourseId = mActivity.getmCourseID();
        imgUrl = getArguments().getString(CourseListActivity.STAMP_IMAGE);
        title = getArguments().getString(CourseListActivity.STAMP_TITLE);
        time = getArguments().getString(CourseListActivity.TIME_FINISH);
        distance = getArguments().getString(CourseListActivity.STAMP_DISTANCE);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TourDeApplication.getInstance().trackEvent("tap_goal_logging_course_id=" + mCourseId, "tap", "tap_goal_logging_course_id=" + mCourseId);
                mActivity.showTakePhoto(spotID,time,distance);
            }
        });
        btnToFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showCourseFinish(avage_speed, time_finish);
            }
        });
        imgView = view.findViewById(R.id.imgMain);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                if (imgView.getTag() == null) {
                    ImageViewAnimatedChange(getApplicationContext(), txtView, title, imgView, imgUrl);
                     //   ImageViewAnimatedChange(mActivity, txtView, "バッジを獲得！", imgView, imgUrl);
                    handler.postDelayed(runnable, 1000);

                }
            }

        };
        handler.postDelayed(runnable, 1000);

    }

    public static void ImageViewAnimatedChange(Context c, final TextView textView, final String s, final ImageView v, final int new_image) {

        final Animation anim_img = AnimationUtils.loadAnimation(c, R.anim.rotate_up);
        final Animation anim_text = AnimationUtils.loadAnimation(c, R.anim.rotate_up_in);

        v.setImageResource(new_image);
        v.setTag(new_image);
        textView.setText(s);
        v.startAnimation(anim_img);
        textView.startAnimation(anim_text);
    }

    public static void ImageViewAnimatedChange(Context c, final TextView textView, final String s, final ImageView v, final String url) {

        final Animation anim_img = AnimationUtils.loadAnimation(c, R.anim.rotate_up);
        final Animation anim_text = AnimationUtils.loadAnimation(c, R.anim.rotate_up_in);
        v.setTag(url);
        if (!url.isEmpty())
            PicassoUtil.getSharedInstance(c).load(url).into(v);
        if (!s.isEmpty())
            textView.setText(s);
        v.startAnimation(anim_img);
        textView.startAnimation(anim_text);
    }
}

