package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
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
import vn.javis.tourde.model.SaveCourseRunning;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.services.TourDeService;
import vn.javis.tourde.utils.BinaryConvert;
import vn.javis.tourde.utils.ClassToJson;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.utils.TimeUtil;
import vn.javis.tourde.view.CircleTransform;
import vn.javis.tourde.view.YourScrollableViewPager;

import static vn.javis.tourde.fragment.FragmentTabLayoutRunning.KEY_SHARED_BASETIME;

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
    @BindView(R.id.webView_introduction)
    WebView webView;
    @BindView(R.id.btn_bicyle)
    ImageButton btn_bicyle;
    @BindView(R.id.btn_bicyle_red)
    ImageButton btn_bicyle_red;
    boolean isFavourite;
    private CourseDetail mCourseDetail;
    PagerAdapter pagerAdapter;

    TabCourseFragment tabCourseFragment;
    TabCommentFragment tabCommentFragment;
    String url = "";
    String[] strCourseType = new String[]{"片道", "往復", "1周"};
    int indexTab;
    //String htmlText = "<html><body style=\"font-size:%spx; text-align:justify; color: black; margin: 0; padding: 0; line-height: 1.5\"> %s </body></Html>";
    String htmlText = "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/HiraKakuPro-W3.otf\")}body,* {font-family: MyFont;text-align: justify;line-height: 1.5;margin: 0; padding: 0}</style>";

    int fontSize;
    int line;
    private boolean isNextScreen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (CourseListActivity) getActivity();
        if (SharedPreferencesUtils.getInstance(getContext()).getLongValue(KEY_SHARED_BASETIME) != 0 && isNextScreen) {
            mActivity.openPage(FragmentTabLayoutRunning.newInstance(true), CourseDetailFragment.class.getSimpleName(), true, false);

//            if (SharedPreferencesUtils.getInstance(mActivity).getBooleanValue(Constant.KEY_GOAL_PAGE)) {
//                if (saveCourseRunning != null) {
//                    mActivity.openPage(GoalFragment.newInstance(saveCourseRunning.getCourseID(), saveCourseRunning.getGoalSpotId(), saveCourseRunning.getAvarageSpeed(), TimeUtil.getTimeFormat(saveCourseRunning.getLastCheckedTime()), saveCourseRunning.getImgUrlGoal(), saveCourseRunning.getGoal_title(), saveCourseRunning.getAllDistance(), true), CourseDetailFragment.class.getSimpleName(), true, false);
//                }
//            } else {
//                mActivity.openPage(FragmentTabLayoutRunning.newInstance(true), CourseDetailFragment.class.getSimpleName(), true, false);
            //            }
        } else if (isNextScreen) {
            mActivity.openPage(new CourseDriveFragment(), CourseDetailFragment.class.getSimpleName(), true, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        TourDeApplication.getInstance().trackScreenView("screen_course_id=" + mCourseID);
    }

    public static CourseDetailFragment newInstance(boolean isNextScreen) {
        CourseDetailFragment fragment = new CourseDetailFragment();
        fragment.isNextScreen = isNextScreen;
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        mCourseDetail =null;
//        tabCourseFragment=null;
//        tabCommentFragment=null;
//        indexTab=0;
//        pagerAdapter =null;
        showProgressDialog();
        // testAPI();
        mCourseID = mActivity.getmCourseID();
        url="https://www.app-tour-de-nippon.jp/test/course/?course_id="+mCourseID;
//        mCourseID = getArguments().getInt(CourseListActivity.COURSE_DETAIL_ID);

        if (SharedPreferencesUtils.getInstance(getContext()).getLongValue(KEY_SHARED_BASETIME) != 0) {
            btn_bicyle_red.setVisibility(View.VISIBLE);
            btn_bicyle.setVisibility(View.GONE);
        }else {
            btn_bicyle_red.setVisibility(View.GONE);
            btn_bicyle.setVisibility(View.VISIBLE);
        }

        indexTab = getArguments().getInt(CourseListActivity.COURSE_DETAIL_INDEX_TAB);
        String savedString = SharedPreferencesUtils.getInstance(getContext()).getStringValue(Constant.SAVED_COURSE_RUNNING);

        btn_bicyle_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.openPage(new FragmentTabLayoutRunning(), CourseDetailFragment.class.getSimpleName(), true, false);
            }
        });

//        final WebSettings webSettings = webView.getSettings();
//        webSettings.setUseWideViewPort(true);
//        webView.setInitialScale(1);
//        webSettings.setLoadWithOverviewMode(true);
        Resources res = getResources();
        fontSize = res.getInteger(R.integer.txtSizeWebView);
        Log.i("fontSize", "" + fontSize);
//        webSettings.setDefaultFontSize((int)fontSize);

        indexTab = indexTab < 0 ? 0 : indexTab;
        if (mActivity.typeBackPress == 1) {
            indexTab = 1;
            mActivity.typeBackPress = 0;
        }
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
                Log.i("test_tab_scroll", "1--" + position);
            }

            @Override
            public void onPageSelected(int position) {
                tab_layout.highLightTab(position);

                Log.i("test_tab_scroll", "2");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("test_tab_scroll", "3-" + state);
            }

        });

        btnBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mActivity.showCourseListPage();
                mActivity.onBackPressed();
            }
        });
        btnBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(token)) {
                    mActivity.showBadgeCollection();
                } else {
                    ProcessDialog.showDialogLogin(getContext(), "", "この機能を利用するにはログインをお願いいたします", new ProcessDialog.OnActionDialogClickOk() {
                        @Override
                        public void onOkClick() {
                            mActivity.openLoginPage();
                        }
                    });
                }
            }
        });
        btnMyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(token))
                    mActivity.showMyCourse();
                else {
                    ProcessDialog.showDialogLogin(getContext(), "", "この機能を利用するにはログインをお願いいたします", new ProcessDialog.OnActionDialogClickOk() {
                        @Override
                        public void onOkClick() {
                            mActivity.openLoginPage();
                        }
                    });
                }
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
                btnFavoriteClick(false);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = txtTitle.getText().toString();
                String shareSub = txtTitle.getText().toString();
                String share = url;
                //  myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub + "\n" + share);
                myIntent.putExtra(Intent.EXTRA_TEXT, "#ツールド" + "\n" + "コース紹介：" + shareBody + "\n" + share);

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
        if (txtTitle == null)
            return;
        mActivity.setMapUrl(model.getKmlFile());
        mActivity.setRoute_url(model.getRouteUrl());
        txtTitle.setText(model.getTitle());
        txtPostUser.setText(model.getPostUserName());
        txtCatchPhrase.setText(model.getCatchPhrase());
//        txtIntroduction.setText(model.getIntroduction());
        //webView.loadData(String.format(htmlText, "" + fontSize, model.getIntroduction()), "text/html; charset=utf-8", "utf-8");
        webView.loadDataWithBaseURL("", htmlText + String.format("<div style=\"font-size:%spx;color: black \">", fontSize) + model.getIntroduction() + "</div>", "text/html", "utf-8", null);


        txtReviewCount.setText(courseDetail.getReviewTotal().getReviewCount());
        txtSpotCount.setText("" + courseDetail.getSpot().size());
        txtArea.setText(model.getArea());
        txtDistance.setText(model.getDistance() + "km");
        String strMonths = model.getSeason();
        Log.i("Course Detail 255", "" + strMonths);
        try {
            int number = Integer.valueOf(model.getSeason());
            strMonths = BinaryConvert.convertToMonths(number) + "月";
            Log.i("Course Detail 255", "" + strMonths);
        } catch (Exception e) {

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
                s += "#" + listTag.get(i) + "     ";
            }
            txtTag.setText(s);
        }
        isFavourite = false;
        FavoriteCourseAPI.getListFavoriteCourse(token, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                List<FavoriteCourse> listFavorCourse = FavoriteCourseAPI.getFavorites(response);
                for (int i = 0; i < listFavorCourse.size(); i++) {
                    if (listFavorCourse.get(i).getCourseId() == model.getCourseId()) {
                        isFavourite = true;
                        break;
                    }
                }
                if (isFavourite && btnFavorite != null) {
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
        // PicassoUtil.getSharedInstance(activity).load(model.getTopImage())
        //        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
        //        .networkPolicy(NetworkPolicy.NO_CACHE).into(imgCourse);
        PicassoUtil.getSharedInstance(activity)
                .load(model.getTopImage())
                .resize(0, 600).onlyScaleDown()
                .into(imgCourse);
        PicassoUtil.getSharedInstance(activity).load(model.getPostUserImage()).resize(0, 100).onlyScaleDown().transform(new CircleTransform()).into(imgPostUser);
        float rate = courseDetail.getReviewTotal().getRatingAverage();

        if (rate >= 1 && rate < 1.5)
            imgStarRate.setImageResource(R.drawable.icon_star1);
        else if (rate >= 2 && rate < 2.5)
            imgStarRate.setImageResource(R.drawable.icon_star2);
        else if (rate >= 3 && rate < 3.5)
            imgStarRate.setImageResource(R.drawable.icon_star3);
        else if (rate >= 4 && rate < 4.5)
            imgStarRate.setImageResource(R.drawable.icon_star4);
        else if (rate >= 5)
            imgStarRate.setImageResource(R.drawable.icon_star5);
        else if (rate >= 0.5 && rate < 1)
            imgStarRate.setImageResource(R.drawable.icon_star0_5);
        else if (rate >= 1.5 && rate < 2)
            imgStarRate.setImageResource(R.drawable.icon_star1_5);
        else if (rate >= 2.5 && rate < 3)
            imgStarRate.setImageResource(R.drawable.icon_star2_5);
        else if (rate >= 3.5 && rate < 4)
            imgStarRate.setImageResource(R.drawable.icon_star3_5);
        else if (rate >= 4.5 && rate < 5)
            imgStarRate.setImageResource(R.drawable.icon_star4_5);
        else
            imgStarRate.setImageResource(R.drawable.icon_star0);


      //  url = model.getRouteUrl();
    }

    @Override
    public void onSuccess(ServiceResult resultCode, Object response) {
        JSONObject jsonObject = (JSONObject) response;
        if (!jsonObject.has("error")) {
            try {
                Log.i("GET COURSE API: ", response.toString());
                mCourseDetail = new CourseDetail((JSONObject) response);
                showCourseDetail(mCourseDetail);
                view_pager.setAdapter(pagerAdapter);
                view_pager.setCurrentPageNumber(indexTab);
                view_pager.setCurrentItem(indexTab);
                if (tabCommentFragment != null) {
                    tabCommentFragment.setListReview(mCourseDetail.getReview());
                    Log.i("COMMENT API detail350: ", mCourseDetail.getReview().toString());
                    tabCommentFragment.setRecyler();
                }
            } catch (Exception e) {
            }

        }
        // TabCourseFragment tabCourseFragment = (TabCourseFragment) pagerAdapter.getItem(0);
//
//        tabCourseFragment.setData("tabCourseFragment");

        // tabCommentFragment = (TabCommentFragment) pagerAdapter.getItem(1);
        hideProgressDialog();

    }

    @Override
    public void onError(VolleyError error) {

    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private int mCurrentPosition = -1;

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return tabCourseFragment = TabCourseFragment.instance(mCourseDetail.getmCourseData().getFinishTime(), mCourseDetail.getmCourseData().getAveragePace(), mCourseDetail.getmCourseData().getStartAddress(), mCourseDetail.getmCourseData().getRouteUrl(), mCourseDetail.getmCourseData().getRouteImage(), mCourseDetail.getSpot(), CourseDetailFragment.this);
//                    return TabSpotUploadedImages.intansce(listImgUrl);
                case 1:
                    return tabCommentFragment = TabCommentFragment.instance(mCourseDetail.getReview());
//                    return TabSpotUploadedImages.intansce(listImgUrl);
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
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    String token = SharedPreferencesUtils.getInstance(getContext()).getStringValue(LoginUtils.TOKEN);

    public void btnFavoriteClick(boolean inChild) {
        if (inChild && isFavourite) {
            // return;
        }
        isFavourite = !isFavourite;

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
                            //   mTracker.setScreenName("screen_course_id" + ""+mCourseID);
                            //  mTracker.send(new HitBuilders.EventBuilder().setCategory("tap_favorite_course_id="+mCourseID).build());
                            TourDeApplication.getInstance().trackEvent("tap_favorite_course_id=" + mCourseID, "tap", "tap_favorite_course_id=" + mCourseID);
                        }
                    } else {
                        isFavourite = !isFavourite;
                        Log.i("is: ", "false");
                        //   Toast.makeText(getContext(), "エラーメッセージ", Toast.LENGTH_LONG).show();
                        ProcessDialog.showDialogLogin(getContext(), "", "この機能を利用するにはログインをお願いいたします", new ProcessDialog.OnActionDialogClickOk() {
                            @Override
                            public void onOkClick() {
                                mActivity.openLoginPage();
                            }
                        });
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
                        ProcessDialog.showDialogLogin(getContext(), "", "この機能を利用するにはログインをお願いいたします", new ProcessDialog.OnActionDialogClickOk() {
                            @Override
                            public void onOkClick() {
                                mActivity.openLoginPage();
                            }
                        });
                    }
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }

    }

}
