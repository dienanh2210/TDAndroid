package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.MainActivity;
import vn.javis.tourde.adapter.ViewPagerAdapter;

public class FragmentTabLayoutRunning extends BaseFragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View mView;
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
                break;
            case R.id.resume:

                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                stopTime.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.running,container,false);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        tabLayout = (TabLayout) mView.findViewById(R.id.tabs);
        viewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        initTabControl();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new FragmentMap(),"MAP");
        adapter.addFragment(new FragmentLog(),"ログ");

        viewPager.setAdapter(adapter);
    }
}
