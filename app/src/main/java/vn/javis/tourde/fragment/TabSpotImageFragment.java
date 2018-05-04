package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
import vn.javis.tourde.adapter.ListSpotsImageAdapter;
import vn.javis.tourde.model.Spot;

public class TabSpotImageFragment extends BaseFragment {


    @BindView(R.id.lv_list_spots)
    GridView GrvSpotImage;

    ListSpotsImageAdapter listSpotImageAdapter;
    CourseListActivity mActivity;
    List<String> listSpotImg = new ArrayList<>();
    String avagePace,finishTIme,startAddress;
    public static TabSpotImageFragment instance(List<String> lstSpot) {
        TabSpotImageFragment fragment = new TabSpotImageFragment();
        fragment.listSpotImg = lstSpot;
        return fragment;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        if (listSpotImg.size() > 0) {
            Log.i("listSpot: ", "" + listSpotImg.size());
            listSpotImageAdapter = new ListSpotsImageAdapter(mActivity, R.layout.list_spots_detail, listSpotImg);
            GrvSpotImage.setAdapter(listSpotImageAdapter);
        }
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.tab_course, container, false);
    }

}
