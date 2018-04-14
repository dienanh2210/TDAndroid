package vn.javis.tourde.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.javis.tourde.R;

public class TabVideoFragment extends BaseFragment{

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.tab_video, container, false);
    }

}
