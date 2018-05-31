package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.customlayout.TourDeTabLayout;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.model.CourseData;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TourDeService;
import vn.javis.tourde.utils.BinaryConvert;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.view.CircleTransform;
import vn.javis.tourde.view.YourScrollableViewPager;

public class CourseDetailFragment extends BaseFragment implements ServiceCallback {
    private int mCourseID;
    private CourseListActivity mActivity;
    List<String> listImgUrl = new ArrayList<>();
    @BindView(R.id.btn_back_to_list)
    ImageButton btnBackToList;
    @BindView(R.id.btn_share)
    ImageButton btnShare;

    @BindView(R.id.title_detail)
    TextView txtTitle;
    @BindView(R.id.txt_catch_phrase_detail)
    TextView txtCatchPhrase;
    @BindView(R.id.txt_introduction)
    TextView txtIntroduction;
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
    @BindView(R.id.txt_date)
    TextView txtDate;

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
    @BindView(R.id.btn_my_course_footer)
    RelativeLayout btnMyCourse;
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
    String url = "";
    String[] strCourseType = new String[]{"片道", "往復", "1周"};

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        // testAPI();
        //     mCourseID = mActivity.getmCourseID();
        mCourseID = getArguments().getInt(CourseListActivity.COURSE_DETAIL_ID);
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
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showCourseListPage();
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

        for (int i = 0; i < 20; i++) {

            listImgUrl.add("plus_button");
        }
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = txtTitle.getText().toString();
                String shareSub = txtTitle.getText().toString();
                String share = url;
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub + "\n" + share);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody + "\n" + share);

                startActivity(Intent.createChooser(myIntent, ""));
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

    @SuppressLint("SetTextI18n")
    void showCourseDetail(CourseDetail courseDetail) {
        final CourseData model = courseDetail.getmCourseData();

        if (model == null)
            return;
        if(txtTitle ==null)
            return;
        txtTitle.setText(model.getTitle());
        txtPostUser.setText(model.getPostUserName());
        txtCatchPhrase.setText(model.getCatchPhrase());
        txtIntroduction.setText(model.getIntroduction());
        txtReviewCount.setText(courseDetail.getReviewTotal().getReviewCount());
        txtSpotCount.setText("" + courseDetail.getSpot().size());
        txtArea.setText(model.getArea());
        txtDistance.setText(model.getDistance());
        String strMonths = model.getSeason();
        Log.i("Course Detail 255",""+strMonths);
        try {
            int number = Integer.valueOf(model.getSeason());
            strMonths = BinaryConvert.convertToMonths(number)+"月";
            Log.i("Course Detail 255",""+strMonths);
        }
        catch (Exception e){

        }
        txtSeason.setText(strMonths);
        txtAverageSlope.setText(model.getAverageSlope());
        txtElevation.setText(model.getElevation() + "m");
        int courseType = model.getCourseType();
        txtCourseType.setText(strCourseType[courseType - 1]);

        String dateGet = model.getDisplayDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = dateFormat.parse(dateGet);

            String out = dateFormat2.format(date).toString();
            txtDate.setText(out);
        } catch (ParseException e) {
        }
        List<String> listTag = courseDetail.getListTag();
        if (listTag.size() > 0) {
            String s = "";
            for (int i = 0; i < listTag.size(); i++) {
                s += "#" + listTag.get(i) + " ";
            }
            txtTag.setText(s);
        }

        isFavourite = false;
        FavoriteCourseAPI.getListFavoriteCourse(LoginFragment.getmUserToken(), new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                List<FavoriteCourse> listFavorCourse = FavoriteCourseAPI.getFavorites(response);
                for (int i = 0; i < listFavorCourse.size(); i++) {
                    if (listFavorCourse.get(i).getCourseId() == model.getCourseId()) {
                        isFavourite = true;
                        break;
                    }
                }
                if (isFavourite) {
                    btnFavorite.setBackground(getResources().getDrawable(R.drawable.icon_bicycle_red));

                    if (tabCourseFragment != null) {
                        tabCourseFragment.changeButtonColor(isFavourite);
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

        Log.i("FavoriteCourseAPI", "" + isFavourite);
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

        url = model.getRouteUrl();
    }

    @Override
    public void onSuccess(ServiceResult resultCode, Object response) {
        Log.i("GET COURSE API: ", response.toString());
        mCourseDetail = new CourseDetail((JSONObject) response);
        showCourseDetail(mCourseDetail);
        view_pager.setAdapter(pagerAdapter);
        if (tabCommentFragment != null) {
            tabCommentFragment.setListReview(mCourseDetail.getReview());
            tabCommentFragment.setRecyler();
        }


        // TabCourseFragment tabCourseFragment = (TabCourseFragment) pagerAdapter.getItem(0);
//
//        tabCourseFragment.setData("tabCourseFragment");

        // tabCommentFragment = (TabCommentFragment) pagerAdapter.getItem(1);


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
                    return tabCourseFragment = TabCourseFragment.instance(mCourseDetail.getmCourseData().getFinishTime(), mCourseDetail.getmCourseData().getAveragePace(), mCourseDetail.getmCourseData().getStartAddress(), mCourseDetail.getSpot());
//                    return TabSpotUploadedImages.intansce(listImgUrl);
                case 1:
                    return tabCommentFragment = TabCommentFragment.instance(mCourseDetail.getReview());
//                    return TabSpotUploadedImages.intansce(listImgUrl);
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
        String token = LoginFragment.getmUserToken();
        int course_id = mCourseID;
        if (isFavourite) {
            FavoriteCourseAPI.insertFavoriteCourse(token, course_id, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                    JSONObject jsonObject = (JSONObject) response;
                    if (jsonObject.has("success")) {
                        btnFavorite.setBackground(getResources().getDrawable(R.drawable.icon_bicycle_red));
                        if (tabCourseFragment != null) {
                            tabCourseFragment.changeButtonColor(isFavourite);
                        }
                    } else {
                        isFavourite = !isFavourite;
                        Log.i("is: ", "false");
                     //   Toast.makeText(getContext(), "エラーメッセージ", Toast.LENGTH_LONG).show();
                        ProcessDialog.showDialogOk(getContext(), "", "エラーメッセージ" );
                    }
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        } else {
            FavoriteCourseAPI.deleteFavoriteCourse(token, course_id, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                    JSONObject jsonObject = (JSONObject) response;
                    if (jsonObject.has("success")) {
                        btnFavorite.setBackground(getResources().getDrawable(R.drawable.icon_bicycle_gray));
                        if (tabCourseFragment != null) {
                            tabCourseFragment.changeButtonColor(isFavourite);
                        }
                    } else {
                        Log.i("is: ", "true");
                        isFavourite = !isFavourite;
                       // Toast.makeText(getContext(), "エラーメッセージ", Toast.LENGTH_LONG).show();
                        ProcessDialog.showDialogOk(getContext(), "", "エラーメッセージ" );
                    }
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }

    }

}
