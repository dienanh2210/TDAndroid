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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.DetailSpotActivity;
import vn.javis.tourde.adapter.ListCourseAdapter;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.customlayout.TourDeTabLayout;
import vn.javis.tourde.model.SpotData;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.view.YourScrollableViewPager;

public class CourseDetailSpotImagesFragment extends BaseFragment implements ServiceCallback {
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

    @BindView(R.id.btn_back_to_list)
    ImageButton btnBack;
    @BindView(R.id.img_course_detail)
    ImageView imgCourse;

    @BindView(R.id.btn_badge_collection)
    RelativeLayout btnBadge;
    @BindView(R.id.btn_my_course_footer)
    RelativeLayout btnMyCourse;
    @BindView(R.id.btn_home_footer)
    RelativeLayout btnHome;
    int spotId=0;
    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mActivity = (CourseListActivity) getActivity();
        spotId = getArguments().getInt(CourseListActivity.SPOT_ID);
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showCourseListPage();
            }
        });
        btnBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showBadgeCollection();
            }
        });
        btnMyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showMyCourse();
            }
        });
         SpotDataAPI.getCourseData(spotId, this);
    }


    @Override
    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
        SpotData spotData = SpotData.getSpotData(response.toString());
        Log.i("detail spot img 130", response.toString());
        txtTitle.setText(spotData.getData().getTitle());
        txtAddress.setText(spotData.getData().getAddress());
        txtSiteURL.setText(spotData.getData().getSiteUrl());
        txtTel.setText(spotData.getData().getTel());
        String tags ="";
        for (String s:spotData.getTag()) {
            tags+="#"+s+" ";
        }
        txtTag.setText(tags);
        Picasso.with(mActivity).load(spotData.getData().getTopImage()).into(imgCourse);


    }

    @Override
    public void onError(VolleyError error) {

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
                    TabSpotFacility tabSpotFacility = new TabSpotFacility();
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt(CourseListActivity.SPOT_ID, spotId);
                    tabSpotFacility.setArguments(dataBundle);
                    return tabSpotFacility;
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
