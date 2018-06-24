package vn.javis.tourde.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

    public static GoalFragment newInstance(int courseId, int idSpot, float speed, String time, String imgUrl, String title, String distance) {
        GoalFragment fragment = new GoalFragment();
        Bundle dataBundle = new Bundle();
        dataBundle.putInt(CourseListActivity.COURSE_DETAIL_ID, courseId);
        dataBundle.putString(CourseListActivity.AVARAGE_SPEED, String.valueOf(speed));
        dataBundle.putString(CourseListActivity.TIME_FINISH, time);
        dataBundle.putInt(CourseListActivity.SPOT_ID, idSpot);
        dataBundle.putString(CourseListActivity.STAMP_IMAGE, imgUrl);
        dataBundle.putString(CourseListActivity.STAMP_TITLE, title);
        dataBundle.putString(CourseListActivity.STAMP_DISTANCE, distance);
        Log.i("speed", String.valueOf(speed));
        fragment.setArguments(dataBundle);
        return fragment;
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {

        return inflater.inflate(R.layout.goal_fragment, container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtils.getInstance(getContext()).setBooleanValue(Constant.KEY_GOAL_PAGE, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        final String avage_speed = getArguments().getString(CourseListActivity.AVARAGE_SPEED, "");
        final String time_finish = getArguments().getString(CourseListActivity.TIME_FINISH, "");
        spotID = getArguments().getInt(CourseListActivity.SPOT_ID, 0);
        mCourseId = mActivity.getmCourseID();
        imgUrl = getArguments().getString(CourseListActivity.STAMP_IMAGE, "");
        title = getArguments().getString(CourseListActivity.STAMP_TITLE, "");
        time = getArguments().getString(CourseListActivity.TIME_FINISH, "");
        distance = getArguments().getString(CourseListActivity.STAMP_DISTANCE, "");
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
        if (!TextUtils.isEmpty(url))
            PicassoUtil.getSharedInstance(c).load(url).into(v);
        if (!TextUtils.isEmpty(s))
            textView.setText(s);
        v.startAnimation(anim_img);
        textView.startAnimation(anim_text);
    }
}

