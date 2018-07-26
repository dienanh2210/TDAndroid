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
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.google.maps.android.SphericalUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.services.GoogleService;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.utils.DistanceLocation;
import vn.javis.tourde.utils.ProcessDialog;

public class CourseDriveFragment extends BaseFragment {
    @BindView(R.id.btn_start)
    ImageView btnStart;
    @BindView(R.id.btn_back_drive)
    ImageButton btnBack;
    @BindView(R.id.btn_drive)
    Button btn_drive;
    @BindView(R.id.btn_show_map)
    Button btnMapWay;
    CourseListActivity mAcitivity;
    @BindView(R.id.title_detail)
    TextView title_detail;
    @BindView(R.id.rlt_googlemap)
    RelativeLayout rlt_googlemap;
    @BindView(R.id.rlt_Navitime)
    RelativeLayout rlt_Navitime;
    int mCourseID;
    double startLongtitude;
    double startLatitude;
    public boolean isFromMain = true;
    public static CourseDriveFragment newInstance(boolean isFromMain) {
        CourseDriveFragment fragment = new CourseDriveFragment();
        fragment.isFromMain = isFromMain;
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAcitivity = (CourseListActivity) getActivity();
        mCourseID = mAcitivity.getmCourseID();
        mAcitivity.turnOnGPS();
        showProgressDialog();
        getStartPosition();
        GetCourseDataAPI.getCourseData(mAcitivity.getmCourseID(), new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("error")) {
                    Log.i("Error course drive73", response.toString());
                } else {
                    CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);
                    if (title_detail != null && mCourseDetail.getmCourseData() != null && !mCourseDetail.getmCourseData().getTitle().isEmpty()) {
                        title_detail.setText(mCourseDetail.getmCourseData().getTitle());
                        startLatitude = Double.parseDouble(mCourseDetail.getSpot().get(0).getLatitude());
                        startLongtitude = Double.parseDouble(mCourseDetail.getSpot().get(0).getLongitude());
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onError(VolleyError error) {
                hideProgressDialog();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TourDeApplication.getInstance().trackEvent("tap_start_logging_course_id=" + mCourseID, "tap", "tap_start_logging_course_id=" + mCourseID);
                if (mAcitivity.isBoolean_permission()) {
                    double longtitude = mAcitivity.getLongitude();
                    double latitude = mAcitivity.getLatitude();
                    // double distance = DistanceLocation.getDistance(startLatitude, startLongtitude, latitude, longtitude);
                    double distance = SphericalUtil.computeDistanceBetween(new LatLng(startLatitude, startLongtitude), new LatLng(latitude, longtitude));
                    double distance2 = SphericalUtil.computeDistanceBetween(new LatLng(startLatitude, startLongtitude), new LatLng(mAcitivity.getLatitudeNetWork(), mAcitivity.getLongitudeNetWork()));

                    Log.i("GPSStart", "" + distance + "-"+distance2+"-"+ latitude + "-" + longtitude + "--" + startLatitude + "-" + startLongtitude);
                    if (distance <= GoogleService.DISTANCE_ALLOW || distance2 <= GoogleService.DISTANCE_ALLOW) {
                        ProcessDialog.showDialogConfirm(getContext(), "", "走行開始しますか？", new ProcessDialog.OnActionDialogClickOk() {
                            @Override
                            public void onOkClick() {
                                mAcitivity.ShowCountDown();
                            }
                        });
                    } else {
                        ProcessDialog.showDialogOk(mAcitivity, "", "スタート地点を確認できませんでした。スタート地点付近に移動してください。", new ProcessDialog.OnActionDialogClickOk() {
                            @Override
                            public void onOkClick() {
//                                ProcessDialog.showDialogConfirm(getContext(), "", "走行開始しますか？", new ProcessDialog.OnActionDialogClickOk() {
//                                    @Override
//                                    public void onOkClick() {
//                                        mAcitivity.ShowCountDown();
//                                    }
//                                });
                            }
                        });
                    }
                } else {
                    mAcitivity.fn_permission();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAcitivity.onBackPressed();
            }
        });
        btn_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mAcitivity.onBackPressed();


                    String uri = String.format( Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)&dirflg=d", startLatitude, startLongtitude, "");
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
        });

        rlt_googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr=" + 12f + "," + 2f + " (" + "Where the party is at" + ")";
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
    private void getStartPosition() {
        GetCourseDataAPI.getCourseData(mCourseID, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("error"))
                    return;
                CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);
                String strLat = mCourseDetail.getmCourseData().getStartLatitude();
                String strLong = mCourseDetail.getmCourseData().getStartLongitude();
                if ((strLat != null && strLat != "") && (strLong != null && strLong != "")) {
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
                //intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.navitime.local.navitime&hl=ja" + packageName));
                context.startActivity(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.navitime.local.navitime&hl=ja " + packageName)));

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
