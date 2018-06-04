package vn.javis.tourde.fragment;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListCourseAdapter;
import vn.javis.tourde.adapter.ListSpotDetailCircleAdapter;

import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.model.Spot;
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
    @BindView( R.id.rlt_googlemap )
    RelativeLayout rlt_googlemap;
    @BindView( R.id.rlt_Navitime )
    RelativeLayout rlt_Navitime;
    ListSpotDetailCircleAdapter listSpotAdapter;
    CourseListActivity mActivity;
    List<Spot> listSpot = new ArrayList<>();
    String avagePace, finishTIme, startAddress;

    public static TabCourseFragment instance(List<Spot> lstSpot) {
        TabCourseFragment fragment = new TabCourseFragment();
        fragment.listSpot = lstSpot;
        return fragment;
    }

    public static TabCourseFragment instance(String finishTime, String averagePace, String startAddress, List<Spot> lstSpot) {
        TabCourseFragment fragment = new TabCourseFragment();
        fragment.listSpot = lstSpot;
        fragment.finishTIme = finishTime;
        fragment.avagePace = averagePace;
        fragment.startAddress = startAddress;
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        if (listSpot.size() > 0) {

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            rcllistspots.setItemAnimator(new DefaultItemAnimator());
            rcllistspots.setLayoutManager(layoutManager);


            listSpotAdapter = new ListSpotDetailCircleAdapter(listSpot, mActivity);
            rcllistspots.setAdapter(listSpotAdapter);
            listSpotAdapter.setOnItemClickListener(new ListSpotDetailCircleAdapter.OnItemClickedListener() {
                @Override
                public void onItemClick(int spotID) {
                    mActivity.showSpotImages(spotID);
                }
            });
            // rcllistspots.setOn

        }
        btnStartPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showCourseDrive();
            }
        });
        txtAvaragePace.setText(avagePace + "km/h");


        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

        try {
            Date date = dateFormat.parse(finishTIme);
            Calendar timeConvert = Calendar.getInstance();
            timeConvert.setTime(date);
            String out = timeConvert.get(Calendar.HOUR) + "時間" + timeConvert.get(Calendar.MINUTE) + "分";
            Log.e("Time", out);
            txtFinishTime.setText(out);
        } catch (ParseException e) {
        }


        txtStartAddress.setText(startAddress);
        btnRunningApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SharedPreferencesUtils.getInstance(getContext()).getStringValue("Checkbox")=="") {
                    String content = "運転中の画面操作・注視は、道路交通法又は、道路交通規正法に違反する可能性があります。画面の注視/操作を行う場合は安全な場所に停車し、画面の注視や操作を行ってください。 \n" +
                            "\n" +
                            "道路標識などの交通規制情報が実際の道路状況と異なる場合は、すべて現地の通行規制や標識の指示に従って走行してください";

                    ProcessDialog.showDialogcheckbox( getContext(), "ご利用にあたって", content, new ProcessDialog.OnActionDialogClickOk() {
                        @Override
                        public void onOkClick() {
                            mActivity.showCourseDrive();

                        }
                    } );
                }
                else
                {
                    mActivity.showCourseDrive();
                }


            }
        });
        rlt_googlemap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr=" + 12f + "," + 2f + " (" + "Where the party is at" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                try
                {
                    startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        //Toast.makeText(getContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        } );



        rlt_Navitime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.navitime.local.navitime&hl=ja "));
//                startActivity(browserIntent)

               String packageName = "com.navitime.local.navitime";
               // String packageName = "NAVITIME: 地図・ルート検索";
                launchNewActivity(getContext(),packageName);
            }
        } );


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


}
