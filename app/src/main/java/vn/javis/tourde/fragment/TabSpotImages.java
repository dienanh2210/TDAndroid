package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.DetailSpotActivity;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.view.YourScrollableViewPager;

public class TabSpotImages extends BaseFragment implements TabLayout.OnTabSelectedListener {

    CourseListActivity mActivity;

    private TabLayout tabLayout;
    private YourScrollableViewPager viewPager;
    List<String> listImgUrl = new ArrayList<>();
    List<String> myListImgUrl = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.tab_spot_images, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        //GetCourseDataAPI.getCourseData(1,this);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        //  activity.setSupportActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("ユーザー投稿写真"));
        tabLayout.addTab(tabLayout.newTab().setText("自分の投稿写真"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Initializing viewPager
        viewPager = (YourScrollableViewPager) view.findViewById(R.id.pager);

        //Creating our pager adapter
        myListImgUrl.add("plus_button");
        for(int i=0;i<20;i++){
            myListImgUrl.add("plus_button");
            listImgUrl.add("plus_button");
        }
        Pager adapter = new Pager(mActivity.getSupportFragmentManager(), tabLayout.getTabCount());
        ImageView abc;

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

    public class Pager extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount = tabCount;
        }

        //Overriding method getItem
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    TabSpotUploadedImages tab1 = TabSpotUploadedImages.intansce(listImgUrl);
                    return tab1;
                case 1:
                    TabMySpotUploadedImages tab2 = TabMySpotUploadedImages.intansce(myListImgUrl);
                    return tab2;
                default:
                    return null;
            }
        }

        //Overriden method getCount to get the number of tabs
        @Override
        public int getCount() {
            return tabCount;
        }
    }

}
