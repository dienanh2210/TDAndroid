package vn.javis.tourde.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.BadgeCollectionActivity;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.DetailSpotActivity;
import vn.javis.tourde.customlayout.TourDeTabLayout;
import vn.javis.tourde.view.YourScrollableViewPager;

public class CourseDetailSpotOneFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    DetailSpotActivity activity;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.course_detail_spot_one_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        activity = (DetailSpotActivity) getActivity();
        //GetCourseDataAPI.getCourseData(1,this);
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);

      //  activity.setSupportActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("ユーザー投稿写真"));
        tabLayout.addTab(tabLayout.newTab().setText("自分の投稿写真"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Initializing viewPager
        viewPager = (ViewPager)view.findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(activity.getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}
