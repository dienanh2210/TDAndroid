package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.javis.tourde.R;

public class TabSpotFacility extends BaseFragment{

    //Overriden method onCreateView
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.tab_spot_facility, container, false);
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return null;
    }

}
