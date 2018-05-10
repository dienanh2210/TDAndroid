package vn.javis.tourde.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.SearchCourseActivity;

public class TabSpotFacility extends BaseFragment implements View.OnClickListener{
Button bt_spot_page;
CourseListActivity activity;

    //Overriden method onCreateView
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_spot_facility, container, false);

        bt_spot_page=view.findViewById( R.id.bt_spot_page );
        bt_spot_page.setOnClickListener( this );

        return view;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activity = (CourseListActivity) getActivity();
    }
    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_spot_page:
               activity.openPage(SpotFacilitiesFragment.newInstance(this), true);
                break;

        }
    }
}
