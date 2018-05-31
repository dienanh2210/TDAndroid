package vn.javis.tourde.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.adapter.ListRegisterAdapter;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.model.Account;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.view.CircleTransform;


public class RegisterFragment extends BaseFragment implements View.OnClickListener, ServiceCallback,
        PrefectureFragment.OnFragmentInteractionListener,
        AgeFragment.OnFragmentInteractionListener {

    private static final int GET_FROM_GALLERY = 1;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.select_userIcon)
    ImageView select_userIcon;
    @BindView(R.id.rlt_age)
    RelativeLayout rlt_age;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.imv_mark_man)
    ImageView imv_mark_man;
    @BindView(R.id.imv_mark_woman)
    ImageView imv_mark_woman;
    @BindView(R.id.register_title)
    TextView register_title;
    @BindView(R.id.title_changeInfo)
    TextView title_changeInfo;
    @BindView(R.id.changeInfo)
    Button changeInfo;
    int prefecture = 1;
    int age = 10;
    String txtAge = "10代";
    String txtArea = "北海道";

    TextView tv_prefecture;
    TextView tv_age;
    Bitmap bitmapIcon;
    private RegisterActivity activity;
    public static final long FILE_SIZE_8MB = 8192;

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
        RelativeLayout rlt_prefecture = view.findViewById(R.id.rlt_prefecture);
        RelativeLayout rlt_man = view.findViewById(R.id.rlt_man);
        RelativeLayout rlt_woman = view.findViewById(R.id.rlt_woman);
        Button appCompatButtonLogin = view.findViewById(R.id.appCompatButtonLogin);
        View tv_back_resgister = view.findViewById(R.id.tv_back_resgister);

        tv_prefecture = view.findViewById(R.id.tv_prefecture);
        tv_prefecture.setText(txtArea);
        tv_age = view.findViewById(R.id.tv_age);
        tv_age.setText(txtAge);
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
        rlt_age.setOnClickListener(this);
        rlt_man.setOnClickListener(this);
        rlt_woman.setOnClickListener(this);
        appCompatButtonLogin.setOnClickListener(this);
        tv_back_resgister.setOnClickListener(this);
        select_userIcon.setOnClickListener(this);
        String change = getArguments().getString(Constant.KEY_CHANGE_INFO);

        if (change != null && change != "") {
            register_title.setVisibility(View.GONE);
            title_changeInfo.setVisibility(View.VISIBLE);
            appCompatButtonLogin.setVisibility(View.GONE);
            changeInfo.setVisibility(View.VISIBLE);
            setInfo();
        } else {
            register_title.setVisibility(View.VISIBLE);
            title_changeInfo.setVisibility(View.GONE);
            appCompatButtonLogin.setVisibility(View.VISIBLE);
            changeInfo.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    void setInfo() {
        Account model = LoginFragment.getmAccount();
        edt_username.setText(model.getNickname());
        edt_email.setText(model.getEmail());
        String url = model.getImage();
        if (url != null && url != ""){
            Picasso.with(getContext()).load(url).transform(new CircleTransform()).into(select_userIcon);
        } else {}
        String sex = model.getSex();
        if(sex=="1")
        {
            imv_mark_man.setVisibility(View.VISIBLE);
            imv_mark_woman.setVisibility(View.GONE);
        }
        else
        {
            imv_mark_woman.setVisibility(View.VISIBLE);
            imv_mark_man.setVisibility(View.GONE);
        }
        tv_age.setText(model.getAge() +"代");
        tv_prefecture.setText(model.getArea());
    }

    private void unResgisterForcus() {
        edt_email.setOnFocusChangeListener(null);
        edt_password.setOnFocusChangeListener(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        unResgisterForcus();
        super.onDestroyView();
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
        int sex = 1;
        switch (v.getId()) {
            case R.id.rlt_prefecture:
                activity.openPage(PrefectureFragment.newInstance(this, prefecture), true, true);
                break;
            case R.id.rlt_age:
                activity.openPage(AgeFragment.newInstance(this, age), true, true);
                break;
            case R.id.rlt_man:
                sex = chooseGender(true) ? 1 : 2;
                break;
            case R.id.rlt_woman:
                sex = chooseGender(false) ? 2 : 1;
                break;
            case R.id.appCompatButtonLogin:
                //   LoginAPI.register(edt_email.toString(), edt_password.toString(), gender, 10, "Tokyo", this);
                if (bitmapIcon == null)
                    LoginAPI.registerAccount(edt_email.getText().toString(), edt_password.getText().toString(), edt_username.getText().toString(), bitmapIcon, sex, age, prefecture, successListener(), errorListener());
                else
                    LoginAPI.registerAccount(activity, edt_email.getText().toString(), edt_password.getText().toString(), edt_username.getText().toString(), bitmapIcon, sex, age, prefecture, this);
                break;
            case R.id.tv_back_resgister:
                activity.onBackPressed();
                break;
            case R.id.select_userIcon:
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
        }
    }

    void loginToApp() {
        final boolean gender = false;
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            LoginAPI.loginEmail(email, password, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) {
                    JSONObject jsonObject = (JSONObject) response;
                    if (jsonObject.has("success")) {
                        ProcessDialog.showDialogLogin(getContext(), "", "新規登録に成功しました", new ProcessDialog.OnActionDialogClickOk() {
                            @Override
                            public void onOkClick() {
                                Intent intent = new Intent(getActivity(), CourseListActivity.class);
                                startActivity(intent);
                                intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                getActivity().setResult(Activity.RESULT_OK, intent);
                            }
                        });
                        //     getActivity().finish();
                        if (jsonObject.has("token")) {
                            try {
                                LoginFragment.setmUserToken(jsonObject.getString("token"));
                                getAccount();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Log.d(edt_email.toString(), edt_password.toString() + "error");
                        Toast.makeText(getContext(), "エラーメッセージ", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
    }

    public void getAccount() {

        LoginAPI.pushToken(LoginFragment.getmUserToken(), new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("account_id")) {
                    LoginFragment.setmAccount(Account.getData(jsonObject.toString()));
                }
            }

            @Override
            public void onError(VolleyError error) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmapIcon = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                boolean isLarge = false;
                File file = new File("android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI");
                Log.i("File size: ", "" + file.length());
                if (file.length() > FILE_SIZE_8MB) {
                    isLarge = true;
                }
                if (!isLarge)
                    select_userIcon.setImageBitmap(bitmapIcon);
                else
                    ProcessDialog.showDialogOk(getContext(), "", "容量が大きすぎるため投稿できません。");
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
                try {
                    Log.d("register account", response.toString());
                    if (response.has("success")) {
                        Log.d(edt_email.getText().toString(), edt_password.getText().toString() + "yes");
                        SharedPreferencesUtils.getInstance(getContext()).setStringValue("Username", edt_username.getText().toString());
                        //SharedPreferencesUtils.getInstance(getContext()).setIntValue("UserIcon", select_userIcon.getDrawable());
                        SharedPreferencesUtils.getInstance(getContext()).setStringValue("Email", edt_email.getText().toString());
                        SharedPreferencesUtils.getInstance(getContext()).setStringValue("Pass", edt_password.getText().toString());
                        loginToApp();
                    } else {
                        Log.d(edt_email.toString(), edt_password.toString() + "error");
                        String error_message = response.getString("error_message");
                        ProcessDialog.showDialogOk(getContext(), "", error_message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("register account error", error.getMessage());
                Toast.makeText(getContext(), "登録に失敗しました", Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    public void onSuccess(ServiceResult resultCode, Object response) {
        Log.i("Register ACCOUNT: ", response.toString());
        JSONObject jsonObject = (JSONObject) response;
        try {
            if (jsonObject.has("success")) {
                Log.d(edt_email.getText().toString(), edt_password.getText().toString() + "yes");
                SharedPreferencesUtils.getInstance(getContext()).setStringValue("Username", edt_username.getText().toString());
                //SharedPreferencesUtils.getInstance(getContext()).setIntValue("UserIcon", select_userIcon.getDrawable());
                SharedPreferencesUtils.getInstance(getContext()).setStringValue("Email", edt_email.getText().toString());
                SharedPreferencesUtils.getInstance(getContext()).setStringValue("Pass", edt_password.getText().toString());
                loginToApp();
            } else {
                Log.d(edt_email.toString(), edt_password.toString() + "error");
                String error_message = ((JSONObject) response).getString("error_message");
                ProcessDialog.showDialogOk(getContext(), "", error_message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onFragmentInteraction(int valueArea, String content) {
        prefecture = valueArea;
        txtArea = content;
        tv_prefecture.setText(content);
        Log.i("test area", "Register fragment 335 " + prefecture + "-" + content);
    }

    @Override
    public void onAgeFragmentInteraction(int valueAge, String content) {
        age = valueAge;
        txtAge = content;
        tv_age.setText(content);
    }
}