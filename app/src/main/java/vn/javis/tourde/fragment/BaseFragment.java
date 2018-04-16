package vn.javis.tourde.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.javis.tourde.activity.MainActivity;

public abstract class BaseFragment extends Fragment {
    private View mView;
    private Unbinder mUnbind;
    private Activity mActivity;
    protected MainActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = getView(inflater,container);
        mUnbind = ButterKnife.bind(this,mView);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        mUnbind.unbind();
    }

    public abstract View getView(LayoutInflater inflater, @Nullable ViewGroup container);

}
