package vn.javis.tourde.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.BadgeCollectionActivity;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.customlayout.TourDeTabLayout;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.model.CourseData;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TourDeService;
import vn.javis.tourde.view.CircleTransform;
import vn.javis.tourde.view.YourScrollableViewPager;

public class CourseDetailFragment extends BaseFragment implements ServiceCallback {
    private int mCourseID;
    private CourseListActivity mActivity;

    @BindView(R.id.btn_back_to_list)
    ImageButton btnBackToList;
    @BindView(R.id.btn_share)
    ImageButton btnShare;

    @BindView(R.id.title_detail)
    TextView txtTitle;
    @BindView(R.id.txt_catch_phrase_detail)
    TextView txtCatchPhrase;
    @BindView(R.id.txt_area_detail)
    TextView txtArea;
    @BindView(R.id.txt_distance_detail)
    TextView txtDistance;

    @BindView(R.id.txt_season)
    TextView txtSeason;
    @BindView(R.id.txt_average_slope)
    TextView txtAverageSlope;
    @BindView(R.id.txt_elevation)
    TextView txtElevation;
    @BindView(R.id.txt_course_type)
    TextView txtCourseType;

    @BindView(R.id.txt_review_count_detail)
    TextView txtReviewCount;
    @BindView(R.id.txt_spot_count_detail)
    TextView txtSpotCount;
    @BindView(R.id.txt_post_user_detail)
    TextView txtPostUser;
    @BindView(R.id.img_course_detail)
    ImageView imgCourse;
    @BindView(R.id.star_rate)
    ImageView imgStarRate;
    @BindView(R.id.txt_tag_detail)
    TextView txtTag;
    @BindView(R.id.img_post_user_detail)
    ImageView imgPostUser;

    CourseListActivity activity;

    @BindView(R.id.course_view_pager)
    YourScrollableViewPager view_pager;
    @BindView(R.id.tab_layout)
    TourDeTabLayout tab_layout;
    @BindView(R.id.btn_badge_collection)
    RelativeLayout btnBadge;
    @BindView(R.id.btn_home_footer)
    RelativeLayout btnHome;
    @BindView(R.id.img_home)
    ImageView imgHomeBtn;
    @BindView(R.id.txt_home)
    TextView txtHomeBtn;
    @BindView(R.id.btn_favorite_detail)
    ImageButton btnFavorite;

    boolean isFavourite;
    private CourseDetail mCourseDetail;
    PagerAdapter pagerAdapter;

    TabCourseFragment tabCourseFragment;
    TabCommentFragment tabCommentFragment;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        // testAPI();
        mCourseID = mActivity.getmCourseID();

        GetCourseDataAPI.getCourseData(mCourseID, this);
        tab_layout.setOnTabChangeListener(new TourDeTabLayout.SCTabChangeListener() {
            @Override
            public void onTabChange(int index, boolean isScroll) {
                view_pager.setCurrentItem(index);
            }
        });

        pagerAdapter = new PagerAdapter(getChildFragmentManager());
//        view_pager.setAdapter(pagerAdapter);
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

        btnBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        btnBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, BadgeCollectionActivity.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
        imgHomeBtn.setBackground(getResources().getDrawable(R.drawable.icon_homeclick));
        txtHomeBtn.setTextColor(getResources().getColor(R.color.SkyBlue));
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFavoriteClick();
            }
        });
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.course_detail_fragment, container, false);
    }

    public CourseDetail getmCourseDetail() {
        return mCourseDetail;
    }

    private void setupViewPager(ViewPager viewPager) {


    }

    void showCourseDetail(CourseDetail courseDetail) {

        CourseData model = courseDetail.getmCourseData();

        txtTitle.setText(model.getTitle());
        txtPostUser.setText(model.getPostUserName());
        txtCatchPhrase.setText(model.getCatchPhrase());
        txtReviewCount.setText(courseDetail.getReviewTotal().getReviewCount());
        txtSpotCount.setText("" + courseDetail.getSpot().size());
        txtArea.setText(model.getArea());
        txtDistance.setText(model.getDistance());
        txtSeason.setText(model.getSeason());
        txtAverageSlope.setText(model.getAverageSlope());
        txtElevation.setText(model.getElevation() + "m");
        txtCourseType.setText(model.getCourseType());
        // txtTag.setText("#" + model.getTag());
        isFavourite = model.getStatus() == 1 ? true : false;
        Picasso.with(activity).load(model.getTopImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE).into(imgCourse);
        Picasso.with(activity).load(model.getPostUserImage()).transform(new CircleTransform()).into(imgPostUser);
        int rate = Math.round(courseDetail.getReviewTotal().getRatingAverage());
        if (rate == 1) {
            imgStarRate.setImageResource(R.drawable.icon_star1);
        } else if (rate == 2) {
            imgStarRate.setImageResource(R.drawable.icon_star2);
        } else if (rate == 3) {
            imgStarRate.setImageResource(R.drawable.icon_star3);
        } else if (rate == 4) {
            imgStarRate.setImageResource(R.drawable.icon_star4);
        } else if (rate >= 5) {
            imgStarRate.setImageResource(R.drawable.icon_star5);
        }
        if (isFavourite) {
            btnFavorite.setBackground(getResources().getDrawable(R.drawable.icon_bicycle_blue));
        }
    }

    @Override
    public void onSuccess(ServiceResult resultCode, Object response) {
        Log.i("GET COURSE API: ", response.toString());

        mCourseDetail = new CourseDetail((JSONObject) response);
        showCourseDetail(mCourseDetail);
        view_pager.setAdapter(pagerAdapter);

        // TabCourseFragment tabCourseFragment = (TabCourseFragment) pagerAdapter.getItem(0);
//
//        tabCourseFragment.setData("tabCourseFragment");

        // tabCommentFragment = (TabCommentFragment) pagerAdapter.getItem(1);

        if (tabCommentFragment != null) {
            tabCommentFragment.setListReview(mCourseDetail.getReview());
            tabCommentFragment.setRecyler();
        }
    }

    @Override
    public void onError(VolleyError error) {

    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return  TabCourseFragment.instance(mCourseDetail.getSpot());
                case 1:
                    return tabCommentFragment = TabCommentFragment.instance(mCourseDetail.getReview());
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private void btnFavoriteClick() {
        isFavourite = !isFavourite;
        String token = "";
        int course_id = 1;
        if (isFavourite) {
            FavoriteCourseAPI.insertFavoriteCourse(token, course_id, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                    btnFavorite.setBackground(getResources().getDrawable(R.drawable.icon_bicycle_blue));
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        } else {
            FavoriteCourseAPI.deleteFavoriteCourse(token, course_id, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                    btnFavorite.setBackground(getResources().getDrawable(R.drawable.icon_bicycle_gray));
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
    }

}
