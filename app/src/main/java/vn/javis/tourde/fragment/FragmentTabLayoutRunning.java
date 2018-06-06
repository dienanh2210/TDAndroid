package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.adapter.ListCheckInSpot;
import vn.javis.tourde.adapter.ListSpotLog;
import vn.javis.tourde.adapter.ViewPagerAdapter;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class FragmentTabLayoutRunning extends BaseFragment {
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.show_select_spot)
    LinearLayout show_select_spot;

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
    private FragmentTabLayoutRunning.OnFragmentInteractionListener listener;
    //  TextView tv_back_password;
    private RegisterActivity activity;

    public static FragmentTabLayoutRunning newInstance(ListCheckInSpot.OnItemClickedListener listener) {
        FragmentTabLayoutRunning fragment = new FragmentTabLayoutRunning();
//        fragment.listener = (CheckPointFragment.OnFragmentInteractionListener) listener;
        return fragment;
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                chronometer.setText(t);
            }
        });

        time = SystemClock.elapsedRealtime();
        chronometer.setBase(time);
        chronometer.setText("00:00:00");
        chronometer.start();
        Log.i("timer",""+SystemClock.elapsedRealtime());
        initTabControl();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        // PicassoUtil.getSharedInstance(mActivity).load("").transform(new CircleTransform()).into(imageCheckinSport);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        spotRecycler.setItemAnimator(new DefaultItemAnimator());
        spotRecycler.setLayoutManager(layoutManager);

        final int courseId = mActivity.getmCourseID();
        GetCourseDataAPI.getCourseData(courseId, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;

                CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);

                List<Spot> list_spot = mCourseDetail.getSpot();
                listSpotCheckinAdapter = new ListCheckInSpot(list_spot, mActivity);
                listSpotCheckinAdapter.setOnItemClickListener(new ListCheckInSpot.OnItemClickedListener() {
                    @Override
                    public void onItemClick(int position) {
                        mActivity.openPage(new CheckPointFragment(),true,false);
                    }
                });
                spotRecycler.setAdapter(listSpotCheckinAdapter);


            }

            @Override
            public void onError(VolleyError error) {

            }
        });

        mActivity.fn_permission();
    }

    @OnClick({R.id.btn_back, R.id.stop_time, R.id.resume})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                mActivity.openPage(new CourseDetailFragment(), true,false);
                break;
            case R.id.stop_time:
                chronometer.stop();
                pauseOffset = time - chronometer.getBase();
                stopTime.setVisibility(View.GONE);
                //temporary open select spot to checkin
                show_select_spot.setVisibility(View.VISIBLE);
                mActivity.turnOffGPS();
                break;
            case R.id.resume:
                chronometer.setBase(time - pauseOffset);
                chronometer.start();
                stopTime.setVisibility(View.VISIBLE);
                show_select_spot.setVisibility(View.GONE);

                mActivity.turnOnGPS();
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
        adapter.addFragment(new FragmentLog(), "ログ");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

    }


    public interface OnFragmentInteractionListener extends View.OnClickListener {
        @Override
        void onClick(View v);
    }
}
