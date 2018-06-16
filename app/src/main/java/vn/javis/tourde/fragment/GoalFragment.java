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
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showTakePhoto(spotID);
            }
        });
        btnToFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showCourseFinish(avage_speed,time_finish);
            }
        });
        imgView = view.findViewById(R.id.imgMain);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                if (imgView.getTag() == null) {
                    ImageViewAnimatedChange( getApplicationContext(), txtView, "チェックポイント通過！", imgView, R.drawable.icon_check_star );
                    //    ImageViewAnimatedChange(mActivity, txtDesctwo, "バッジを獲得！", imgView, imgUrl);
                    handler.postDelayed( runnable, 1000 );

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
}

