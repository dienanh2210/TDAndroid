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

import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import vn.javis.tourde.apiservice.CommentsAPI;
import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.fragment.BadgeCollectionFragment;
import vn.javis.tourde.fragment.CourseDetailFragment;
import vn.javis.tourde.fragment.CourseDetailSpotImagesFragment;
import vn.javis.tourde.fragment.CourseDriveFragment;
import vn.javis.tourde.fragment.CourseListFragment;
import vn.javis.tourde.R;
import vn.javis.tourde.fragment.FragmentTabLayoutMyCourse;
import vn.javis.tourde.fragment.FragmentTabLayoutRunning;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.fragment.PostCommentFragment;
import vn.javis.tourde.fragment.SearchCourseFragment;
import vn.javis.tourde.fragment.SpotFacilitiesFragment;
import vn.javis.tourde.fragment.TakePhotoFragment;
import vn.javis.tourde.services.GoogleService;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.fragment.CountDownTimesFragment;
import vn.javis.tourde.utils.ProcessDialog;

/**
 * Created by admin on 3/23/2018.
 */

public class CourseListActivity extends AppCompatActivity implements ServiceCallback {


    public static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final String COURSE_DETAIL_ID = "COURSE_ID";
    public static final String SPOT_ID = "SPOT_ID";
    private static final int REQUEST_PERMISSIONS = 50;


    public int getmCourseID() {
        return mCourseID;
    }

    private int mCourseID;
    private int mSpotID;

    public int getmSpotID() {
        return mSpotID;
    }
    Intent intentGPS;
    boolean boolean_permission;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Double latitude, longitude;
    Geocoder geocoder;
    Bundle dataBundle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        ListCourseAPI api = new ListCourseAPI(this);
        setHearder();
        // fetchData();
        dataBundle = new Bundle();
        showCourseListPage();
        geocoder = new Geocoder(this, Locale.getDefault());
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        medit = mPref.edit();
        fn_permission();

    }

    public void setmCourseID(int mCourseID) {
        this.mCourseID = mCourseID;
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

    public void showDialogWarning() {
        ProcessDialog.showDialogLogin(CourseListActivity.this, "", "この機能を利用するにはログインをお願いいたします", new ProcessDialog.OnActionDialogClickOk() {
            @Override
            public void onOkClick() {
                Intent intent = new Intent(CourseListActivity.this, LoginSNSActivity.class);
                startActivity(intent);
            }
        });
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
        dataBundle.putString("searching", "");
        openPage(new CourseListFragment(), false, false);
    }

    public void showCourseListPage(HashMap<String, String> parram) {
        dataBundle.putSerializable("params", parram);
        dataBundle.putString("searching", "searching");
        openPage(new CourseListFragment(), false, true);
    }

    public void ShowCourseDetail(int position) {
        mCourseID = ListCourseAPI.getInstance().getCourseIdByPosition(position);
        dataBundle.putInt(COURSE_DETAIL_ID, mCourseID);
        openPage(new CourseDetailFragment(), true, false, true);
    }

    public void ShowFavoriteCourseDetail(int courseId) {

        dataBundle.putInt(COURSE_DETAIL_ID, courseId);
        openPage(new CourseDetailFragment(), true, false);
    }

    public void showMyCourse() {
        openPage(new FragmentTabLayoutMyCourse(), true, false);
    }

    public void showBadgeCollection() {
        openPage(new BadgeCollectionFragment(), true, false);
    }

    public void ShowCountDown() {
        openPage(new CountDownTimesFragment(), false, false);
    }

    public void showCommentPost() {
        openPage(new PostCommentFragment(), true, false);
    }

    public void showCourseDrive() {
        openPage(new CourseDriveFragment(), true, false);
    }

    public void showSpotFacilitiesFragment(int spotID){
        mSpotID = spotID;
        dataBundle.putInt(SPOT_ID, mSpotID);
        openPage(new SpotFacilitiesFragment(), true, false);
    }
    public void showFragmentTabLayoutRunning() {
        openPage(new FragmentTabLayoutRunning(), true, false);
    }


    public void openPage(android.support.v4.app.Fragment fragment, boolean isBackStack, boolean isAnimation) {
        fragment.setArguments(dataBundle);
        android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        if (isAnimation)
            tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isBackStack)
            tx.addToBackStack(null);
        tx.commitAllowingStateLoss();
    }

    public void openPage(android.support.v4.app.Fragment fragment, boolean isBackStack, boolean isAnimation, boolean isBack) {
        fragment.setArguments(dataBundle);
        android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        if (isAnimation) {
            if (isBack)
                tx.setCustomAnimations(R.anim.pop_exit, R.anim.pop_enter);
            else
                tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        }

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

    public void showTakePhoto(int spotID) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
        } else {
            mSpotID = spotID;
            dataBundle.putInt(SPOT_ID, mSpotID);
            dataBundle.putInt(COURSE_DETAIL_ID,mCourseID);
            openPage(new TakePhotoFragment(), true, false);
        }
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public void onError(VolleyError error) {

    }

    public void showSpotImages(int spotID) {
        mSpotID = spotID;
        dataBundle.putInt(SPOT_ID, mSpotID);
        openPage(new CourseDetailSpotImagesFragment(), true, false);
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginSNSActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("onBackPressed", "true");
    }

    public void showSearchPage() {
        openPage(new SearchCourseFragment(), true, false);


    }
    public void showSpotFacilities() {
        openPage(new SpotFacilitiesFragment(), true, false);


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
                    openPage(new TakePhotoFragment(), true, false);
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
        if (!boolean_permission)
            fn_permission();
        else {
            intentGPS = new Intent(this, GoogleService.class);
            startService(intentGPS);
            Log.i("GPSLOG", "turn on \n");
        }
    }

    public void turnOffGPS() {
        stopService(intentGPS);

        Log.i("GPSLOG", "turn off \n");

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));
            Log.i("latutide", "" + latitude);
            Log.i("longitude", "" + longitude);

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
    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
