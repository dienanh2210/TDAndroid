package vn.javis.tourde.fragment;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.DistanceLocation;
import vn.javis.tourde.utils.ProcessDialog;

public class CourseDriveFragment extends BaseFragment {
    @BindView(R.id.btn_start)
    ImageView btnStart;
    @BindView(R.id.btn_back_drive)
    ImageButton btnBack;
    @BindView(R.id.btn_back_detail)
    Button getBtnBackDetail;
    @BindView(R.id.btn_show_map)
    Button btnMapWay;
    CourseListActivity mAcitivity;
    @BindView(R.id.title_detail)
    TextView title_detail;
    @BindView( R.id.rlt_googlemap )
    RelativeLayout rlt_googlemap;
    @BindView( R.id.rlt_Navitime )
    RelativeLayout rlt_Navitime;
    int courseID;

    double startLongtitude;
    double startLatitude;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAcitivity = (CourseListActivity) getActivity();
        courseID =mAcitivity.getmCourseID();
        getStartPosition();
        GetCourseDataAPI.getCourseData(mAcitivity.getmCourseID(), new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);
                title_detail.setText(mCourseDetail.getmCourseData().getTitle());
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double longtitude = mAcitivity.getLongitude();
                double latitude = mAcitivity.getLatitude();
                double distance = DistanceLocation.getDistance(startLatitude,startLongtitude,latitude,longtitude);
                if(distance<=20){
                    ProcessDialog.showDialogConfirm(getContext(), "", "走行開始しますか？", new ProcessDialog.OnActionDialogClickOk() {
                        @Override
                        public void onOkClick() {
                            mAcitivity.ShowCountDown();
                        }
                    });
                }
                else {
                    ProcessDialog.showDialogOk(mAcitivity,"","スタート地点を確認できませんでした。スタート地点付近に移動してください。");
                  //show for test
                    mAcitivity.ShowCountDown();
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAcitivity.onBackPressed();
            }
        });
        getBtnBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAcitivity.onBackPressed();
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
                String packageName = "com.navitime.local.navitime";
                // String packageName = "NAVITIME: 地図・ルート検索";
                launchNewActivity(getContext(),packageName);
            }
        } );

    }

    private void getStartPosition() {
        GetCourseDataAPI.getCourseData(courseID, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject =(JSONObject)response;
                if(jsonObject.has("error"))
                    return;
               CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);
               String strLat = mCourseDetail.getmCourseData().getStartLatitude();
               String strLong = mCourseDetail.getmCourseData().getStartLongitude();
               if((strLat != null && strLat !="") &&(strLong !=null && strLong !="")){
                   startLatitude = Double.parseDouble(strLat);
                   startLongtitude = Double.parseDouble(strLong);
               }

            }

            @Override
            public void onError(VolleyError error) {

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
        return inflater.inflate(R.layout.fragment_course_drive, container, false);
    }

}
