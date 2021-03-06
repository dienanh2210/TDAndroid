package vn.javis.tourde.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
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
import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.adapter.ListCheckInSpot;
import vn.javis.tourde.apiservice.ApplicationVersionAPI;
import vn.javis.tourde.apiservice.CommentsAPI;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.apiservice.MaintenanceAPI;
import vn.javis.tourde.fragment.BadgeCollectionFragment;
import vn.javis.tourde.fragment.BaseFragment;
import vn.javis.tourde.fragment.CheckPointFragment;
import vn.javis.tourde.fragment.CourseDetailFragment;
import vn.javis.tourde.fragment.CourseDetailSpotImagesFragment;
import vn.javis.tourde.fragment.CourseDriveFragment;
import vn.javis.tourde.fragment.CourseListFragment;
import vn.javis.tourde.R;
import vn.javis.tourde.fragment.FinishCourseFragment;
import vn.javis.tourde.fragment.FragmentTabLayoutMyCourse;
import vn.javis.tourde.fragment.FragmentTabLayoutRunning;
import vn.javis.tourde.fragment.GoalFragment;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.fragment.PostCommentFragment;
import vn.javis.tourde.fragment.SearchCourseFragment;
import vn.javis.tourde.fragment.SpotFacilitiesFragment;
import vn.javis.tourde.fragment.TakePhotoFragment;
import vn.javis.tourde.model.CheckAppVersion;
import vn.javis.tourde.model.CourseData;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.model.Location;
import vn.javis.tourde.model.MaintenanceStatus;
import vn.javis.tourde.model.SaveCourseRunning;
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.services.GoogleService;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.fragment.CountDownTimesFragment;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.ClassToJson;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.utils.TimeUtil;

import static vn.javis.tourde.fragment.FragmentTabLayoutRunning.KEY_SHARED_BASETIME;
import static vn.javis.tourde.fragment.FragmentTabLayoutRunning.newInstance;

/**
 * Created by admin on 3/23/2018.
 */

public class CourseListActivity extends BaseActivity {


    public static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final String COURSE_DETAIL_ID = "COURSE_ID";
    public static final String COURSE_DETAIL_INDEX_TAB = "COURSE_INDEX_TAB";
    public static final String STAMP_IMAGE = "stamp_img";
    public static final String STAMP_TITLE = "stamp_title";
    public static final String STAMP_DISTANCE = "stamp_distance";
    public static final String STAMP_GAIN = "stamp_gain";
    public static final String IS_FIRST_SPOT ="is_first_spot";
    public static final String AVARAGE_SPEED = "avarage_speed";
    public static final String TIME_FINISH = "time_finish";
    public static final String IS_CHECKING_SPOT = "is_checking_spot";

    public static final String SPOT_ID = "SPOT_ID";
    private static final int REQUEST_PERMISSIONS = 50;
    public static boolean isRunningBackground;

    public int getmCourseID() {
        return mCourseID;
    }

    private int mCourseID;
    private int mSpotID;
    private String mapUrl;
    private String route_url;
    private String timeFinish, distanceSpot;

    public int getmSpotID() {
        return mSpotID;
    }

    Intent intentGPS;

    boolean boolean_permission;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    double latitude = 0, longitude = 0;
    double latitudeNetWork = 0, longitudeNetWork = 0;
    boolean isCheckingSpot=false;
    Geocoder geocoder;
    Bundle dataBundle;

    @BindView(R.id.main_layout)
    View mLayout;

    public int typeBackPress;

    private CourseListFragment mCourseListFragment;
    private CourseDetailFragment mCourseDetailFragment;
    private CourseDriveFragment courseDriveFragment;
    private FinishCourseFragment finishCourseFragment;
    private GoalFragment goalFragment;
    private FragmentTabLayoutRunning fragmentTabLayoutRunning;
    private SearchCourseFragment searchCourseFragment;
    private CourseDetailSpotImagesFragment courseDetailSpotImagesFragment;
    private FragmentTabLayoutMyCourse fragmentTabLayoutMyCourse;
    private BadgeCollectionFragment badgeCollectionFragment;
    private SpotFacilitiesFragment spotFacilitiesFragment;
    private CheckPointFragment checkPointFragment;
    android.support.v4.app.FragmentTransaction frgTransaction;
    String token;
    boolean token_sns;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.course_list_view);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ListCourseAPI api = new ListCourseAPI(this);
        setHearder();
        // fetchData();
        dataBundle = new Bundle();
        showCourseListPage();
        geocoder = new Geocoder(this, Locale.getDefault());
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        medit = mPref.edit();
        token = SharedPreferencesUtils.getInstance(this).getStringValue(LoginUtils.TOKEN);
        token_sns = SharedPreferencesUtils.getInstance(this).getBooleanValue(LoginUtils.TOKEN_SNS);
        fn_permission();
        //    showCourseFinish();hiện
        if (!TextUtils.isEmpty(token)) {
            checkLogging();
        }
    }

    public boolean isBoolean_permission() {
        return boolean_permission;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }
    public String getRoute_url() {
        return route_url;
    }

    public void setRoute_url(String route_url) {
        this.route_url = route_url;
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

    public void checkLogging() {
        if (SharedPreferencesUtils.getInstance(this).getLongValue(KEY_SHARED_BASETIME) != 0) {
            ProcessDialog.showDialogCheckLogging(CourseListActivity.this, "", "走行（計測）を再開しますか？", new ProcessDialog.OnActionDialogClickOk() {
                @Override
                public void onOkClick() {

                    String savedString = SharedPreferencesUtils.getInstance(CourseListActivity.this).getStringValue(Constant.SAVED_COURSE_RUNNING);
                    Log.i("SAVED_COURSE_RUNNING", savedString);
                    if (!TextUtils.isEmpty(savedString)) {
                        SaveCourseRunning saveCourseRunning = new ClassToJson<SaveCourseRunning>().getClassFromJson(savedString, SaveCourseRunning.class);
                        mCourseID = saveCourseRunning.getCourseID();
                    }
                    openPage(CourseDetailFragment.newInstance(true), true, false);
                    /*if (SharedPreferencesUtils.getInstance(CourseListActivity.this).getBooleanValue(Constant.KEY_GOAL_PAGE)) {
//                        String savedString = SharedPreferencesUtils.getInstance(CourseListActivity.this).getStringValue(Constant.SAVED_COURSE_RUNNING);
//                        Log.i("SAVED_COURSE_RUNNING", savedString);
//                        if (!TextUtils.isEmpty(savedString)) {
//                            SaveCourseRunning saveCourseRunning = new ClassToJson<SaveCourseRunning>().getClassFromJson(savedString, SaveCourseRunning.class);
//                            mCourseID = saveCourseRunning.getCourseID();
//                            Log.i("getAvarageSpeed", saveCourseRunning.getAvarageSpeed() + "");
                            openPage(CourseDetailFragment.newInstance(true), true, false);
//                        }
                    } else {
                        openPage(CourseDetailFragment.newInstance(true), true, false);
                    }*/
                }
            }, new ProcessDialog.OnActionDialogClickCancel() {
                @Override
                public void onCancelClick() {
                   mCourseListFragment.onResume();
                }
            });
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

    public void showCourseListPage() {
        dataBundle.putString("searching", "");
        if (mCourseListFragment == null)
        mCourseListFragment = new CourseListFragment();
        openPage(mCourseListFragment, false, false);
    }

    public void showCourseListPage(HashMap<String, String> parram) {
        dataBundle.putSerializable("params", parram);
        dataBundle.putString("searching", "searching");
//        if (mCourseListFragment == null)
        mCourseListFragment = new CourseListFragment();
        openPage(mCourseListFragment, false, true);
    }


    public void ShowCourseDetailById(int id) {
        mCourseID = id;
        dataBundle.putInt(COURSE_DETAIL_ID, mCourseID);
        dataBundle.putInt(COURSE_DETAIL_INDEX_TAB, 0);
        isCheckingSpot=false;
        dataBundle.putBoolean(IS_CHECKING_SPOT, isCheckingSpot);
//        if (mCourseDetailFragment == null)
        mCourseDetailFragment = new CourseDetailFragment();
        openPage(mCourseDetailFragment, true, false);
    }

    public void ShowFavoriteCourseDetail(int courseId) {
        mCourseID = courseId;
        dataBundle.putInt(COURSE_DETAIL_ID, courseId);
        dataBundle.putInt(COURSE_DETAIL_INDEX_TAB, 0);
        isCheckingSpot=false;
        dataBundle.putBoolean(IS_CHECKING_SPOT, isCheckingSpot);
//        if (mCourseDetailFragment == null)
        mCourseDetailFragment = new CourseDetailFragment();
        openPage(mCourseDetailFragment, true, false, true);
    }

    public void showMyCourse() {
//        if (fragmentTabLayoutMyCourse == null)
        fragmentTabLayoutMyCourse = new FragmentTabLayoutMyCourse();
        openPage(fragmentTabLayoutMyCourse, true, false);
    }

    public void showBadgeCollection() {
//        if (badgeCollectionFragment == null)
        badgeCollectionFragment = new BadgeCollectionFragment();
        openPage(badgeCollectionFragment, true, false);
    }

    public void ShowCountDown() {
        openPage(new CountDownTimesFragment(), false, false);
    }

    public void showCommentPost(String tagBackStack) {
        PostCommentFragment postCommentFragment = new PostCommentFragment();
        dataBundle.putInt(COURSE_DETAIL_ID, mCourseID);
        openPage(postCommentFragment, tagBackStack, true, false);
    }

    public void showCourseDrive() {
//        if (courseDriveFragment == null)
        courseDriveFragment = new CourseDriveFragment();
        openPage(courseDriveFragment, CourseDetailFragment.class.getSimpleName(), true, false);
    }

    public void showSpotFacilitiesFragment(int spotID) {
        mSpotID = spotID;
        dataBundle.putInt(SPOT_ID, mSpotID);
//        if (spotFacilitiesFragment == null)
        spotFacilitiesFragment = new SpotFacilitiesFragment();
        openPage(spotFacilitiesFragment, true, false);
    }

    public void showFragmentTabLayoutRunning() {
//        if (fragmentTabLayoutRunning == null)
        fragmentTabLayoutRunning = new FragmentTabLayoutRunning();
        openPage(fragmentTabLayoutRunning, true, false);
    }

    public void showGoalFragment(int idSpot, float speed, String time, String imgUrl, String title, String distance) {
        dataBundle.putInt(COURSE_DETAIL_ID, mCourseID);
        dataBundle.putString(AVARAGE_SPEED, String.format("%.2f", speed));
        dataBundle.putString(TIME_FINISH, time);
        dataBundle.putInt(SPOT_ID, idSpot);
        dataBundle.putString(STAMP_IMAGE, imgUrl);
        dataBundle.putString(STAMP_TITLE, title);
        dataBundle.putString(STAMP_DISTANCE, distance);
//        if (goalFragment == null)
        goalFragment = new GoalFragment();
        openPage(goalFragment, FragmentTabLayoutRunning.class.getSimpleName(), true, false);
    }

    public void showCourseFinish(String speed, String time, boolean isFromMain) {
        dataBundle.putInt(COURSE_DETAIL_ID, mCourseID);
        dataBundle.putString(AVARAGE_SPEED, speed);
        dataBundle.putString(TIME_FINISH, time);
//        if (finishCourseFragment == null)
        finishCourseFragment = new FinishCourseFragment();
        finishCourseFragment.isFromMain = isFromMain;
        openPage(finishCourseFragment, true, false);
    }

    public void showSpotImages(int spotID) {
        mSpotID = spotID;
        dataBundle.putInt(SPOT_ID, mSpotID);
        typeBackPress = 3;
//        if (courseDetailSpotImagesFragment == null)
        courseDetailSpotImagesFragment = new CourseDetailSpotImagesFragment();
        openPage(courseDetailSpotImagesFragment, true, false);
    }
    public void showSpotImages(int spotID,boolean isCheckingSpot) {
        mSpotID = spotID;
        this.isCheckingSpot =isCheckingSpot;
        dataBundle.putBoolean(IS_CHECKING_SPOT, isCheckingSpot);
        dataBundle.putInt(SPOT_ID, mSpotID);

//        if (courseDetailSpotImagesFragment == null)
        courseDetailSpotImagesFragment = new CourseDetailSpotImagesFragment();
        openPage(courseDetailSpotImagesFragment, true, false);
    }
    public void showSpotImages(int spotID, String tag) {
        mSpotID = spotID;
        dataBundle.putInt(SPOT_ID, mSpotID);
//        if (courseDetailSpotImagesFragment == null)
        courseDetailSpotImagesFragment = new CourseDetailSpotImagesFragment();
        openPage(courseDetailSpotImagesFragment, tag, true, false);
    }
    public void showCheckPointFragment() {

        isCheckingSpot=false;
        dataBundle.putBoolean(IS_CHECKING_SPOT, isCheckingSpot);
        checkPointFragment = new CheckPointFragment();
        openPage(checkPointFragment, true, false);
    }
    public void showCheckPointFragment(int mSpotID, String imgUrl, String title, String time, String distance, boolean showSecondAnim,boolean isFirstSpot) {
        this.mSpotID = mSpotID;
        dataBundle.putInt(SPOT_ID, mSpotID);
        dataBundle.putInt(COURSE_DETAIL_ID, mCourseID);
        dataBundle.putString(STAMP_IMAGE, imgUrl);
        dataBundle.putString(STAMP_TITLE, title);
        dataBundle.putString(TIME_FINISH, time);
        dataBundle.putString(STAMP_DISTANCE, distance);
        dataBundle.putBoolean(STAMP_GAIN, showSecondAnim);
        dataBundle.putBoolean(IS_FIRST_SPOT, isFirstSpot);

//        if (checkPointFragment == null)
        checkPointFragment = new CheckPointFragment();
        openPage(checkPointFragment, true, false);
    }

    public void showSpotFacilities(boolean isCheckingSpot) {
//        if (spotFacilitiesFragment == null)
        this.isCheckingSpot =isCheckingSpot;
        spotFacilitiesFragment = new SpotFacilitiesFragment();
        dataBundle.putBoolean(IS_CHECKING_SPOT, isCheckingSpot);
        openPage(spotFacilitiesFragment, true, false);
    }

    public void showSearchPage(SearchCourseFragment.OnFragmentInteractionListener listener) {
//        if (searchCourseFragment == null)
        searchCourseFragment = SearchCourseFragment.newInstance(listener);
        openPage(searchCourseFragment, true, true);
    }
    public void openPage(android.support.v4.app.Fragment fragment, boolean isBackStack, boolean isAnimation) {
        if (fragment.getArguments() == null)// quanpv
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

    public void openPage(android.support.v4.app.Fragment fragment, String tag, boolean isBackStack, boolean isAnimation) {
        if (fragment.getArguments() == null)// quanpv
            fragment.setArguments(dataBundle);
        android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        if (isAnimation)
            tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isBackStack)
            tx.addToBackStack(tag);
        tx.commitAllowingStateLoss();
    }

    public void openPage(android.support.v4.app.Fragment fragment, boolean isBackStack, boolean isAnimation, boolean isBack) {
        fragment.setArguments(dataBundle);
        frgTransaction = getSupportFragmentManager().beginTransaction();
        if (isAnimation) {
            if (isBack)
                frgTransaction.setCustomAnimations(R.anim.pop_exit, R.anim.pop_enter);
            else
                frgTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        }

        frgTransaction.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        frgTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);


        if (isBackStack)
            frgTransaction.addToBackStack(null);
        frgTransaction.commitAllowingStateLoss();
    }


    public void showTakePhoto(int spotID, String time, String distance) {

        timeFinish = time;
        distanceSpot = distance;
        mSpotID = spotID;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
        } else {
            mSpotID = spotID;
            //         dataBundle.putInt(SPOT_ID, mSpotID);
            //        dataBundle.putInt(COURSE_DETAIL_ID, mCourseID);
            //        dataBundle.putString(TIME_FINISH, time);
            //        dataBundle.putString(STAMP_DISTANCE, distance);
//            openPage(new TakePhotoFragment(), true, false);
            Intent intent = new Intent(this, TakePhotoActivity.class);
            intent.putExtra(SPOT_ID, mSpotID);
            intent.putExtra(COURSE_DETAIL_ID, mCourseID);
            intent.putExtra(TIME_FINISH, timeFinish);
            intent.putExtra(STAMP_DISTANCE, distanceSpot);
            startActivity(intent);

        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitudeNetWork() {
        return latitudeNetWork;
    }

    public void setLatitudeNetWork(double latitudeNetWork) {
        this.latitudeNetWork = latitudeNetWork;
    }

    public double getLongitudeNetWork() {
        return longitudeNetWork;
    }

    public void setLongitudeNetWork(double longitudeNetWork) {
        this.longitudeNetWork = longitudeNetWork;
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginSNSActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof PostCommentFragment) {
            typeBackPress = 1;
            popBackStack(CourseDetailFragment.class.getSimpleName());
            return;
        } else if (fragment instanceof SpotFacilitiesFragment) {
            if(isCheckingSpot)
            {
                showCheckPointFragment();
            }
            else {
                typeBackPress = 3;
                showSpotImages(mSpotID);
            }
            return;
        } else if (fragment instanceof CourseDetailSpotImagesFragment) {
            if(isCheckingSpot) {
                // popBackStack(CourseDetailFragment.class.getSimpleName());
                showCheckPointFragment();
                return;
            }

        } else if (fragment instanceof CountDownTimesFragment) {
            return;
        }
        else if (fragment instanceof CheckPointFragment) {
            showFragmentTabLayoutRunning();
            return;
        }else if (fragment instanceof FragmentTabLayoutRunning) {
            popBackStack(CourseDetailFragment.class.getSimpleName());
            return;

        } else if (fragment instanceof GoalFragment) {
            return;
        } else if (fragment instanceof CourseListFragment) {
            finish();
            return;
        } else if (fragment instanceof CourseDetailFragment) {
            showCourseListPage();
            return;
        }

        super.onBackPressed();
        Log.i("onBackPressed", "true");
    }


    public void popBackStack(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void popBackStack(int count) {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < count; i++) {
            fm.popBackStack();
        }
    }

    public void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(CourseListActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {
                Log.i("shouldShowRequest", "------------------>");
                ActivityCompat.requestPermissions(CourseListActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION

                        },
                        REQUEST_PERMISSIONS);
//                Snackbar.make(mLayout, "Accept location",
//                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // Request the permission
//                        ActivityCompat.requestPermissions(CourseListActivity.this,
//                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                                REQUEST_PERMISSIONS);
//                    }
//                }).show();


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
//                    openPage(new TakePhotoFragment(), true, false);
                    Intent intent = new Intent(this, TakePhotoActivity.class);
                    intent.putExtra(SPOT_ID, mSpotID);
                    intent.putExtra(COURSE_DETAIL_ID, mCourseID);
                    intent.putExtra(TIME_FINISH, timeFinish);
                    intent.putExtra(STAMP_DISTANCE, distanceSpot);
                    startActivity(intent);
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
            final ArrayList<Location> lstLOcat = new ArrayList<>();
            intentGPS = new Intent(this, GoogleService.class);
            if (mCourseID > 0) {
                Log.i("onBind", "" + mCourseID);
                GetCourseDataAPI.getCourseData(mCourseID, new ServiceCallback() {
                    @Override
                    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                        JSONObject jsonObjec = (JSONObject) response;
                        if (!jsonObjec.has("error")) {
                            CourseDetail courseData = new CourseDetail((JSONObject) response);
                            List<Spot> lstSpot = courseData.getSpot();
                            double la1, long1;
                            for (Spot spot : lstSpot) {
                                if (!spot.getLatitude().isEmpty() && !spot.getLatitude().isEmpty() && spot.getOrderNumber()!=0) {

                                    Location location1 = new Location(spot.getSpotId(), Double.parseDouble(spot.getLatitude()), Double.parseDouble(spot.getLongitude()), spot.getOrderNumber());

                                    if (!lstLOcat.contains(location1))
                                        lstLOcat.add(location1);


                                }
                            }
                            intentGPS.putExtra("location", lstLOcat);
                            startService(intentGPS);
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            } else {
                intentGPS.putExtra("location", lstLOcat);
                startService(intentGPS);
            }


            Log.i("GPSLOG", "turn on \n");
        }
    }

    public void turnOffGPS() {
        stopService(intentGPS);

        Log.i("GPSLOG", "turn off \n");

    }

    public void turnOnGps(ArrayList<Location> listLocate) {
        if (intentGPS != null) {
            intentGPS.putExtra("location", listLocate);
            startService(intentGPS);
        }

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str1 = intent.getStringExtra("latutide");
            String str2 = intent.getStringExtra("longitude");

            if (str1.isEmpty() && str2.isEmpty())
                return;//location_network
            boolean isLocationNetWork = intent.getBooleanExtra("location_network", false);
            if (isLocationNetWork) {
                latitudeNetWork = Double.valueOf(intent.getStringExtra("latutide"));
                longitudeNetWork = Double.valueOf(intent.getStringExtra("longitude"));
            } else {
                latitude = Double.valueOf(intent.getStringExtra("latutide"));
                longitude = Double.valueOf(intent.getStringExtra("longitude"));
            }
            Log.i("latutide", "" + latitude);
            Log.i("longitude", "" + longitude);

            List<Address> addresses = null;

//            try {
//                addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                String cityName = addresses.get(0).getAddressLine(0);
//                String stateName = addresses.get(0).getAddressLine(1);
//                String countryName = addresses.get(0).getAddressLine(2);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
        }
    };

    @Override
    public void onResume() {
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

