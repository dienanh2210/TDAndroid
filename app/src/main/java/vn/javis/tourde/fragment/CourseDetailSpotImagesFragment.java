package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.DetailSpotActivity;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.customlayout.TourDeTabLayout;
import vn.javis.tourde.model.SpotData;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.view.YourScrollableViewPager;

public class CourseDetailSpotImagesFragment extends BaseFragment implements ServiceCallback{
    @BindView(R.id.tab_layout)
    TourDeTabLayout tab_layout;
    @BindView(R.id.course_view_pager)
    YourScrollableViewPager view_pager;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.tv_address)
    TextView txtAddress;
    @BindView(R.id.tv_site_url)
    TextView txtSiteURL;
    @BindView(R.id.tv_tel)
    TextView txtTel;
    @BindView(R.id.tv_tag)
    TextView txtTag;
    CourseListActivity mActivity;


    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mActivity = (CourseListActivity) getActivity();

        //GetCourseDataAPI.getCourseData(1,this);
        tab_layout.setOnTabChangeListener(new TourDeTabLayout.SCTabChangeListener() {
            @Override
            public void onTabChange(int index, boolean isScroll) {
                view_pager.setCurrentItem(index);
            }
        });
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
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

        int spotId = mActivity.getmSpotID();
        SpotDataAPI.getCourseData(spotId, this);
    }


    @Override
    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
        SpotData spotData = SpotData.getSpotData(response.toString());
        Log.i("spot json",response.toString());
        txtTitle.setText(spotData.getData().getTitle());
        txtAddress.setText(spotData.getData().getAddress());
        txtSiteURL.setText(spotData.getData().getSiteUrl());
        txtTel.setText(spotData.getData().getTel());
        txtTag.setText(spotData.getTag().toString());

    }

    @Override
    public void onError(VolleyError error) {

    }
    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.course_detail_spot_fragment, container, false);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TabSpotImages();
                case 1:
                    return new TabSpotFacility();
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
