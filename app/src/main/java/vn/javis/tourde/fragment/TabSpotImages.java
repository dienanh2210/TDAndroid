package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.view.YourScrollableViewPager;

public class TabSpotImages extends BaseFragment implements TabLayout.OnTabSelectedListener {

    CourseListActivity mActivity;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<String> listImgUrl;
    List<String> myListImgUrl = new ArrayList<>();
    int spotId;
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
        myListImgUrl.add("plus_button");
        //this is for test
        listImgUrl = (ArrayList<String>)getArguments().getSerializable(Constant.LIST_SPOT_IMAGE);
        spotId = getArguments().getInt(CourseListActivity.SPOT_ID);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        Log.i("listImgUrl",listImgUrl.toString());
        tabLayout.addTab(tabLayout.newTab().setText("ユーザー投稿写真"));
        tabLayout.addTab(tabLayout.newTab().setText("自分の投稿写真"));
        viewPager = view.findViewById(R.id.pager);

        Pager adapter = new Pager(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
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

        int tabCount;
        private int mCurrentPosition = -1;
        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount = tabCount;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TabSpotUploadedImages tab1 = TabSpotUploadedImages.intansce(listImgUrl);
                    return tab1;
                case 1:
                    TabMySpotUploadedImages tab2 = TabMySpotUploadedImages.intansce(myListImgUrl,spotId);
                    return tab2;
                default:
                    return null;
            }
        }
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if (position != mCurrentPosition) {
                Fragment fragment = (Fragment) object;
                YourScrollableViewPager pager = (YourScrollableViewPager) container;
                if (fragment != null && fragment.getView() != null) {
                    mCurrentPosition = position;
                    pager.measureCurrentView(fragment.getView());
                }
            }
        }
        @Override
        public int getCount() {
            return tabCount;
        }
    }

}
