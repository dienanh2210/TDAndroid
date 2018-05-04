package vn.javis.tourde.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.BadgeCollectionActivity;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.DetailSpotActivity;
import vn.javis.tourde.customlayout.TourDeTabLayout;
import vn.javis.tourde.view.YourScrollableViewPager;

public class CourseDetailSpotFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TourDeTabLayout tab_layout;
    @BindView(R.id.course_view_pager)
    YourScrollableViewPager view_pager;

    DetailSpotActivity activity;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.course_detail_spot_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        activity = (DetailSpotActivity) getActivity();

        //GetCourseDataAPI.getCourseData(1,this);
        tab_layout.setOnTabChangeListener(new TourDeTabLayout.SCTabChangeListener() {
            @Override
            public void onTabChange(int index, boolean isScroll) {
                view_pager.setCurrentItem(index);
            }
        });
        CourseDetailSpotFragment.PagerAdapter pagerAdapter = new CourseDetailSpotFragment.PagerAdapter(getChildFragmentManager());
        view_pager.setAdapter(pagerAdapter);
        view_pager.setOffscreenPageLimit(2);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab_layout.highLightTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    public class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new  CourseDetailSpotOneFragment();
                case 1:
                    return new CourseDetailSpotFourFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
