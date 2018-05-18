package vn.javis.tourde.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.view.CircleTransform;

public class ChildCheckinSpot extends BaseFragment implements View.OnClickListener {
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
    @OnClick({R.id.ln_select_checkin_spot})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.ln_select_checkin_spot:
               // mActivity.openPage(CheckPointFragment.newInstance(this), true);
                Log.i("asdfadfa","asfaf");
                break;
        }
    }


    @Override
    public void onClick(View v) {

    }
}
