package vn.javis.tourde.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.javis.tourde.activity.MainActivity;
import vn.javis.tourde.utils.ProcessDialog;

public abstract class BaseSaveStateFragment extends Fragment {
    protected View mView;
    private Unbinder mUnbind;
    protected MainActivity activity;
    protected ProcessDialog mProgessDialog;
    protected boolean isInitView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = getView(inflater, container);
            mUnbind = ButterKnife.bind(this, mView);
            ButterKnife.setDebug(true);
            isInitView = true;
        } else {
            isInitView = false;
        }

        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        // tam thoi bo cai nay di, tranh crash (quanpv)
//        if (mUnbind != null) {
//            mUnbind.unbind();
//            mUnbind = null;
//        }
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
