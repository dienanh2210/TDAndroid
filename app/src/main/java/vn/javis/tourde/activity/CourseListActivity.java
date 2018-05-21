package vn.javis.tourde.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.fragment.BadgeCollectionFragment;
import vn.javis.tourde.fragment.CourseDetailFragment;
import vn.javis.tourde.fragment.CourseDetailSpotImagesFragment;
import vn.javis.tourde.fragment.CourseDriveFragment;
import vn.javis.tourde.fragment.CourseListFragment;
import vn.javis.tourde.R;
import vn.javis.tourde.fragment.FragmentTabLayoutMyCourse;
import vn.javis.tourde.fragment.PostCommentFragment;
import vn.javis.tourde.fragment.TakePhotoFragment;
import vn.javis.tourde.services.GoogleService;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.fragment.CountDownTimesFragment;

/**
 * Created by admin on 3/23/2018.
 */

public class CourseListActivity extends AppCompatActivity implements ServiceCallback {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public int getmCourseID() {
        return mCourseID;
    }

    private int mCourseID;
    private int mSpotID;
    public static final int MY_CAMERA_PERMISSION_CODE = 100;
    Intent intentGPS;
    private static final int REQUEST_PERMISSIONS = 50;
    boolean boolean_permission;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Double latitude, longitude;
    Geocoder geocoder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        ListCourseAPI api = new ListCourseAPI(this);
        setHearder();
        fetchData();
        geocoder = new Geocoder(this, Locale.getDefault());
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        medit = mPref.edit();
        fn_permission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setHearder() {
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }

    }

    private void fetchData() {
        Intent intent = getIntent();
        boolean searching = "true".equals(intent.getStringExtra("searching"));
        HashMap<String, String> params = new HashMap<>();
        if (intent.getSerializableExtra("searchValue") instanceof HashMap) {
            params = (HashMap<String, String>) intent.getSerializableExtra("searchValue");
            if (params != null) {
                Log.i("params", params.toString());
            }
        }
        if (!searching) {
            ListCourseAPI.getJsonValues(this);
        } else {
            ListCourseAPI.getJsonValueSearch(params, this);
        }
    }

    public void showCourseListPage() {

        openPage(new CourseListFragment(), false);
    }

    public void ShowCourseDetail(int position) {
        mCourseID = ListCourseAPI.getInstance().getCourseIdByPosition(position);
        openPage(new CourseDetailFragment(), true);
    }

    public void showMyCourse() {
        openPage(new FragmentTabLayoutMyCourse(), true);
    }

    public void showBadgeCollection() {
        openPage(new BadgeCollectionFragment(), true);
    }

    public void ShowCountDown() {
        openPage(new CountDownTimesFragment(), true);
    }

    public void showCommentPost() {
        openPage(new PostCommentFragment(), true);

    }

    public void showCourseDrive() {
        openPage(new CourseDriveFragment(), true);
    }

    public void openPage(android.support.v4.app.Fragment fragment, boolean isBackStack) {
        android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isBackStack)
            tx.addToBackStack(null);
        tx.commitAllowingStateLoss();
    }

    @Override
    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
        ListCourseAPI.setAllCourses((JSONObject) response);
        showCourseListPage();
    }

    public void showTakePhoto() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
        } else {
            openPage(new TakePhotoFragment(), true);
        }
    }

    @Override
    public void onError(VolleyError error) {

    }

    public int getmSpotID() {
        return mSpotID;
    }

    public void showSpotImages(int spotID) {
        mSpotID = spotID;
        openPage(new CourseDetailSpotImagesFragment(), true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("onBackPressed", "true");
    }

    public void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(CourseListActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {

            } else {
                ActivityCompat.requestPermissions(CourseListActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION

                        },
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;
            turnOnGPS();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_CAMERA_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openPage(new TakePhotoFragment(), true);
                }
            }
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean_permission = true;
                    if (boolean_permission) {
                        if (mPref.getString("service", "").matches("")) {
                            medit.putString("service", "service").commit();
                            turnOnGPS();
                        } else {
                            Toast.makeText(getApplicationContext(), "Service is already running", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enable the gps", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }
        }

    }

    public void turnOnGPS() {
        intentGPS = new Intent(this, GoogleService.class);
        startService(intentGPS);
        Log.i("gps", "turn on");
    }

    public void turnOffGPS() {
        stopService(intentGPS);

    }




    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));

            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);

    }
}
