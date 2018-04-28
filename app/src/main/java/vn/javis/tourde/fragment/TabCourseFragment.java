package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    ListSpotsDetailAdapter listSpotAdapter;
    CourseListActivity mActivity;
    List<Spot> listSpot = new ArrayList<>();

    public static TabCourseFragment instance(List<Spot> lstSpot){
        TabCourseFragment fragment = new TabCourseFragment();
        fragment.listSpot = lstSpot;
        return  fragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity =(CourseListActivity)getActivity();
        if(listSpot.size()>0) {
            Log.i("listSpot: ",""+listSpot.size());
            listSpotAdapter = new ListSpotsDetailAdapter(mActivity, R.layout.list_spots_detail, listSpot);
            lvSpot.setAdapter(listSpotAdapter);
        }
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.tab_course, container, false);
    }
    public void setListSpotAdapter(){

    }

}
