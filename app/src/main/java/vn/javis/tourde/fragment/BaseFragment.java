package vn.javis.tourde.fragment;


import android.content.Context;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.Tracker;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.javis.tourde.activity.MainActivity;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.utils.ProcessDialog;

public abstract class BaseFragment extends Fragment {
    private View mView;
    private Unbinder mUnbind;
    protected MainActivity activity;
    protected ProcessDialog mProgessDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = getView(inflater, container);
        mUnbind = ButterKnife.bind(this, mView);
        ButterKnife.setDebug(true);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mUnbind != null) {
            mUnbind.unbind();
            mUnbind = null;
        }
        super.onDestroyView();

    }

    // modified by quanpv
    public void showProgressDialog() {
        showProgressDialog("ローディング中...", false);
    }

    public void showProgressDialog(String message, boolean cancelable) {
        if (mProgessDialog == null) {
            mProgessDialog = new ProcessDialog(getContext());
        }
        try {
            mProgessDialog.showDialog(message, cancelable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        if (mProgessDialog != null) {
            try {
                mProgessDialog.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public abstract View getView(LayoutInflater inflater, @Nullable ViewGroup container);

}
