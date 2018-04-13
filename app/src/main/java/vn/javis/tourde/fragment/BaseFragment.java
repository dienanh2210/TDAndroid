package vn.javis.tourde.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.javis.tourde.activity.MainActivity;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.BasePresenter;
import vn.javis.tourde.activity.NetworkStateManager;
import vn.javis.tourde.activity.ProcessDialog;

public abstract class BaseFragment extends Fragment {

    private View mParent;

//    protected MainActivity activity;

    protected ProcessDialog mProgressDialog;

    private View mView;

    private Unbinder mUnbind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParent = getView(inflater, container);
        if (mParent == null)
            throw new NullPointerException("Chua khoi tao view");

        mUnbind = ButterKnife.bind(this, mParent);

//        activity = (MainActivity) getActivity();

        return mParent;
    }

    public abstract void onInit();

    public abstract View getView(LayoutInflater inflater, @Nullable ViewGroup container);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbind.unbind();
    }

    protected boolean isNetworkAvailable(boolean showToastMessage) {
        boolean isNetworkAvailable = NetworkStateManager.isNetworkAvailable(getContext());
        if (!isNetworkAvailable && showToastMessage) {
            //Toast.makeText(getContext(), R.string.error_not_connected_to_network, Toast.LENGTH_LONG).show();
        }
        return isNetworkAvailable;
    }

    public void showProgress() {
        showProgress("Loading...", false);
    }

    public void showProgress(String message, boolean cancelable) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProcessDialog(getActivity());
        }
        try {
            mProgressDialog.showDialog(message, cancelable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void hideProgress() {
        if (mProgressDialog != null) {
            try {
                mProgressDialog.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}