package vn.javis.tourde.utils;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import vn.javis.tourde.R;

public class CountDownTimes extends AppCompatActivity {

    private TextView countdownText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_down_page);
        countdownText = findViewById(R.id.countDownText);
        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownText.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdownText.setText("Done!");
                //openpage();
            }
        }.start();

    }


}
