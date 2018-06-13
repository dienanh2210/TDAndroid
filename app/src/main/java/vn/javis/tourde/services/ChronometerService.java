package vn.javis.tourde.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Chronometer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by QuanPham on 6/13/18.
 */


public class ChronometerService extends IntentService {

    public static final String str_receiver_chronometer = "receiver.chronometer";
    public static final String KEY_TIME_STRING = "time_string";
    public static final String KEY_TIME = "time";
    public static final String KEY_TIME_BASE = "time_base";
    private Chronometer mChronometer;
    private long time;
    private static final int LOC_API_CALL_INTERVAL = 1 * 1000;
    private Timer timer;

    public ChronometerService() {
        super("ChronometerService");
    }


//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        return null;
//    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        long time = intent.getLongExtra(KEY_TIME_BASE, SystemClock.elapsedRealtime());
        mChronometer.setBase(time);
        mChronometer.start();
        startTimer();
        Log.i("onStartCommand", "" + time);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChronometer = new Chronometer(this);
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        long time = intent.getLongExtra(KEY_TIME_BASE, SystemClock.elapsedRealtime());
//        mChronometer.setBase(time);
//        mChronometer.start();
//        startTimer();
//        Log.i("onStartCommand", "" + time);
//        return START_STICKY;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy", "------>Chronometer");
//        mChronometer.stop();
//        stopTimer();
    }


    //Timer related functions

    private void startTimer() {
        if (timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                time = SystemClock.elapsedRealtime() - mChronometer.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                Log.i("Chronometer_Service", t);
                Intent intent = new Intent(str_receiver_chronometer);
                intent.putExtra(KEY_TIME_STRING, t);
                intent.putExtra(KEY_TIME, time);
                sendBroadcast(intent);
            }
        }, 5, LOC_API_CALL_INTERVAL);
    }

    private void stopTimer() {

        if (null != timer) {
            timer.cancel();
            timer = null;
        }
    }

}
