package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListSpotsDetailAdapter;
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.utils.ProcessDialog;

public class TabCourseFragment extends BaseFragment {


    @BindView(R.id.lv_list_spots)
    ListView lvSpot;
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

    ListSpotsDetailAdapter listSpotAdapter;
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
            Log.i("listSpot: ", "" + listSpot.size());
            listSpotAdapter = new ListSpotsDetailAdapter(mActivity, R.layout.list_spots_detail, listSpot);
            lvSpot.setAdapter(listSpotAdapter);
            listSpotAdapter.setOnSpotImageClick(new ListSpotsDetailAdapter.OnSpotImageClick() {
                @Override
                public void onItemClick(int spotID) {
                    mActivity.showSpotImages(spotID);
                }
            });
        }
        btnStartPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showCourseDrive();
            }
        });
        txtAvaragePace.setText(avagePace);
        txtFinishTime.setText(finishTIme);
        txtStartAddress.setText(startAddress);
        btnRunningApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = "運転中の画面操作・注視は、道路交通法又は、道路交通規正法に違反する可能性があります。画面の注視/操作を行う場合は安全な場所に停車し、画面の注視や操作を行ってください。 \n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "道路標識などの交通規制情報が実際の道路状況と異なる場合は、すべて現地の通行規制や標識の指示に従って走行してください";

                ProcessDialog.showDialogConfirm(getContext(), "ご利用にあたって", content, new ProcessDialog.OnActionDialogClickOk() {
                    @Override
                    public void onOkClick() {
                        mActivity.ShowCountDown();
                    }
                });
            }
        });
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
