package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListSpotCheckinAdapter;
import vn.javis.tourde.adapter.ViewPagerAdapter;
import vn.javis.tourde.apiservice.SpotCheckInAPI;
import vn.javis.tourde.model.SpotCheckIn;
import vn.javis.tourde.services.GoogleService;
import vn.javis.tourde.view.CircleTransform;

public class FragmentTabLayoutRunning extends BaseFragment{
    @BindView( R.id.tabs)
     TabLayout tabLayout;
    @BindView( R.id.viewpager)
     ViewPager viewPager;
    @BindView(R.id.show_select_spot)
    LinearLayout show_select_spot;

    private long pauseOffset;
   // private boolean running;

    @BindView(R.id.tv_time)
    android.widget.Chronometer chronometer;
    @BindView( R.id.finish_resume)
    LinearLayout finishResume;
    @BindView( R.id.stop_time)
    Button stopTime;
    @SuppressLint("SetTextI18n")
    CourseListActivity mActivity;
    @BindView(R.id.select_spot)
    RecyclerView spotRecycler;
    ListSpotCheckinAdapter listSpotCheckinAdapter;
    private FragmentTabLayoutRunning.OnFragmentInteractionListener listener;
    //  TextView tv_back_password;


    public static FragmentTabLayoutRunning newInstance(ListSpotCheckinAdapter.OnItemClickedListener listener) {
        FragmentTabLayoutRunning fragment = new FragmentTabLayoutRunning();
//        fragment.listener = (CheckPointFragment.OnFragmentInteractionListener) listener;
        return fragment;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
            chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                    int h = (int) (time / 3600000);
                    int m = (int) (time - h * 3600000) / 60000;
                    int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                    String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                    chronometer.setText(t);
                }
            });
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.setText("00:00:00");
            chronometer.start();

        initTabControl();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
       // Picasso.with(mActivity).load("").transform(new CircleTransform()).into(imageCheckinSport);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        spotRecycler.setItemAnimator(new DefaultItemAnimator());
        spotRecycler.setLayoutManager(layoutManager);
        SpotCheckInAPI spot = new SpotCheckInAPI();

        List<SpotCheckIn> list_courses = spot.getListSpot();

        listSpotCheckinAdapter = new ListSpotCheckinAdapter(list_courses, mActivity);
        listSpotCheckinAdapter.setOnItemClickListener( new ListSpotCheckinAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int position) {
                mActivity.openPage(CheckPointFragment.newInstance(this), true);
            }
        } );

        spotRecycler.setAdapter(listSpotCheckinAdapter);
    }

    @OnClick({R.id.btn_back, R.id.stop_time, R.id.resume})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                mActivity.openPage(new CourseDetailFragment(),true);
                break;
            case R.id.stop_time:
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                stopTime.setVisibility(View.GONE);
                //temporary open select spot to checkin
                show_select_spot.setVisibility(View.VISIBLE);
                Intent intent = new Intent(mActivity, GoogleService.class);
                mActivity.stopService(intent);
                break;
            case R.id.resume:
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                stopTime.setVisibility(View.VISIBLE);
                show_select_spot.setVisibility(View.GONE);
                Intent intent2 = new Intent(mActivity, GoogleService.class);
                mActivity.startService(intent2);
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
        adapter.addFragment(new FragmentMap(),"MAP");
        adapter.addFragment(new FragmentLog(),"ログ");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

    }


    public interface OnFragmentInteractionListener extends View.OnClickListener {
        @Override
        void onClick(View v);
    }
}
