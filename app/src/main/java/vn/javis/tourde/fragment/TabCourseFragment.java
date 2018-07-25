package vn.javis.tourde.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListSpotDetailCircleAdapter;

import vn.javis.tourde.model.SaveCourseRunning;
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.utils.ClassToJson;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class TabCourseFragment extends BaseFragment {


    @BindView(R.id.lv_list_spots)
    ListView lvSpot;
    @BindView(R.id.rcl_list_spots)
    RecyclerView rcllistspots;

    @BindView(R.id.btn_signup_favorite)
    RelativeLayout btnSignUp;
    @BindView(R.id.course_access_start_point)
    LinearLayout btnStartPoint;
    @BindView(R.id.txt_finish_time)
    TextView txtFinishTime;
    @BindView(R.id.txt_average_pace)
    TextView txtAvaragePace;
    @BindView(R.id.txt_start_address)
    TextView txtStartAddress;
    @BindView(R.id.btn_running_app)
    RelativeLayout btnRunningApp;
    @BindView(R.id.rlt_googlemap)
    RelativeLayout rlt_googlemap;
    @BindView(R.id.rlt_Navitime)
    RelativeLayout rlt_Navitime;
    @BindView(R.id.img_map)

    ImageView imgRoute;
    int mCourseID;
    private GoogleMap googleMap;
    ListSpotDetailCircleAdapter listSpotAdapter;
    CourseListActivity mActivity;
    List<Spot> listSpot = new ArrayList<>();
    String avagePace, finishTIme, startAddress, routeUrl, routeImg;
    CourseDetailFragment parentFragment;
    String token = SharedPreferencesUtils.getInstance(getContext()).getStringValue(LoginUtils.TOKEN);


    public static TabCourseFragment instance(List<Spot> lstSpot, CourseDetailFragment parentFragment) {
        TabCourseFragment fragment = new TabCourseFragment();
        fragment.listSpot = lstSpot;
        fragment.parentFragment = parentFragment;
        return fragment;
    }

    public static TabCourseFragment instance(String finishTime, String averagePace, String startAddress, String routeUrl, String routeImg, List<Spot> lstSpot, CourseDetailFragment parentFragment) {
        TabCourseFragment fragment = new TabCourseFragment();
        fragment.listSpot = lstSpot;
        fragment.finishTIme = finishTime;
        fragment.avagePace = processAveragePace(averagePace);
        fragment.startAddress = startAddress;
        fragment.parentFragment = parentFragment;
        fragment.routeUrl = routeUrl;
        fragment.routeImg = routeImg;

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (listSpot.size() > 0) {

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
            rcllistspots.setItemAnimator(new DefaultItemAnimator());
            rcllistspots.setLayoutManager(layoutManager);
            rcllistspots.setNestedScrollingEnabled(false);

            listSpotAdapter = new ListSpotDetailCircleAdapter(listSpot, mActivity);
            rcllistspots.setAdapter(listSpotAdapter);
            listSpotAdapter.setOnItemClickListener(new ListSpotDetailCircleAdapter.OnItemClickedListener() {
                @Override
                public void onItemClick(int spotID) {
//                    mActivity.showSpotImages(spotID);
                    mActivity.showSpotImages(spotID, CourseDetailFragment.class.getSimpleName());
                }
            });
            // rcllistspots.setOn

        }
        btnStartPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleMapAtStartSpot();
            }
        });
        txtAvaragePace.setText(avagePace + "km/h");


        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

        try {
            Date date = dateFormat.parse(finishTIme);
            Calendar timeConvert = Calendar.getInstance();
            timeConvert.setTime(date);
            String out = (timeConvert.get(Calendar.MINUTE) > 0 ? timeConvert.get(Calendar.HOUR) + "時間" + timeConvert.get(Calendar.MINUTE) + "分" : timeConvert.get(Calendar.HOUR) + "時間");
            Log.e("Time", out);
            txtFinishTime.setText(out);
        } catch (ParseException e) {
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(token)) {
                    ProcessDialog.showDialogLogin(getContext(), "", "この機能を利用するにはログインをお願いいたします", new ProcessDialog.OnActionDialogClickOk() {
                        @Override
                        public void onOkClick() {
                            mActivity.openLoginPage();
                        }
                    });
                } else {
                    if (parentFragment != null)
                        parentFragment.btnFavoriteClick(true);
                }

            }
        });
        txtStartAddress.setText(startAddress);
        if (listSpot.size() > 0) {
            startAddress = listSpot.get(0).getAddress();
            txtStartAddress.setText(startAddress);
        }
        imgRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(routeUrl));
                startActivity(browserIntent);
            }
        });
        PicassoUtil.getSharedInstance(mActivity).load(routeImg).resize(0, 500).onlyScaleDown().into(imgRoute);
        mCourseID = mActivity.getmCourseID();

        if (SharedPreferencesUtils.getInstance(getContext()).getLongValue(FragmentTabLayoutRunning.KEY_SHARED_BASETIME) == 0) {
            btnRunningApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(token)) {
                        ProcessDialog.showDialogLogin(getContext(), "", "この機能を利用するにはログインをお願いいたします", new ProcessDialog.OnActionDialogClickOk() {
                            @Override
                            public void onOkClick() {
                                mActivity.openLoginPage();
                            }
                        });
                    } else {
                        //    mActivity.showCourseDrive();
                        if (SharedPreferencesUtils.getInstance(getContext()).getStringValue("Checkbox") == "") {
                            String content = "運転中の画面操作・注視は、道路交通法又は、道路交通規正法に違反する可能性があります。画面の注視/操作を行う場合は安全な場所に停車し、画面の注視や操作を行ってください。 \n" +
                                    "\n" +
                                    "道路標識などの交通規制情報が実際の道路状況と異なる場合は、すべて現地の通行規制や標識の指示に従って走行してください";

                            ProcessDialog.showDialogcheckbox(getContext(), "ご利用にあたって", content, new ProcessDialog.OnActionDialogClickOk() {
                                @Override
                                public void onOkClick() {
                                    mActivity.showCourseDrive();

                                }
                            });
                        } else {
                            mActivity.showCourseDrive();
                        }
                    }

                }
            });
        } else {
            btnRunningApp.setBackground(mActivity.getResources().getDrawable(R.drawable.custom_frame_gray));
            String savedString = SharedPreferencesUtils.getInstance(getContext()).getStringValue(Constant.SAVED_COURSE_RUNNING);
            final SaveCourseRunning saveCourseRunning = new ClassToJson<SaveCourseRunning>().getClassFromJson(savedString, SaveCourseRunning.class);
            btnRunningApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCourseID == saveCourseRunning.getCourseID()) {
//                        mActivity.showFragmentTabLayoutRunning();
                        mActivity.openPage(new FragmentTabLayoutRunning(), CourseDetailFragment.class.getSimpleName(), true, false);
                    }
                    else
                    {
                        ProcessDialog.showDialogConfirm(getContext(), "", "走行中のコースがあります。\n 終了しますか？", new ProcessDialog.OnActionDialogClickOk() {
                            @Override
                            public void onOkClick() {
                                SharedPreferencesUtils.getInstance(getContext()).removeKey(FragmentTabLayoutRunning.KEY_SHARED_BASETIME);
                                SharedPreferencesUtils.getInstance(getContext()).removeKey(Constant.SAVED_COURSE_RUNNING);
                                SharedPreferencesUtils.getInstance(getContext()).removeKey(Constant.KEY_GOAL_PAGE);

                                mActivity.showCourseDrive();
                            }
                        });
                    }
                }
            });
        }
        rlt_googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   openGoogleMapAtStartSpot();
                try {
                    String uri1 = "";
                    if(listSpot.size()>0) {
                        for (Spot spot : listSpot) {
//                            float latitude = Float.parseFloat( spot.getLatitude() );
//                            float longtitude = Float.parseFloat( spot.getLongitude() );
                            float latitude = Float.parseFloat( spot.getLatitude() );
                            float longtitude = Float.parseFloat( spot.getLongitude() );
                            uri1 += "/" + latitude + "," + longtitude;
//                        uri += "/" +  mActivity.getLatitudeNetWork() + "," + mActivity.getLongitudeNetWork();
                            Log.i( "URI", "https://www.google.com/maps/dir" + uri1 );

                        }
                    }
                    Intent intent = new Intent( android.content.Intent.ACTION_VIEW, Uri.parse( "https://www.google.com/maps/dir" + uri1 ) );
                    intent.setPackage( "com.google.android.apps.maps" );
                    startActivity( intent );
                } catch (ActivityNotFoundException ex) {
                    //   Toast.makeText( getContext(), "Please install a maps application", Toast.LENGTH_LONG ).show();
                }
            }
        });

        rlt_Navitime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = "com.navitime.local.navitime";
                // String packageName = "NAVITIME: 地図・ルート検索";
                launchNewActivity(getContext(), packageName);
            }
        });


    }

    public void launchNewActivity(Context context, String packageName) {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
            intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        }
        if (intent == null) {
            try {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.navitime.local.navitime&hl=ja" + packageName));
                context.startActivity(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.navitime.local.navitime&hl=ja " + packageName)));

            }
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }


    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.tab_course, container, false);
    }

    public void changeButtonColor(boolean isFavor) {
        if (isFavor)
            btnSignUp.setBackground(mActivity.getResources().getDrawable(R.drawable.custom_frame_gray));
        else
            btnSignUp.setBackground(mActivity.getResources().getDrawable(R.drawable.custom_frame));
    }

    private static String processAveragePace(String s) {
        if (s.contains(",")) {
            s = s.replace(",", "");
        }
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat(".00", otherSymbols);
        return String.valueOf(df.format(Double.valueOf(s)));
    }

    void openGoogleMapAtStartSpot() {
        if (listSpot.size() > 0) {
            float startSpotLatitude = Float.parseFloat(listSpot.get(0).getLatitude());
            float startSpotLongtitude = Float.parseFloat(listSpot.get(0).getLongitude());

            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)&dirflg=d", startSpotLatitude, startSpotLongtitude, "");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
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


    }
}
