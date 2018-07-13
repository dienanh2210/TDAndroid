package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.fragment.BaseFragment;

public class CountDownTimesFragment extends BaseFragment {

    CourseListActivity mAcitivity;
    private TextView countdownText;
    private CountDownTimer countDownTimer;
    private boolean isCountDownTimer = true;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAcitivity = (CourseListActivity) getActivity();

        countdownText = view.findViewById(R.id.countDownText);

    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.count_down_page, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        countDownTimer = new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownText.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mAcitivity.showFragmentTabLayoutRunning();
            }
        };
        if(isCountDownTimer)
        countDownTimer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        isCountDownTimer = false;
//        countDownTimer.cancel();
    }
}
