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


    ListSpotsDetailAdapter listSpotAdapter;
    CourseListActivity mActivity;
    List<Spot> listSpot = new ArrayList<>();
    String avagePace,finishTIme,startAddress;
    public static TabCourseFragment instance(List<Spot> lstSpot) {
        TabCourseFragment fragment = new TabCourseFragment();
        fragment.listSpot = lstSpot;
        return fragment;
    }

    public static TabCourseFragment instance(String finishTime, String averagePace, String startAddress, List<Spot> lstSpot) {
        TabCourseFragment fragment = new TabCourseFragment();
        fragment.listSpot = lstSpot;
        fragment.finishTIme = finishTime;
        fragment.avagePace =averagePace;
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
