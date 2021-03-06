package vn.javis.tourde.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.customlayout.TourDeTabLayout;
import vn.javis.tourde.model.SpotData;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;
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
    @BindView( R.id.btn_share )
     ImageButton btn_share;
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
    int spotId=0,indexTab=0;
    String token = SharedPreferencesUtils.getInstance(getContext()).getStringValue(LoginUtils.TOKEN);
    @BindView(R.id.img_home)
    ImageView imgHomeBtn;
    @BindView(R.id.txt_home)
    TextView txtHomeBtn;
    @BindView( R.id.ln_googlemap )
   LinearLayout ln_googlmap;
    @BindView( R.id.ln_browser )
    LinearLayout ln_browser;
    @BindView( R.id.ln_call )
     LinearLayout ln_call;
    String url = "";
    boolean istrue;
    List<String> listSpotImage= new ArrayList<>();
    @Nullable

    public static CourseDetailSpotImagesFragment newInstance(View.OnClickListener listener) {
        CourseDetailSpotImagesFragment fragment = new CourseDetailSpotImagesFragment();
        //  fragment.listener = (OnFragmentInteractionListener) listener;
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        TourDeApplication.getInstance().trackScreenView("screen_spot_id="+spotId);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        mActivity = (CourseListActivity) getActivity();
        spotId = getArguments().getInt(CourseListActivity.SPOT_ID);

        url="https://www.app-tour-de-nippon.jp/test/course/spot/?spot_id="+spotId;
        if(mActivity.typeBackPress==3)
        {
            indexTab=1;
            mActivity.typeBackPress=0;
        }
        //GetCourseDataAPI.getCourseData(1,this);
        tab_layout.setOnTabChangeListener(new TourDeTabLayout.SCTabChangeListener() {
            @Override
            public void onTabChange(int index, boolean isScroll) {
                view_pager.setCurrentItem(index);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
        imgHomeBtn.setBackground(getResources().getDrawable(R.drawable.icon_homeclick));
        txtHomeBtn.setTextColor(getResources().getColor(R.color.SkyBlue));
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showCourseListPage();

            }
        });
        btnBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(token))
                    mActivity.showBadgeCollection();
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
        btnMyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showMyCourse();
            }
        });
        Log.i("DetailSpotImages128",""+spotId);
       showProgressDialog();
        SpotDataAPI.getSpotData(spotId, this);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = txtTitle.getText().toString();
            //    String url=txtSiteURL.getText().toString();
                String share = url;
                myIntent.putExtra(Intent.EXTRA_TEXT, "#ツールド"+"\n"+"スポット紹介："+shareBody + "\n" + share);
              //  myIntent.putExtra(Intent.EXTRA_TEXT,"" );
                startActivity(Intent.createChooser(myIntent, ""));
            }
        });

    }

    @Override
    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
        JSONObject jsonObject =(JSONObject)response;
        if(jsonObject.has("error"))
            return;
        final SpotData spotData = SpotData.getSpotData(response.toString());
        if(spotData==null)
            return;
        Log.i("detail spot img 130", response.toString());
        if(txtTitle==null)
            return;

        txtTitle.setText(spotData.getData().getTitle());
        txtAddress.setText(spotData.getData().getAddress());
        txtSiteURL.setText(spotData.getData().getSiteUrl());
        txtTel.setText(spotData.getData().getTel());

        String tags = "";
        for (String s : spotData.getTag()) {
            tags += "#" + s + "     ";
        }
        txtTag.setText(tags);
        listSpotImage = spotData.getImages();
        if (spotData.getData().getTopImage() != null && spotData.getData().getTopImage() != "")
            PicassoUtil.getSharedInstance(mActivity).load(spotData.getData().getTopImage()).resize(0, 500).onlyScaleDown().into(imgCourse);

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());

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
        view_pager.setAdapter(pagerAdapter);
        view_pager.setCurrentPageNumber(indexTab);
        view_pager.setCurrentItem(indexTab);
       hideProgressDialog();


        ln_call.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String phone = "+34666777888";
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
//                startActivity(intent);
                String phone = txtTel.getText().toString();
                if(!TextUtils.isEmpty( phone )) {
                    Intent phoneIntent = new Intent( Intent.ACTION_DIAL, Uri.fromParts(
                            "tel", phone, null ) );
                    startActivity( phoneIntent );
                }
            }
        } );
            ln_browser.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url=txtSiteURL.getText().toString();
                    if(!TextUtils.isEmpty( url )) {
                        Intent i = new Intent( Intent.ACTION_VIEW );
                        i.setData( Uri.parse( url ) );
                        startActivity( i );
                    }
                }
            } );
        ln_googlmap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  String uri = "http://maps.google.com/maps?daddr=" + 12f + "," + 2f + " (" + "Where the party is at" + ")";
              //  String uri =txtAddress.getText().toString();
//                double latitude = Double.parseDouble(spotData.getData().getLatitude() );
//                double longtitude = Double.parseDouble(spotData.getData().getLongitude() );

                float latitude = Float.parseFloat(spotData.getData().getLatitude() );
                float longtitude =  Float.parseFloat(spotData.getData().getLongitude() );

                String url = spotData.getData().getGoogleMapUrl();
                String uri = String.format( Locale.ENGLISH, url+"?daddr=", latitude, longtitude, "");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    try {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    } catch (ActivityNotFoundException innerEx) {
                        //Toast.makeText(getContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        } );
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
        private int mCurrentPosition = -1;
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TabSpotImages tabSpotImages =new TabSpotImages();
                    Bundle dataBundle1 = new Bundle();
                    dataBundle1.putSerializable(Constant.LIST_SPOT_IMAGE, (Serializable) listSpotImage);
                    dataBundle1.putInt(CourseListActivity.SPOT_ID, spotId);
                    tabSpotImages.setArguments(dataBundle1);
                    return tabSpotImages;
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

}
