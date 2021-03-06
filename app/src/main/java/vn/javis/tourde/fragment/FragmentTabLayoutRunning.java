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
import java.util.Date;
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
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.utils.TimeUtil;


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

    FragmentMap fragmentMap;
    private boolean isSaveTime = true;// save when leave sreen this
    public boolean isFinishTime;
    public boolean isFromMain;
    public boolean isTimeSaved;
    boolean isPausing;
    private int timeWaitForNext = 300;
    String route_url;
    String token = SharedPreferencesUtils.getInstance(getContext()).getStringValue(LoginUtils.TOKEN);
    Date lastTimeCheckin;

    public static FragmentTabLayoutRunning newInstance(ListCheckInSpot.OnItemClickedListener listener) {
        FragmentTabLayoutRunning fragment = new FragmentTabLayoutRunning();
//        fragment.listener = (CheckPointFragment.OnFragmentInteractionListener) listener;
        return fragment;
    }

    public static FragmentTabLayoutRunning newInstance(boolean isFromMain) {
        FragmentTabLayoutRunning fragment = new FragmentTabLayoutRunning();
        fragment.isFromMain = isFromMain;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (CourseListActivity) getActivity();
        courseID = mActivity.getmCourseID();
        lastTimeCheckin = new Date();
        lastTimeCheckin.setTime(-30000);
        String savedString = SharedPreferencesUtils.getInstance(getContext()).getStringValue(Constant.SAVED_COURSE_RUNNING);
        if (!TextUtils.isEmpty(savedString)) {
            saveCourseRunning = new ClassToJson<SaveCourseRunning>().getClassFromJson(savedString, SaveCourseRunning.class);

            if (SharedPreferencesUtils.getInstance(mActivity).getBooleanValue(Constant.KEY_GOAL_PAGE)) {
                if (saveCourseRunning != null) {
                    mActivity.openPage(GoalFragment.newInstance(saveCourseRunning.getCourseID(), saveCourseRunning.getGoalSpotId(), saveCourseRunning.getAvarageSpeed(), TimeUtil.getTimeFormat(saveCourseRunning.getLastCheckedTime()), saveCourseRunning.getImgUrlGoal(), saveCourseRunning.getGoal_title(), saveCourseRunning.getAllDistance(), true), FragmentTabLayoutRunning.class.getSimpleName(), true, false);
                }
            }
            courseID = saveCourseRunning.getCourseID();
            mActivity.setmCourseID(courseID);
            lastLongtitude = saveCourseRunning.getLast_longtitude();
            lastLatitude = saveCourseRunning.getLast_latitude();
            lastTimeCheckin = saveCourseRunning.getLastTimeCheckin();
            //    lastLongtitude = saveCourseRunning.la
//            if(saveCourseRunning.isFinished())
//            {
//                saveCourseRunning.resetAllSpot();
//                saveCourseRunning.setFinished(false);
//            }

        }
        preferencesUtils = SharedPreferencesUtils.getInstance(mActivity);

        //  mActivity.registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));
        //   mActivity.registerReceiver(broadcastReceiverArried, new IntentFilter(GoogleService.str_receiver_arrived));
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.setKeepScreenOn(true);
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
        list_spot.clear();
        //     listSpotCheckinAdapter = new ListCheckInSpot(list_spot, mActivity);
        //   spotRecycler.setAdapter(listSpotCheckinAdapter);
        final int courseId = mActivity.getmCourseID();

//        setupViewPager(viewPager);
//        tabLayout.setupWithViewPager(viewPager);
        showProgressDialog();
        GetCourseDataAPI.getCourseData(courseId, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("error"))
                    return;
                CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);
                if (!mCourseDetail.getmCourseData().getDistance().isEmpty())
                    courseDistance = Float.parseFloat(mCourseDetail.getmCourseData().getDistance());
                route_url = mCourseDetail.getmCourseData().getRouteUrl();
                mActivity.setMapUrl(mCourseDetail.getmCourseData().getKmlFile());
                mActivity.setRoute_url(mCourseDetail.getmCourseData().getRouteUrl());
//                if(fragmentMap !=null)
//                {
//                    fragmentMap.onResume();
//                }
                list_spot.clear();
                list_spot = mCourseDetail.getSpot();
                if (list_spot.size() > 0) {

                    longtitude = Double.parseDouble(list_spot.get(0).getLongitude());
                    latitude = Double.parseDouble(list_spot.get(0).getLatitude());
                    lastSpotId = list_spot.get(list_spot.size() - 1).getSpotId();

                    //     listSpotCheckinAdapter = new ListCheckInSpot(list_spot, mActivity);
//                    listSpotCheckinAdapter.setOnItemClickListener(new ListCheckInSpot.OnItemClickedListener() {
//                        @Override
//                        public void onItemClick(int id,int order) {
//                            showCheckPointFragment(id,order);
//                        }
//                    });
                    setupViewPager();
                    tabLayout.setupWithViewPager(viewPager);
                    if (saveCourseRunning == null) {
                        lastLatitude = latitude;
                        lastLongtitude = longtitude;
                        setListCheckedSpot();
                    }
                    //   listSpotCheckinAdapter.notifyDataSetChanged();

                    //set info recyler tab fragment

                }
                hideProgressDialog();
            }

            @Override
            public void onError(VolleyError error) {
                hideProgressDialog();
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
            int orderNumber = spot.getOrderNumber();
            for (int i = 0; i < lstLocation.size(); i++) {
                if (!isSpotChecked(id, orderNumber) && lstLocation.get(i).getSpotID() == id && orderNumber == lstLocation.get(i).getOrderNumber() && !newList.contains(spot)) {
                    newList.add(spot);
                }
            }
        }
        if (newList.size() > 0) {
            listSpotCheckinAdapter = new ListCheckInSpot(newList, mActivity);
            listSpotCheckinAdapter.setOnItemClickListener(new ListCheckInSpot.OnItemClickedListener() {
                @Override
                public void onItemClick(int id, int order) {
                    TourDeApplication.getInstance().trackEvent("tap_checkin_spot_id=" + id, "tap", "tap_checkin_spot_id=" + id);
                    showCheckPointFragment(id, order);
                }
            });
            spotRecycler.setAdapter(listSpotCheckinAdapter);
            //  listSpotCheckinAdapter.notifyDataSetChanged();
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
            //check time here
            try {
                Date timeNow = new Date();
                long diff = (timeNow.getTime() - lastTimeCheckin.getTime()) / 1000;

                if (diff < timeWaitForNext) {
                    return;
                }
                lstLocation.clear();
                lstLocation = (ArrayList<Location>) intent.getSerializableExtra("arrived");
                Log.i("lstLocation335", "lstLocation" + lstLocation.size());
                if (!lstLocation.isEmpty()) {
                    changeListSpotCheckInData();
                }
            } catch (Exception e) {


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
//        if (numSpot == 1) {
//            showCheckPointFragment(spotId);
//        } else {
        show_select_spot.setVisibility(View.VISIBLE);
        //      changePaged = true;
        //  }
    }

    void showCheckPointFragment(final int spotId, final int order) {
        if (isSpotChecked(spotId, order))
            return;

        final String distance = String.format("%.2f", getCurrentDistance());
        double distanceSpot = getSpotDistanceByOrder(order - 1);
        final String strDistanceSpot = String.format("%.2f", distanceSpot);

        long lastCheckedTime = saveCourseRunning.getLastCheckedTime();
        long timeDiffer = time - lastCheckedTime;
        Date timeNow = new Date();
        lastTimeCheckin = timeNow;
        saveCourseRunning.setLastTimeCheckin(lastTimeCheckin);
        checkedSpot(spotId, order, getTimeFormat(timeDiffer), calculateAvarageSpeed(distanceSpot, timeDiffer), time);

        if (spotId > 0 && courseID > 0) {
            if (getSizeCheckedSpot() == list_spot.size()) //complete all spot
            {
                saveCourseRunning.setFinished(true);
                if (spotId == lastSpotId) {
                    showProgressDialog();
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
                                        final float speed = (float) courseDistance / ((float) time / 3600000);
                                        final String finishTime = getTimeFormat(time);
//                                        preferencesUtils.setLongValue(KEY_SHARED_BASETIME, 0);
//                                        isSaveTime = false;

                                        //
                                        PostCourseLogAPI.postCourseLog(token, courseID, speed, finishTime, new ServiceCallback() { //call postcourselog
                                            @Override
                                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                                JSONObject jsonObject = (JSONObject) response;
                                                if (jsonObject.has("success")) {
                                                    saveGoalValue(spotId, speed, finishTime, imgUrl, title, strDistanceSpot);
                                                    mActivity.showGoalFragment(spotId, speed, finishTime, imgUrl, title, strDistanceSpot);

                                                }
                                                hideProgressDialog();
                                            }

                                            @Override
                                            public void onError(VolleyError error) {
                                                hideProgressDialog();
                                            }
                                        });
                                    }
                                }
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Log.i("VolleyError", "" + error.getMessage());
                            hideProgressDialog();
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
                                    isTimeSaved = true;
                                    final String imgUrl = model.getImage() == null ? "" : model.getImage();
                                    final String title = model.getTitle() == null ? "" : model.getTitle();
                                    String finish_time = getTimeFormat(time);

                                    mActivity.showCheckPointFragment(spotId, imgUrl, title, finish_time, strDistanceSpot, model.getGained(), false);
                                }
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Log.i("VolleyError", "" + error.getMessage());
                            hideProgressDialog();
                        }
                    });
                }

            } else if (spotId == lastSpotId) {
                final float speed =  courseDistance / ((float) time / 3600000);
                final String finishTime = getTimeFormat(time);
//                preferencesUtils.setLongValue(KEY_SHARED_BASETIME, 0);
//                isSaveTime = false;
                //   mActivity.showGoalFragment(spotId, speed, finishTime,"","");
                //   ProcessDialog.showProgressDialog(mActivity, "Loading", false);
                PostCourseLogAPI.postCourseLog(token, courseID, speed, finishTime, new ServiceCallback() {
                    @Override
                    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                        JSONObject jsonObject = (JSONObject) response;
                        if (jsonObject.has("success")) {
                            isSaveTime = false;
                            preferencesUtils.setLongValue(KEY_SHARED_BASETIME, chronometer.getBase());
                            isTimeSaved = true;
                            saveGoalValue(spotId, speed, finishTime, "", "", strDistanceSpot);
                            mActivity.showGoalFragment(spotId, speed, finishTime, "", "", strDistanceSpot);

                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        hideProgressDialog();
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
                                String finish_time = getTimeFormat(time);
//                                for test animation
//                                if(spotId==181) model.setGained( true );

                                mActivity.showCheckPointFragment(spotId, imgUrl, title, finish_time, strDistanceSpot, model.getGained(), false);
                                isTimeSaved = true;
                            }
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Log.i("VolleyError", "" + error.getMessage());
                        hideProgressDialog();
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
                isTimeSaved = true;
                pauseOffset = chronometer.getBase() - SystemClock.elapsedRealtime();
                preferencesUtils.setLongValue(KEY_SHARED_BASETIME, pauseOffset);
                chronometer.stop();
                stopTime.setVisibility(View.GONE);
                //temporary open select spot to checkin
                //   show_select_spot.setVisibility(View.VISIBLE);
                //   mActivity.unregisterReceiver(broadcastReceiverArried);
                mActivity.turnOffGPS();
                isPausing = true;
                break;
            case R.id.resume:
                isSaveTime = true;
//                isTimeSaved = true;
                chronometer.setBase(SystemClock.elapsedRealtime() + pauseOffset);
                chronometer.start();
                stopTime.setVisibility(View.VISIBLE);
                show_select_spot.setVisibility(View.GONE);
                changePaged = false;
                //   mActivity.registerReceiver(broadcastReceiverArried, new IntentFilter(GoogleService.str_receiver_arrived));
                mActivity.turnOnGPS();
                isPausing = false;
                break;
            case R.id.finish:
                ProcessDialog.showDialogConfirm(getContext(), "", "終了しますか？", new ProcessDialog.OnActionDialogClickOk() {
                    @Override
                    public void onOkClick() {
                        SharedPreferencesUtils.getInstance(getContext()).removeKey(FragmentTabLayoutRunning.KEY_SHARED_BASETIME);
                        SharedPreferencesUtils.getInstance(getContext()).removeKey(Constant.SAVED_COURSE_RUNNING);
                        SharedPreferencesUtils.getInstance(getContext()).removeKey(Constant.KEY_GOAL_PAGE);
//                        mActivity.ShowCourseDetail();
                        isFinishTime = true;
                        mActivity.onBackPressed();

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
        CourseListActivity.isRunningBackground = false;
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
        if (!isPausing) {
            CourseListActivity.isRunningBackground = true;
            ArrayList<Location> lstUncheckedSpot = getListUncheckedSpot();
            if (lstUncheckedSpot.size() > 0) {
                mActivity.turnOnGps(lstUncheckedSpot);
            }
        }
    }

    @Override
    public void onDestroyView() {
        CourseListActivity.isRunningBackground = false;
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

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        fragmentMap = FragmentMap.instance(list_spot);
        adapter.addFragment(fragmentMap, "MAP");
        fragmentLog = FragmentLog.intance(saveCourseRunning, courseDistance);
        adapter.addFragment(fragmentLog, "ログ");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

    }


    public interface OnFragmentInteractionListener extends View.OnClickListener {
        @Override
        void onClick(View v);
    }

    double calculateAvarageSpeed(double spotDistance, long time) {


        double aSpeed = spotDistance / ((double) time / 3600000);
        lastLatitude = latitude;
        lastLongtitude = longtitude;
        saveCourseRunning.setLast_latitude(lastLatitude);
        saveCourseRunning.setLast_longtitude(lastLongtitude);
        DecimalFormat df = new DecimalFormat("#.##");
        aSpeed = Double.valueOf(df.format(aSpeed).replace(",", "."));
        return aSpeed;
    }

    String getTimeFormat(long time) {
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        final String finishTime = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
        return finishTime;
    }

    double getCurrentDistance() {
        double distance = SphericalUtil.computeDistanceBetween(new LatLng(lastLatitude, lastLongtitude), new LatLng(latitude, longtitude));
        distance = distance / 1000;
        return distance;
    }

    double getRealCourseDistance() {
        double distance = SphericalUtil.computeDistanceBetween(new LatLng(saveCourseRunning.getStart_latitude(), saveCourseRunning.getStart_longtitude()), new LatLng(latitude, longtitude));
        distance = distance / 1000;
        return distance;
    }

    double getSpotDitance(int spotId) {
        double distance = 0;
        for (Spot spot : list_spot) {
            if (spot.getSpotId() == spotId) {
                distance = Double.parseDouble(spot.getSpotDistance());
            }
        }
        return distance;
    }

    double getSpotDistanceByOrder(int order) {
        double distance = 0;
        for (Spot spot : list_spot) {
            if (spot.getOrderNumber() == order) {
                distance = Double.parseDouble(spot.getSpotDistance());
            }
        }
        return distance;
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
            final int spotID = spot.getSpotId();
            if (checked) {
                //check in first spot
                Date timeNow = new Date();
                lastTimeCheckin = timeNow;
                saveCourseRunning.setLastTimeCheckin(lastTimeCheckin);
                CheckInStampAPI.postCheckInStamp(token, spot.getSpotId(), new ServiceCallback() {
                    @Override
                    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                        JSONObject jsonObject = (JSONObject) response;
                        if (!jsonObject.has("error")) {
                            Stamp model = Stamp.getData(response.toString());

                            if (model.getSuccess()) {
                                if (model.getGained()) {
                                    isSaveTime = false;
                                    preferencesUtils.setLongValue(KEY_SHARED_BASETIME, chronometer.getBase());
                                    String imgUrl = model.getImage() == null ? "" : model.getImage();
                                    String title = model.getTitle() == null ? "" : model.getTitle();
                                    String finish_time = getTimeFormat(time);
//                                for test animation
//                                if(spotId==181) model.setGained( true );

                                    mActivity.showCheckPointFragment(spotID, imgUrl, title, "", "", model.getGained(), true);
                                    isTimeSaved = true;
                                }

                            }
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }
            checked = false;
        }
        String s = new ClassToJson<SaveCourseRunning>().getStringClassJson(saveCourseRunning);
        SharedPreferencesUtils.getInstance(getContext()).setStringValue(Constant.SAVED_COURSE_RUNNING, s);
        fragmentLog.updateCheckedSpot(saveCourseRunning);
    }

    void saveCheckedSpot() {
        String s = new ClassToJson<SaveCourseRunning>().getStringClassJson(saveCourseRunning);
        SharedPreferencesUtils.getInstance(getContext()).setStringValue(Constant.SAVED_COURSE_RUNNING, s);
    }

    private boolean isSpotChecked(int spotId, int order) {
        for (int i = 0; i < saveCourseRunning.getLstCheckedSpot().size(); i++) {
            if (saveCourseRunning.getLstCheckedSpot().get(i).getSpotID() == spotId && saveCourseRunning.getLstCheckedSpot().get(i).getOrderNumber() == order && saveCourseRunning.getLstCheckedSpot().get(i).isChecked()) {
                Log.i("running566", "" + spotId);
                return true;

            }
        }
        Log.i("running566-", "" + spotId);
        return false;
    }

    private void checkedSpot(int spotId, int order, String finishTime, double averageSpeed, long lastCheckTime) {
        for (int i = 0; i < saveCourseRunning.getLstCheckedSpot().size(); i++) {
            if (saveCourseRunning.getLstCheckedSpot().get(i).getSpotID() == spotId && saveCourseRunning.getLstCheckedSpot().get(i).getOrderNumber() == order) {
                saveCourseRunning.getLstCheckedSpot().get(i).setChecked(true);
                if (order > saveCourseRunning.getHighestCheckedSpot()) {
                    saveCourseRunning.setHighestCheckedSpot(order);
                    saveCourseRunning.getLstCheckedSpot().get(i).setHighestChecked(true);
                }

//                if (i == saveCourseRunning.getLastCheckedOrder() + 1) {
//                    saveCourseRunning.getLstCheckedSpot().get(saveCourseRunning.getLastCheckedOrder()).setAvarageSpeed(averageSpeed);
//                    saveCourseRunning.getLstCheckedSpot().get(saveCourseRunning.getLastCheckedOrder()).setTime(finishTime);
//                }

                if (i > 0) {
                    saveCourseRunning.getLstCheckedSpot().get(i - 1).setAvarageSpeed(averageSpeed);
                    saveCourseRunning.getLstCheckedSpot().get(i - 1).setTime(finishTime);
                    saveCourseRunning.getLstCheckedSpot().get(i - 1).setTurnOffAnim(true);

                }
                saveCourseRunning.setLastCheckedOrder(i);
                saveCourseRunning.setLastCheckedTime(lastCheckTime);
            }
        }

        fragmentLog.updateCheckedSpot(saveCourseRunning);
        saveCheckedSpot();
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

    void saveGoalValue(int idSpot, float speed, String time, String imgUrl, String title, String distance) {
        saveCourseRunning.setGoalSpotId(idSpot);
        saveCourseRunning.setAvarageSpeed(speed);
        saveCourseRunning.setImgUrlGoal(imgUrl);
        saveCourseRunning.setGoal_title(title);
        saveCourseRunning.setAllDistance(distance);
        saveCheckedSpot();
    }

    ArrayList<Location> getListUncheckedSpot() {
        ArrayList<Location> newList = new ArrayList<>();
        for (Spot spot : list_spot) {
            int id = spot.getSpotId();
            int order = spot.getOrderNumber();

            if (order > 0 && !isSpotChecked(id, order)) {
                newList.add(new Location(spot.getSpotId(), Double.parseDouble(spot.getLatitude()), Double.parseDouble(spot.getLongitude()), spot.getOrderNumber()));
            }

        }
        return newList;
    }

}
