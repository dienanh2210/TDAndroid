package vn.javis.tourde.activity.UserRegistration.RegisterFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.android.volley.Response;
import com.android.volley.Response.Listener;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;


import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.ApiEndpoint;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.volley.VolleyCustomRequest;

public class RegisterFragment extends Fragment implements View.OnClickListener, ServiceCallback {

    private EditText edt_email;
    private EditText edt_password;
    private RelativeLayout rlt_prefecture;
    private ImageView imv_mark_man;
    private ImageView imv_mark_woman;
    private RelativeLayout rlt_woman;
    private RelativeLayout rlt_man;
    private Button appCompatButtonLogin;
    public int refreshRate;

    private RegisterActivity activity;

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
        activity = (RegisterActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_register_fragment, container, false);
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        rlt_prefecture = view.findViewById(R.id.rlt_prefecture);
        imv_mark_man = view.findViewById(R.id.imv_mark_man);
        imv_mark_woman = view.findViewById(R.id.imv_mark_woman);
        rlt_man = view.findViewById(R.id.rlt_man);
        rlt_woman = view.findViewById(R.id.rlt_woman);
        appCompatButtonLogin = view.findViewById(R.id.appCompatButtonLogin);

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
        appCompatButtonLogin.setOnClickListener(this);
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


    private boolean chooseGender(boolean isMan) {
        if (isMan) {
            imv_mark_man.setVisibility(View.VISIBLE);
            imv_mark_woman.setVisibility(View.GONE);
        } else {
            imv_mark_man.setVisibility(View.GONE);
            imv_mark_woman.setVisibility(View.VISIBLE);
        }
        return isMan;
    }

    @Override
    public void onClick(View v) {
        boolean gender = false;
        switch (v.getId()) {
            case R.id.rlt_prefecture:
                activity.openPage(new PrefectureFragment());
                break;
            case R.id.rlt_man:
                gender = chooseGender(true);
                break;
            case R.id.rlt_woman:
                gender = chooseGender(false);
                break;
            case R.id.appCompatButtonLogin:
             //   LoginAPI.register(edt_email.toString(), edt_password.toString(), gender, 10, "Tokyo", this);
                LoginAPI.registerAccount( edt_email.toString(), edt_password.toString(),successListener(),errorListener());
                break;
        }
    }

    private Response.Listener<JSONObject> successListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("register account", response.toString());
                if(response.has("success"))
                {

                }
                else {

                }

            }
        };

    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("register account error", error.getMessage());
            }
        };
    }

    @Override
    public void onSuccess(ServiceResult resultCode, Object response) {
        Log.i("Register ACCOUNT: ", response.toString());
    }

    @Override
    public void onError(VolleyError error) {

    }
}