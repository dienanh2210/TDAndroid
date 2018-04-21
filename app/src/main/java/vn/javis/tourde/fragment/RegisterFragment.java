package vn.javis.tourde.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;

import com.android.volley.VolleyError;

import org.json.JSONObject;


import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.SharedPreferencesUtils;

import static android.content.Context.MODE_PRIVATE;

public class RegisterFragment extends BaseFragment implements View.OnClickListener, ServiceCallback, PrefectureFragment.OnFragmentInteractionListener {

    private static final int GET_FROM_GALLERY = 1;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.select_userIcon)
    ImageView select_userIcon;
    private EditText edt_email;
    private EditText edt_password;
    private RelativeLayout rlt_prefecture;
    private ImageView imv_mark_man;
    private ImageView imv_mark_woman;
    private RelativeLayout rlt_woman;
    private RelativeLayout rlt_man;
    private Button appCompatButtonLogin;
    public int refreshRate;
    private View tv_back_resgister;
    String prefecture = "北海道";
    TextView tv_prefecture;

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
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.activity_register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_email = view.findViewById(R.id.edt_email);
        edt_password = view.findViewById(R.id.edt_password);
        rlt_prefecture = view.findViewById(R.id.rlt_prefecture);
        imv_mark_man = view.findViewById(R.id.imv_mark_man);
        imv_mark_woman = view.findViewById(R.id.imv_mark_woman);
        rlt_man = view.findViewById(R.id.rlt_man);
        rlt_woman = view.findViewById(R.id.rlt_woman);
        appCompatButtonLogin = view.findViewById(R.id.appCompatButtonLogin);
        tv_back_resgister = view.findViewById(R.id.tv_back_resgister);
        tv_prefecture = view.findViewById(R.id.tv_prefecture);
        tv_prefecture.setText(prefecture);

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
        tv_back_resgister.setOnClickListener(this);
        select_userIcon.setOnClickListener(this);
    }

    /*@Override
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
        tv_back_resgister = view.findViewById(R.id.tv_back_resgister);
        tv_prefecture = view.findViewById(R.id.tv_prefecture);
        tv_prefecture.setText(prefecture);

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
        tv_back_resgister.setOnClickListener(this);
        return view;
    }*/

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
                activity.openPage(PrefectureFragment.newInstance(this), true);
                break;
            case R.id.rlt_man:
                gender = chooseGender(true);
                break;
            case R.id.rlt_woman:
                gender = chooseGender(false);
                break;
            case R.id.appCompatButtonLogin:
                //   LoginAPI.register(edt_email.toString(), edt_password.toString(), gender, 10, "Tokyo", this);
                LoginAPI.registerAccount(edt_email.getText().toString(), edt_password.getText().toString(), successListener(), errorListener());

                break;
            case R.id.tv_back_resgister:
                activity.onBackPressed();
                break;
            case R.id.select_userIcon:
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;

        }
    }

    //    startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                select_userIcon.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private Response.Listener<JSONObject> successListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("register account", response.toString());
                if (response.has("success")) {
                    Log.d(edt_email.getText().toString(), edt_password.getText().toString() + "yes");
                    SharedPreferencesUtils.getInstance(getContext()).setStringValue("Username", edt_username.getText().toString());
                    //SharedPreferencesUtils.getInstance(getContext()).setIntValue("UserIcon", select_userIcon.getDrawable());
                    SharedPreferencesUtils.getInstance(getContext()).setStringValue("Email", edt_email.getText().toString());
                    SharedPreferencesUtils.getInstance(getContext()).setStringValue("Pass", edt_password.getText().toString());
                    Toast.makeText(getContext(), "Register Sucessflly", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(edt_email.toString(), edt_password.toString() + "error");
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

    @Override
    public void onFragmentInteraction(String content) {
        prefecture = content;
        tv_prefecture.setText(content);
    }
}