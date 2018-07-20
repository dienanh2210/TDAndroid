package vn.javis.tourde.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import vn.javis.tourde.activity.BasicInfoActivity;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.CropperImageActivity;
import vn.javis.tourde.activity.LoginSNSActivity;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.adapter.ListRegisterAdapter;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.model.Account;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ListArea;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.view.CircleTransform;


public class RegisterFragment extends BaseFragment implements View.OnClickListener, ServiceCallback,
        PrefectureFragment.OnFragmentInteractionListener,
        AgeFragment.OnFragmentInteractionListener {

    private static final int GET_FROM_GALLERY = 1;
    private static final int CROPPER_IMAGE = 500;
    private static boolean isChangAccount;
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
    @BindView(R.id.appCompatButtonLogin)
    Button appCompatButtonLogin;
    @BindView(R.id.changeInfo)
    Button changeInfo;


    int prefecture = 1;
    int age = 10;
    int sex = 1;
    String txtAge = "10代";
    String txtArea = "北海道";

    TextView tv_prefecture;
    TextView tv_age;
    public static Bitmap bitmapIcon;
    int changeImage = 0;
    private RegisterActivity activity;
    public static final long FILE_SIZE_8MB = 8 * 1024 * 1024;
    String token;
    JSONObject data;

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
        token = SharedPreferencesUtils.getInstance(getContext()).getStringValue(LoginUtils.TOKEN);

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
        Button changeInfo = view.findViewById(R.id.changeInfo);
        View tv_back_resgister = view.findViewById(R.id.tv_back_resgister);

        tv_prefecture = view.findViewById(R.id.tv_prefecture);

        tv_age = view.findViewById(R.id.tv_age);
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
        changeInfo.setOnClickListener(this);
        tv_back_resgister.setOnClickListener(this);
        select_userIcon.setOnClickListener(this);
        select_userIcon.setImageBitmap(bitmapIcon);
        tv_prefecture.setText(txtArea);
        tv_age.setText(txtAge);
        if (!TextUtils.isEmpty(token)) {
            setInfo();
            register_title.setVisibility(View.GONE);
            title_changeInfo.setVisibility(View.VISIBLE);
            isChangAccount = true;
        } else {
            register_title.setVisibility(View.VISIBLE);
            title_changeInfo.setVisibility(View.GONE);
            appCompatButtonLogin.setVisibility(View.VISIBLE);
            changeInfo.setVisibility(View.GONE);
        }
        if (isChangAccount) {
            appCompatButtonLogin.setVisibility(View.GONE);
            changeInfo.setVisibility(View.VISIBLE);
        }

    }

    void setInfo() {
        showProgressDialog();
        LoginAPI.pushToken(token, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;

                edt_username.setText(jsonObject.getString("nickname"));
                edt_email.setText(jsonObject.getString("email"));
                if (jsonObject.getInt("sex") == 1) {
                    imv_mark_man.setVisibility(View.VISIBLE);
                    imv_mark_woman.setVisibility(View.GONE);
                } else {
                    imv_mark_woman.setVisibility(View.VISIBLE);
                    imv_mark_man.setVisibility(View.GONE);
                }

                age = jsonObject.getInt("age");
                tv_age.setText(age + "代");
                prefecture = jsonObject.getInt("area");

                if(prefecture>0)tv_prefecture.setText(ListArea.getAreaName(prefecture - 1));
                if (!TextUtils.isEmpty(jsonObject.getString("image")))
                    PicassoUtil.getSharedInstance(getContext()).load(jsonObject.getString("image")).resize(0, 200).onlyScaleDown().transform(new CircleTransform()).into(select_userIcon);
                hideProgressDialog();
            }


            @Override
            public void onError(VolleyError error) {
                hideProgressDialog();
            }

        });


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

        switch (v.getId()) {
            case R.id.rlt_prefecture:
                activity.openPage(PrefectureFragment.newInstance(this, prefecture, tv_prefecture.getText().toString()), true, true);
                break;
            case R.id.rlt_age:
                activity.openPage(AgeFragment.newInstance(this, age), true, true);
                break;
            case R.id.rlt_man:
                chooseGender(true);
                sex = 1;
                break;
            case R.id.rlt_woman:
                sex = 2;
                chooseGender(false);
                break;
            case R.id.appCompatButtonLogin:
                //   LoginAPI.register(edt_email.toString(), edt_password.toString(), gender, 10, "Tokyo", this);
                if (bitmapIcon == null) {
                    showProgressDialog();
                    LoginAPI.registerAccount(edt_email.getText().toString(), edt_password.getText().toString(), edt_username.getText().toString(), bitmapIcon, sex, age, prefecture, successListener(), errorListener());
                } else {
                    LoginAPI.registerAccount(activity, edt_email.getText().toString(), edt_password.getText().toString(), edt_username.getText().toString(), bitmapIcon, sex, age, prefecture, this);
                    showProgressDialog();
                }
                break;
            case R.id.tv_back_resgister:
                isChangAccount = false;
                bitmapIcon = null;
                activity.onBackPressed();
                break;
            case R.id.select_userIcon:
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
            case R.id.changeInfo:
                showProgressDialog();
                LoginAPI.editAccount(activity, token, edt_email.getText().toString(), edt_password.getText().toString(), edt_username.getText().toString(), bitmapIcon, changeImage, sex, age, prefecture, new ServiceCallback() {
                    @Override
                    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                        if (((JSONObject) response).has("success")) {
                            Intent intent = new Intent(getActivity(), BasicInfoActivity.class);
                            startActivity(intent);
                            hideProgressDialog();
                            activity.finish();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });

                break;

        }
    }

    void loginToApp() {
        final boolean gender = false;
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            showProgressDialog();
            LoginAPI.loginEmail(email, password, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) {
                    JSONObject jsonObject = (JSONObject) response;
                    if (jsonObject.has("success")) {
                        if (!isChangAccount) {
                            ProcessDialog.showDialogLogin(getContext(), "", "新規登録に成功しました", new ProcessDialog.OnActionDialogClickOk() {
                                @Override
                                public void onOkClick() {

                                    Intent intent = new Intent(getActivity(), CourseListActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                    getActivity().setResult(Activity.RESULT_OK, intent);
                                    startActivity(intent);
                                    ProcessDialog.showloading(getContext(), true);
                                }
                            });
                        } else {
                            Intent intent = new Intent(getActivity(), CourseListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                            getActivity().setResult(Activity.RESULT_OK, intent);
                            startActivity(intent);
                            ProcessDialog.showloading(getContext(), true);
                        }
                        if (jsonObject.has("token")) {
                            try {
                                SharedPreferencesUtils.getInstance(activity).setStringValue(LoginUtils.TOKEN, jsonObject.getString("token"));
                                getAccount();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Log.d(edt_email.toString(), edt_password.toString() + "error");
                    }
                    hideProgressDialog();
                }

                @Override
                public void onError(VolleyError error) {
                    hideProgressDialog();
                }
            });
        }
    }

    public void getAccount() {

        LoginAPI.pushToken(token, new ServiceCallback() {
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
            //        bitmapIcon = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            File file = new File(getPath(selectedImage));
            Log.i("File size: ", "size" + file.length() + getPath(selectedImage));
            if (file.length() < FILE_SIZE_8MB /*&& bitmapIcon != null*/) {
//                    select_userIcon.setImageBitmap(bitmapIcon);
                Intent intent = new Intent(getActivity(), CropperImageActivity.class);
                intent.putExtra(Constant.KEY_IMAGE_URI, selectedImage);
                getActivity().startActivityForResult(intent, CROPPER_IMAGE);
            } else
                ProcessDialog.showDialogOk(getContext(), "", "容量が大きすぎるため投稿できません。");
        }

        if (requestCode == CROPPER_IMAGE && resultCode == Activity.RESULT_OK) {
            Log.i("onActivityResult", "------------->>>>Fragment");
            Uri uriCropper = data.getParcelableExtra(Constant.KEY_IMAGE_CROPPER);
            try {
                if (uriCropper != null)
                    bitmapIcon = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriCropper);
            } catch (IOException e) {
                e.printStackTrace();
            }
            select_userIcon.setImageBitmap(bitmapIcon);
        }

    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
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
                hideProgressDialog();
            }

        };

    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("register account error", error.getMessage());
                ProcessDialog.showDialogOk(getContext(), "", " 新規登録に失敗しました。");
                hideProgressDialog();
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
        hideProgressDialog();
    }

    @Override
    public void onError(VolleyError error) {
        hideProgressDialog();
    }

    @Override
    public void onFragmentInteraction(int valueArea, String content) {
        prefecture = valueArea;
        txtArea = content;
        tv_prefecture.setText(content);
        Log.i("test area", "Register fragment 430 " + prefecture + "-" + content);
    }

    @Override
    public void onAgeFragmentInteraction(int valueAge, String content) {
        age = valueAge;
        txtAge = content;
        tv_age.setText(content);
    }


}