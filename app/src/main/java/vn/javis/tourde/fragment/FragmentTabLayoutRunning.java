package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.adapter.ListCheckInSpot;
import vn.javis.tourde.adapter.ListSpotLog;
import vn.javis.tourde.adapter.ViewPagerAdapter;
import vn.javis.tourde.apiservice.CheckInStampAPI;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.apiservice.PostCourseLogAPI;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.model.Location;
import vn.javis.tourde.model.SaveCourseRunning;
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.model.Stamp;
import vn.javis.tourde.services.ChronometerService;
import vn.javis.tourde.services.GoogleService;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.utils.ClassToJson;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;


public class FragmentTabLayoutRunning extends BaseFragment {
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.show_select_spot)
    RelativeLayout show_select_spot;

    private long pauseOffset;
    // private boolean running;

    @BindView(R.id.tv_time)
    android.widget.Chronometer chronometer;
    @BindView(R.id.finish_resume)
    LinearLayout finishResume;
    @BindView(R.id.stop_time)
    Button stopTime;
    CourseListActivity mActivity;
    @BindView(R.id.select_spot)
    RecyclerView spotRecycler;
    ListCheckInSpot listSpotCheckinAdapter;
    long time;
    double latitude;
    double longtitude;
    double lastLatitude;
    double lastLongtitude;
    Geocoder geocoder;
    int courseID;
    int lastSpotId;
    float courseDistance;
    boolean changePaged;
    private String time_str = "00:00:00";
    private FragmentTabLayoutRunning.OnFragmentInteractionListener listener;
    int currentIdSpotDetect;
    long currentTimeDetect;
    //  TextView tv_back_password;
    private RegisterActivity activity;
    private SharedPreferencesUtils preferencesUtils;
    public static final String KEY_SHARED_BASETIME = "key_time_";
    ArrayList<Location> lstLocation = new ArrayList<>();
    List<Spot> list_spot = new ArrayList<>();

    SaveCourseRunning saveCourseRunning;
    FragmentLog fragmentLog;
    private boolean isSaveTime = true;// save when leave sreen this

    public static FragmentTabLayoutRunning newInstance(ListCheckInSpot.OnItemClickedListener listener) {
        FragmentTabLayoutRunning fragment = new FragmentTabLayoutRunning();
//        fragment.listener = (CheckPointFragment.OnFragmentInteractionListener) listener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (CourseListActivity) getActivity();
        courseID = mActivity.getmCourseID();
        String savedString =SharedPreferencesUtils.getInstance(getContext()).getStringValue(Constant.SAVED_COURSE_RUNNING);
        if (!TextUtils.isEmpty(savedString)) {
            saveCourseRunning = new ClassToJson<SaveCourseRunning>().getClassFromJson(savedString, SaveCourseRunning.class);
            courseID = saveCourseRunning.getCourseID();
            mActivity.setmCourseID(courseID);

        }
        preferencesUtils = SharedPreferencesUtils.getInstance(mActivity);
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                chronometer.setText(t);
                time_str = t;
                Log.i("onChronometerTick", t);
            }
        });
        isSaveTime = true;

        if (preferencesUtils.getLongValue(KEY_SHARED_BASETIME) == 0) {
            time = SystemClock.elapsedRealtime();
        } else if (preferencesUtils.getLongValue(KEY_SHARED_BASETIME) < 0) {
            time = SystemClock.elapsedRealtime() + preferencesUtils.getLongValue(KEY_SHARED_BASETIME);
        } else {
            time = preferencesUtils.getLongValue(KEY_SHARED_BASETIME);
        }
        chronometer.setBase(time);
        chronometer.start();
        chronometer.setText(time_str);

        Log.i("timer", "" + KEY_SHARED_BASETIME);
        initTabControl();

        // PicassoUtil.getSharedInstance(mActivity).load("").transform(new CircleTransform()).into(imageCheckinSport);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        spotRecycler.setItemAnimator(new DefaultItemAnimator());
        spotRecycler.setLayoutManager(layoutManager);
        show_select_spot.setVisibility(View.GONE);
        final int courseId = mActivity.getmCourseID();
        list_spot.clear();
        ProcessDialog.showProgressDialog(mActivity, "Loading", false);
        GetCourseDataAPI.getCourseData(courseId, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("error"))
                    return;
                CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);
                if (!mCourseDetail.getmCourseData().getDistance().isEmpty())
                    courseDistance = Float.parseFloat(mCourseDetail.getmCourseData().getDistance());
                list_spot = mCourseDetail.getSpot();
                if (list_spot.size() > 0) {

                    longtitude = Double.parseDouble(list_spot.get(0).getLongitude());
                    latitude = Double.parseDouble(list_spot.get(0).getLatitude());
                    lastLatitude = latitude;
                    lastLongtitude = longtitude;
                    lastSpotId = list_spot.get(list_spot.size() - 1).getSpotId();

                    listSpotCheckinAdapter = new ListCheckInSpot(list_spot, mActivity);
                    listSpotCheckinAdapter.setOnItemClickListener(new ListCheckInSpot.OnItemClickedListener() {
                        @Override
                        public void onItemClick(int id) {
                            showCheckPointFragment(id);
                        }
                    });
                    spotRecycler.setAdapter(listSpotCheckinAdapter);

                    setListCheckedSpot();

                    setupViewPager(viewPager); //set info recyler tab fragment
                    tabLayout.setupWithViewPager(viewPager);
                }
                ProcessDialog.hideProgressDialog();
            }

            @Override
            public void onError(VolleyError error) {
                ProcessDialog.hideProgressDialog();
            }
        });

        //   spotRecycler.setAdapter(listSpotCheckinAdapter);
        mActivity.fn_permission();

    }

    private void startChronometerService() {
        SharedPreferencesUtils pref = SharedPreferencesUtils.getInstance(mActivity);
        if (TextUtils.isEmpty(pref.getStringValue("startChronometerService"))) {
            pref.setStringValue("startChronometerService", "startChronometerService");
            Intent intent = new Intent(getContext(), ChronometerService.class);
            intent.putExtra(ChronometerService.KEY_TIME_BASE, SystemClock.elapsedRealtime());
            mActivity.startService(intent);
        }

    }

    void changeListSpotCheckInData() {
        List<Spot> newList = new ArrayList<>();
        for (Spot spot : list_spot) {
            int id = spot.getSpotId();
            for (int i = 0; i < lstLocation.size(); i++) {
                if (!isSpotChecked(id) && lstLocation.get(i).getSpotID() == id) {
                    newList.add(spot);
                    continue;
                }
            }
        }
        if (newList.size() > 0) {
            listSpotCheckinAdapter = new ListCheckInSpot(newList, mActivity);
            listSpotCheckinAdapter.setOnItemClickListener(new ListCheckInSpot.OnItemClickedListener() {
                @Override
                public void onItemClick(int id) {
                    TourDeApplication.getInstance().trackEvent("tap_checkin_spot_id=" + id, "tap", "tap_checkin_spot_id=" + id);
                    showCheckPointFragment(id);
                }
            });
            spotRecycler.setAdapter(listSpotCheckinAdapter);
            spotRecycler.setAdapter(listSpotCheckinAdapter);
            onGetListSpotArrived(newList.size(), newList.get(0).getSpotId());
        }

    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longtitude = Double.valueOf(intent.getStringExtra("longitude"));
        }
    };

    private BroadcastReceiver broadcastReceiverArried = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (changePaged)
                return;
            lstLocation.clear();
            lstLocation = (ArrayList<Location>) intent.getSerializableExtra("arrived");
            Log.i("latutide111", "sbcccc");
            if (!lstLocation.isEmpty()) {
                changeListSpotCheckInData();
            }
        }
    };

    private BroadcastReceiver broadcastReceiver_chronometer = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            time_str = intent.getStringExtra(ChronometerService.KEY_TIME_STRING);
            time = intent.getLongExtra(ChronometerService.KEY_TIME, 0);
            chronometer.setText(time_str);
        }
    };

    void onGetListSpotArrived(int numSpot, int spotId) {
        if (numSpot == 1) {
            showCheckPointFragment(spotId);
        } else {
            show_select_spot.setVisibility(View.VISIBLE);
            changePaged = true;
        }
    }

    void showCheckPointFragment(final int spotId) {
        if (isSpotChecked(spotId))
            return;

        checkedSpot(spotId, getTimeFormat(time), calculateAvarageSpeed(time));

        if (spotId > 0 && courseID > 0) {
            final String token = LoginFragment.getmUserToken();
            if (getSizeCheckedSpot() == list_spot.size()) //complete all spot
            {
                if (spotId == lastSpotId) {
                    ProcessDialog.showProgressDialog(mActivity, "Loading", false);
                    CheckInStampAPI.postCheckInStamp(token, courseID, spotId, new ServiceCallback() { //call checkinstamp
                        @Override
                        public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                            JSONObject jsonObject = (JSONObject) response;
                            if (!jsonObject.has("error")) {
                                Stamp model = Stamp.getData(response.toString());
                                if (model.getSuccess()) {
                                    final String imgUrl = model.getImage() == null ? "" : model.getImage();
                                    final String title = model.getTitle() == null ? "" : model.getTitle();
                                    if (spotId == lastSpotId) {
                                        final float speed = courseDistance / ((float) time / 3600000);
                                        int h = (int) (time / 3600000);
                                        int m = (int) (time - h * 3600000) / 60000;
                                        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                                        final String finishTime = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                                        preferencesUtils.setLongValue(KEY_SHARED_BASETIME, 0);
                                        isSaveTime = false;

                                        //
                                        PostCourseLogAPI.postCourseLog(token, courseID, speed, finishTime, new ServiceCallback() { //call postcourselog
                                            @Override
                                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                                JSONObject jsonObject = (JSONObject) response;
                                                if (jsonObject.has("success")) {
                                                    mActivity.showGoalFragment(spotId, speed, finishTime, imgUrl, title);
                                                }
                                                ProcessDialog.hideProgressDialog();
                                            }

                                            @Override
                                            public void onError(VolleyError error) {
                                                ProcessDialog.hideProgressDialog();
                                            }
                                        });
                                    }
                                }
                            }
                            ProcessDialog.hideProgressDialog();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Log.i("VolleyError", "" + error.getMessage());
                            ProcessDialog.hideProgressDialog();
                        }
                    });
                } else {
                    CheckInStampAPI.postCheckInStamp(token, courseID, spotId, new ServiceCallback() { //call checkinstamp
                        @Override
                        public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                            JSONObject jsonObject = (JSONObject) response;
                            if (!jsonObject.has("error")) {
                                Stamp model = Stamp.getData(response.toString());
                                if (model.getSuccess()) {
                                    isSaveTime = false;
                                    preferencesUtils.setLongValue(KEY_SHARED_BASETIME, chronometer.getBase());
                                    final String imgUrl = model.getImage() == null ? "" : model.getImage();
                                    final String title = model.getTitle() == null ? "" : model.getTitle();
                                    mActivity.showCheckPointFragment(spotId, imgUrl, title);
                                }
                            }
                            ProcessDialog.hideProgressDialog();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Log.i("VolleyError", "" + error.getMessage());
                            ProcessDialog.hideProgressDialog();
                        }
                    });
                }

            } else if (spotId == lastSpotId) {
                final float speed = courseDistance / ((float) time / 3600000);
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                final String finishTime = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                preferencesUtils.setLongValue(KEY_SHARED_BASETIME, 0);
                isSaveTime = false;
                //   mActivity.showGoalFragment(spotId, speed, finishTime,"","");
                //   ProcessDialog.showProgressDialog(mActivity, "Loading", false);
                PostCourseLogAPI.postCourseLog(token, courseID, speed, finishTime, new ServiceCallback() {
                    @Override
                    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                        JSONObject jsonObject = (JSONObject) response;
                        if (jsonObject.has("success")) {
                            isSaveTime = false;
                            preferencesUtils.setLongValue(KEY_SHARED_BASETIME, chronometer.getBase());
                            mActivity.showGoalFragment(spotId, speed, finishTime, "", "");
                        }
                        ProcessDialog.hideProgressDialog();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        ProcessDialog.hideProgressDialog();
                    }
                });
            } else {
                //   mActivity.showCheckPointFragment(spotId, "","");
                //   ProcessDialog.showProgressDialog(mActivity, "Loading", false);
                CheckInStampAPI.postCheckInStamp(token, spotId, new ServiceCallback() {
                    @Override
                    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                        JSONObject jsonObject = (JSONObject) response;
                        if (!jsonObject.has("error")) {
                            Stamp model = Stamp.getData(response.toString());

                            if (model.getSuccess()) {
                                isSaveTime = false;
                                preferencesUtils.setLongValue(KEY_SHARED_BASETIME, chronometer.getBase());
                                String imgUrl = model.getImage() == null ? "" : model.getImage();
                                String title = model.getTitle() == null ? "" : model.getTitle();
                                mActivity.showCheckPointFragment(spotId, imgUrl, title);
                            }
                        }
                        ProcessDialog.hideProgressDialog();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Log.i("VolleyError", "" + error.getMessage());
                        ProcessDialog.hideProgressDialog();
                    }
                });
            }
        }
    }

    @OnClick({R.id.btn_back, R.id.stop_time, R.id.resume, R.id.finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
//                if (SharedPreferencesUtils.getInstance(getContext()).getLongValue(KEY_SHARED_BASETIME) == 0) {
//                    mActivity.ShowCourseDetailByTab(0);
//                } else {
//                    mActivity.onBackPressed();
//                }
                mActivity.onBackPressed();
                break;
            case R.id.stop_time:
                isSaveTime = false;
                pauseOffset = chronometer.getBase() - SystemClock.elapsedRealtime();
                preferencesUtils.setLongValue(KEY_SHARED_BASETIME, pauseOffset);
                chronometer.stop();
                stopTime.setVisibility(View.GONE);
                //temporary open select spot to checkin
                //   show_select_spot.setVisibility(View.VISIBLE);
                //   mActivity.unregisterReceiver(broadcastReceiverArried);
                mActivity.turnOffGPS();

                break;
            case R.id.resume:
                isSaveTime = true;
                chronometer.setBase(SystemClock.elapsedRealtime() + pauseOffset);
                chronometer.start();
                stopTime.setVisibility(View.VISIBLE);
                show_select_spot.setVisibility(View.GONE);
                changePaged = false;
                //   mActivity.registerReceiver(broadcastReceiverArried, new IntentFilter(GoogleService.str_receiver_arrived));
                mActivity.turnOnGPS();
                break;
            case R.id.finish:
                ProcessDialog.showDialogConfirm(getContext(), "", "終了しますか？", new ProcessDialog.OnActionDialogClickOk() {
                    @Override
                    public void onOkClick() {
                        SharedPreferencesUtils.getInstance(getContext()).removeKey(FragmentTabLayoutRunning.KEY_SHARED_BASETIME);
                        SharedPreferencesUtils.getInstance(getContext()).removeKey(Constant.SAVED_COURSE_RUNNING);
                        mActivity.ShowCourseDetail();

                    }
                });
                break;

        }
    }


    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View mView = inflater.inflate(R.layout.running, container, false);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        changePaged = false;
        // time = (preferencesUtils.getLongValue(KEY_SHARED_BASETIME) == 0) ? SystemClock.elapsedRealtime() : SystemClock.elapsedRealtime() + preferencesUtils.getLongValue(KEY_SHARED_BASETIME);
        mActivity.registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));
        mActivity.registerReceiver(broadcastReceiverArried, new IntentFilter(GoogleService.str_receiver_arrived));

    }

    @Override
    public void onPause() {
        super.onPause();
//        if (isSaveTime) {
//            pauseOffset = chronometer.getBase() - SystemClock.elapsedRealtime();
//            preferencesUtils.setLongValue(KEY_SHARED_BASETIME , pauseOffset);
//            chronometer.stop();
//        }
        mActivity.unregisterReceiver(broadcastReceiver);
        mActivity.unregisterReceiver(broadcastReceiverArried);
    }

    @Override
    public void onDestroyView() {
        if (isSaveTime) {
            preferencesUtils.setLongValue(KEY_SHARED_BASETIME, chronometer.getBase());
        }
        super.onDestroyView();
    }

    private void initTabControl() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 1:
                        //Todo tab selected
                        break;
                    case 2:
                        break;

                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentMap(), "MAP");
        fragmentLog = FragmentLog.intance(saveCourseRunning);
        adapter.addFragment(fragmentLog, "ログ");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

    }


    public interface OnFragmentInteractionListener extends View.OnClickListener {
        @Override
        void onClick(View v);
    }

    double calculateAvarageSpeed(long time) {
        double aSpeed = 0;
        double distance = SphericalUtil.computeDistanceBetween(new LatLng(lastLatitude, lastLongtitude), new LatLng(latitude, longtitude));
        aSpeed = courseDistance / ((double) time / 3600000);
        lastLatitude = latitude;
        lastLongtitude = longtitude;
        DecimalFormat df = new DecimalFormat("#.##");
        aSpeed = Double.valueOf(df.format(aSpeed));
        return aSpeed;
    }

    String getTimeFormat(long time) {
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        final String finishTime = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
        return finishTime;
    }

    private void setListCheckedSpot() {
        if (saveCourseRunning != null)
            return;
        saveCourseRunning = new SaveCourseRunning(courseID, lastLatitude, lastLongtitude);
        boolean checked = true;

        for (Spot spot : list_spot) {
            SaveCourseRunning.CheckedSpot checkedSpot = saveCourseRunning.new CheckedSpot(spot.getSpotId(), spot.getTitle(), spot.getOrderNumber(), spot.getTopImage(), checked);
            saveCourseRunning.getLstCheckedSpot().add(checkedSpot);
            //saveCourseRunning.addCheckedSpot(spot.getSpotId(), spot.getTitle(), spot.getOrderNumber(), spot.getTopImage(), checked);
          //  checked = false;
        }
        String s = new ClassToJson<SaveCourseRunning>().getStringClassJson(saveCourseRunning);
        SharedPreferencesUtils.getInstance(getContext()).setStringValue(Constant.SAVED_COURSE_RUNNING, s);

    }

    private boolean isSpotChecked(int spotId) {
        for (int i = 0; i < saveCourseRunning.getLstCheckedSpot().size(); i++) {
            if ( saveCourseRunning.getLstCheckedSpot().get(i).getSpotID() == spotId &&  saveCourseRunning.getLstCheckedSpot().get(i).isChecked()) {
                Log.i("running566", "" + spotId);
                return true;

            }
        }
        Log.i("running566-", "" + spotId);
        return false;
    }

    private void checkedSpot(int spotId, String finishTime, double averageSpeed) {
        for (int i = 0; i <  saveCourseRunning.getLstCheckedSpot().size(); i++) {
            if ( saveCourseRunning.getLstCheckedSpot().get(i).getSpotID() == spotId) {
                saveCourseRunning.getLstCheckedSpot().get(i).setChecked(true);
                saveCourseRunning.getLstCheckedSpot().get(i).setAvarageSpeed(averageSpeed);
                saveCourseRunning.getLstCheckedSpot().get(i).setTime(finishTime);
            }
        }
        fragmentLog.updateCheckedSpot( saveCourseRunning);
    }

    private int getSizeCheckedSpot() {
        int num = 0;
        for (int i = 0; i < saveCourseRunning.getLstCheckedSpot().size(); i++) {
            if (saveCourseRunning.getLstCheckedSpot().get(i).isChecked() == true) {
                num++;

            }
        }
        return num;
    }
}
