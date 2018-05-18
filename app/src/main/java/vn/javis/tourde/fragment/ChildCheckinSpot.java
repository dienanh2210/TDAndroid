package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.view.CircleTransform;

public class ChildCheckinSpot extends BaseFragment{
    @BindView(R.id.image_checkin_spot)
    ImageView imageCheckinSport;
    @BindView(R.id.tv_spotNumber)
    ImageView tv_spotNumber;
    @BindView(R.id.tv_spotName)
    ImageView tv_sportName;
    CourseListActivity mActivity;
    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View mView = inflater.inflate(R.layout.child_checkin_spot,container, false);
        return mView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Picasso.with(mActivity).load("").transform(new CircleTransform()).into(imageCheckinSport);

    }
}
