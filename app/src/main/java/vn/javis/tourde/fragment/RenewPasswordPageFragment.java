package vn.javis.tourde.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.LoginSNSActivity;
import vn.javis.tourde.activity.MenuPageActivity;
import vn.javis.tourde.utils.ProcessDialog;

public class RenewPasswordPageFragment extends BaseFragment {

    LoginSNSActivity mAcitivity;
    private OnFragmentInteractionListener listener;
    TextView tv_back_password;


    public static RenewPasswordPageFragment newInstance(View.OnClickListener listener) {
        RenewPasswordPageFragment fragment = new RenewPasswordPageFragment();
        fragment.listener = (RenewPasswordPageFragment.OnFragmentInteractionListener) listener;
        return fragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAcitivity = (LoginSNSActivity) getActivity();
        tv_back_password=view.findViewById( R.id.tv_back_password );
        tv_back_password.setOnClickListener( onClickBack );
    }
    View.OnClickListener onClickBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        mAcitivity.onBackPressed();
        }
    };
    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.renew_password_page_fragment, container, false);

    }


    public interface OnFragmentInteractionListener extends View.OnClickListener {
        @Override
        void onClick(View v);
    }
}
