package vn.javis.tourde.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.utils.DistanceLocation;

/**
 * Created by QuanPV
 */

public class GoogleService extends Service implements LocationListener {

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    Location location2;

    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 10000;
    public static String str_receiver = "tourde.service.receiver";
    public static String str_receiver_arrived = "tourde.service.receiver.arrived";
    Intent intent;

    private double latitude_des, longitude_des;
    int timeDelay = 5;
    private String filename = "logGPS.txt";
    ArrayList<vn.javis.tourde.model.Location> lstLocation = new ArrayList<>();
    ArrayList<vn.javis.tourde.model.Location> lstLocationArrived = new ArrayList<>();
    private static final double DISTANCE_ALLOW = 10000;

    public GoogleService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Nullable
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//       data=(String) intent.getExtras().get("data");
        latitude_des = intent.getDoubleExtra("ed_latitude", 0);
        longitude_des = intent.getDoubleExtra("ed_longitude", 0);
        lstLocation = (ArrayList<vn.javis.tourde.model.Location>) intent.getSerializableExtra("location");
        Log.i("onBind", "" + latitude_des + "-" + longitude_des + lstLocation.size());
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToGetLocation(), timeDelay, notify_interval);
        intent = new Intent(str_receiver);
//        fn_getlocation();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (!location.hasAccuracy()) {
            return;
        }
        if (location.getAccuracy() > 5) {

        }
        Log.i("onLocationChanged", location.getAccuracy() + "");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {


                        Log.i("GPSLOG: ", "latutide: " + location.getLatitude() + ", longitude: " + location.getLongitude());
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location, true);
                    }
                }

            }
            if (isGPSEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        Log.e("latitude", location.getLatitude() + "");
                        Log.e("longitude", location.getLongitude() + "");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location, false);
                    }
                }
            }


        }

    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    fn_getlocation();
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void fn_update(Location location, boolean isLocationNetWork) {
        intent.putExtra("location_network", isLocationNetWork);
        intent.putExtra("latutide", location.getLatitude() + "");
        intent.putExtra("longitude", location.getLongitude() + "");
        intent.putExtra("arrived", false);
        if (lstLocation.size() > 0) {
            Log.i("latutide", location.getLatitude() + "-" + lstLocation.get(0).getLatitude());
            Log.i("longitude", location.getLongitude() + "-" + lstLocation.get(0).getLongtitude());
        }
        lstLocationArrived.clear();
        //double distance1 = DistanceLocation.getDistance(location.getLatitude(), location.getLongitude(),lstLocation.get(0).getLatitude(), lstLocation.get(0).getLongtitude());
        Intent intent1 = new Intent(str_receiver_arrived);
        for (vn.javis.tourde.model.Location lct : lstLocation) {
            double distance = SphericalUtil.computeDistanceBetween(new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(lct.getLatitude(), lct.getLongtitude()));
            Log.i("GPS_218,lat", lct.getSpotID() + "-" + location.getLatitude() + "-" + lct.getLatitude() + ",longitude" + location.getLongitude() + "-" + lct.getLongtitude() + ",distance" + distance);
            if (distance <= DISTANCE_ALLOW) {
                if (!lstLocationArrived.contains(lct))
                    lstLocationArrived.add(lct);
                //   showNotification();

                //    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().commit();

                intent1.putExtra("arrived", lstLocationArrived);

                //    stopService(new Intent(GoogleService.this, GoogleService.class));
            }
        }
        if (!lstLocationArrived.isEmpty()) {
            sendBroadcast(intent1);
        }

        // intent.putExtra("arrived", lstLocationArrived);
        //   Log.i("distance", ""+distance + "--");
        sendBroadcast(intent);
//        stopService(new Intent(GoogleService.this, GoogleService.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(this, CourseListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle("Notification");
        builder.setContentText("You have come here");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
        builder.setVibrate(pattern);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        builder.setContentIntent(localPendingIntent);
        builder.setAutoCancel(true);
        builder.setContentInfo("You have come here!");
//        builder.build();

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, builder.build());

//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle("My notification")
//                .setContentText("Hello World!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("googleservice", intent.getStringExtra("test"));
            //   latitude = Double.valueOf(intent.getStringExtra("latutide"));
            //     longtitude = Double.valueOf(intent.getStringExtra("longitude"));
        }
    };

    private void writeToFile(String data) {
        try {
            File testFile = new File(this.getExternalFilesDir(null), filename);
            if (!testFile.exists())
                testFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(testFile, true /*append*/));
            writer.write(data);
            writer.close();
            MediaScannerConnection.scanFile(this,
                    new String[]{testFile.toString()},
                    null,
                    null);
        } catch (IOException e) {
            Log.e("ReadWriteFile", "Unable to write to the TestFile.txt file.");
        }


    }

}
