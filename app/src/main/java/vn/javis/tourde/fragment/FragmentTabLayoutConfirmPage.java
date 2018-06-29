package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.adapter.ListCheckInSpot;
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

public class FragmentTabLayoutConfirmPage extends BaseFragment {
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.show_select_spot)
    RelativeLayout show_select_spot;
    CourseListActivity mActivity;
    @BindView(R.id.select_spot)
    RecyclerView spotRecycler;
    ListCheckInSpot listSpotCheckinAdapter;
    double latitude;
    double longtitude;
    int courseID;
    int lastSpotId;
    float courseDistance;
    boolean changePaged;

    private FragmentTabLayoutConfirmPage.OnFragmentInteractionListener listener;
    ArrayList<Location> lstLocation = new ArrayList<>();
    List<Spot> list_spot = new ArrayList<>();
    SaveCourseRunning saveCourseRunning;
    FragmentLog fragmentLog;
    FragmentMap fragmentMap;
    String token = SharedPreferencesUtils.getInstance(getContext()).getStringValue(LoginUtils.TOKEN);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (CourseListActivity) getActivity();
        courseID = mActivity.getmCourseID();

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initTabControl();

        // PicassoUtil.getSharedInstance(mActivity).load("").transform(new CircleTransform()).into(imageCheckinSport);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        spotRecycler.setItemAnimator(new DefaultItemAnimator());
        spotRecycler.setLayoutManager(layoutManager);
        show_select_spot.setVisibility(View.GONE);
        spotRecycler.setAdapter(listSpotCheckinAdapter);
        final int courseId = mActivity.getmCourseID();
        list_spot.clear();
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
                mActivity.setMapUrl(mCourseDetail.getmCourseData().getKmlFile());

                list_spot = mCourseDetail.getSpot();
                if (list_spot.size() > 0) {

                    longtitude = Double.parseDouble(list_spot.get(0).getLongitude());
                    latitude = Double.parseDouble(list_spot.get(0).getLatitude());
                    lastSpotId = list_spot.get(list_spot.size() - 1).getSpotId();

                    listSpotCheckinAdapter = new ListCheckInSpot(list_spot, mActivity);
                    setupViewPager();
                    tabLayout.setupWithViewPager(viewPager);
                    listSpotCheckinAdapter.notifyDataSetChanged();
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
        }
    };




    @OnClick({R.id.confirm_logging, R.id.end_running})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_logging:
                mActivity.onBackPressed();
                break;
            case R.id.end_running:

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
        mActivity.unregisterReceiver(broadcastReceiver);
        mActivity.unregisterReceiver(broadcastReceiverArried);
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
        fragmentMap = new FragmentMap();
        adapter.addFragment(fragmentMap, "MAP");
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

}
