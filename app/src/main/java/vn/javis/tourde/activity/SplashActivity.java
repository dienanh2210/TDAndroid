package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import vn.javis.tourde.R;

public class SplashActivity extends BaseActivity {
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        startRun();
    }

    private void startRun() {
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( SplashActivity.this, MainActivity.class );
                startActivity( intent );
                finish();
            }
        };
        handler.postDelayed( runnable, 2000 );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks( runnable );
    }
}
