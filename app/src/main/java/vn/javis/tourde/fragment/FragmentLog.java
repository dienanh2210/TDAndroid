package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListSpotCheckinAdapter;


public class FragmentLog extends BaseFragment{
    View mView;
    CourseListActivity mActivity;
    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.log_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }
}
