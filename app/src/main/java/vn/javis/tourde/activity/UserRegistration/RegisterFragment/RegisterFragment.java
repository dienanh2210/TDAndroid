package vn.javis.tourde.activity.UserRegistration.RegisterFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import vn.javis.tourde.R;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText edt_email;
    private EditText edt_password;
    private RelativeLayout rlt_prefecture;
    private ImageView imv_mark_man;
    private ImageView imv_mark_woman;
    private RelativeLayout rlt_woman;
    private RelativeLayout rlt_man;

    public RegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.activity_register_fragment, container, false);
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        rlt_prefecture = view.findViewById(R.id.rlt_prefecture);
        imv_mark_man = view.findViewById(R.id.imv_mark_man);
        imv_mark_woman = view.findViewById(R.id.imv_mark_woman);
        rlt_man = view.findViewById(R.id.rlt_man);
        rlt_woman = view.findViewById(R.id.rlt_woman);

        edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edt_email.setBackgroundResource(R.drawable.focus_background);
                } else {
                    edt_email.setBackgroundResource(0);
                }
            }
        });

        edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edt_password.setBackgroundResource(R.drawable.focus_background);
                } else {
                    edt_password.setBackgroundResource(0);
                }
            }
        });
        rlt_prefecture.setOnClickListener(this);
        rlt_man.setOnClickListener(this);
        rlt_woman.setOnClickListener(this);

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void chooseGender(boolean isMan) {
        if (isMan) {
            imv_mark_man.setVisibility(View.VISIBLE);
            imv_mark_woman.setVisibility(View.GONE);
        } else {
            imv_mark_man.setVisibility(View.GONE);
            imv_mark_woman.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlt_prefecture:
                ((RegisterActivity) getActivity()).openPage(new PrefectureFragment());
                break;
            case R.id.rlt_man:
                chooseGender(true);
                break;
            case R.id.rlt_woman:
                chooseGender(false);
                break;
        }
    }
}