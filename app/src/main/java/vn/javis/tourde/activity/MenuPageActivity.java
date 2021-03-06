package vn.javis.tourde.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.LogoutAccount;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.fragment.RegisterFragment;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;

import static vn.javis.tourde.fragment.FragmentTabLayoutRunning.KEY_SHARED_BASETIME;

public class MenuPageActivity extends BaseActivity {

    TextView tv_close, tv_basic, tv_userregistration;
    RelativeLayout tv_login, tv_tutorial, logout, rlt_register, basic_Info;
    @BindView(R.id.rlt_newuserregister)
    View rlt_newuserregisterl;
    @BindView(R.id.rlt_nippon)
    RelativeLayout rlt_pippon;
    @BindView(R.id.ll_logout)
    View ll_logout;
    @BindView(R.id.line_register)
    ImageView lineRegister;
    @BindView(R.id.privacy)
    RelativeLayout privacy;
    @BindView(R.id.inquiry)
    RelativeLayout inquiry;
    @BindView( R.id.rateInApp )
    RelativeLayout rateInApp;
    MenuPageActivity activity;
    @BindView(R.id.tv_version)
    TextView tv_version;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_page);
        super.onCreate(savedInstanceState);
        tv_login = findViewById(R.id.login);
        tv_login.setOnClickListener(onClickLogin);

        rlt_register = findViewById(R.id.rlt_register);
        rlt_register.setOnClickListener(onClickNewUser);
        tv_close = findViewById(R.id.tv_close);
        tv_close.setOnClickListener(onClick);

        tv_tutorial = findViewById(R.id.rlt_tutorial);
        tv_tutorial.setOnClickListener(onClickTutorial);

        tv_basic = findViewById(R.id.tv_basic);
        tv_basic.setOnClickListener(onClickBasicInfo);

        tv_userregistration = findViewById(R.id.tv_userregistration);
        tv_userregistration.setOnClickListener(onClickNewUser);

        ll_logout.setOnClickListener(onClickLogout);
        basic_Info = findViewById(R.id.basic_Info);
        basic_Info.setVisibility(View.GONE);
        //String token = LoginFragment.getmUserToken();
        //get token from device to check login or not
        token = SharedPreferencesUtils.getInstance(getApplicationContext()).getStringValue(LoginUtils.TOKEN);
        if (!TextUtils.isEmpty(token)) {
            ll_logout.setVisibility(View.VISIBLE);
            tv_login.setVisibility(View.GONE);
            rlt_register.setVisibility(View.GONE);
            lineRegister.setVisibility(View.GONE);
            //if (!SharedPreferencesUtils.getInstance(getApplicationContext()).getBooleanValue(LoginUtils.TOKEN_SNS))
            basic_Info.setVisibility(View.VISIBLE);

        }
        rlt_pippon.setOnClickListener(onClicknippon);
        rateInApp.setOnClickListener(onClickrate);

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPageActivity.this, PrivacypolicyActivity.class);
                //Intent intent = new Intent(MenuPageActivity.this, UsePageActivity.class);
                startActivity(intent);
            }
        });
        inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPageActivity.this, InquiryActivity.class);
                startActivityForResult(intent, 1);
            }
        });
//show version app
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            tv_version.setText(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
        }
    }
    View.OnClickListener onClicknippon = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("http://www.tour-de-nippon.jp/series/");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    };
    View.OnClickListener onClickrate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=tour.de.nippon.app&hl=ja");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    };
    View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuPageActivity.this, LoginSNSActivity.class);
            //basic_Info.setVisibility(View.VISIBLE);
            startActivityForResult(intent, 1);
        }
    };
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuPageActivity.this, CourseListActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onClickTutorial = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuPageActivity.this, ViewPageActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onClickBasicInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuPageActivity.this, BasicInfoActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onClickNewUser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuPageActivity.this, MenuEntryActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onClickLogout = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(SharedPreferencesUtils.getInstance(MenuPageActivity.this).getLongValue(KEY_SHARED_BASETIME) != 0)
            {
                ProcessDialog.showDialogOk(MenuPageActivity.this, "", "走行中はログアウトできません。\n 走行を終了してください。");
            }
            else {
                ProcessDialog.showDialogConfirm(MenuPageActivity.this, "", "ログアウトしますか？", new ProcessDialog.OnActionDialogClickOk() {
                    @Override
                    public void onOkClick() {
                        LogoutAccount.logOut(token, new ServiceCallback() {
                            @Override
                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                JSONObject jsonObject = (JSONObject) response;
                                // Log.d("logout", jsonObject.toString());
                                Log.d("logout", token + jsonObject);
                                if (jsonObject.has("success")) {
                                    rlt_register.setVisibility(View.VISIBLE);
                                    lineRegister.setVisibility(View.VISIBLE);
                                    tv_login.setVisibility(View.VISIBLE);
                                    ll_logout.setVisibility(View.GONE);
                                    basic_Info.setVisibility(View.GONE);
                                    LoginFragment.setmAccount(null);
                                    //Delete token from device when sign out
                                    SharedPreferencesUtils.getInstance(getApplicationContext()).delete();
                                    SharedPreferencesUtils.getInstance(MenuPageActivity.this).setStringValue("Tutorial","finish");
                                    SharedPreferencesUtils.getInstance(MenuPageActivity.this).setStringValue("rule","finish");
                                    Intent intent = new Intent(MenuPageActivity.this, CourseListActivity.class);
                                    intent.putExtra(Constant.KEY_LOGOUT_SUCCESS, 1);
                                    startActivity(intent);
                                    RegisterFragment.bitmapIcon = null;
                                } else {

                                }
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Log.e("", "onError: ", error);
                            }
                        });
                    }
                });
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("onBackPressed", "true");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                boolean str = data.getBooleanExtra(Constant.KEY_LOGIN_SUCCESS, false);
                rlt_newuserregisterl.setVisibility(View.GONE);
                ll_logout.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(token)) {
                    ll_logout.setVisibility(View.VISIBLE);
                    tv_login.setVisibility(View.GONE);
                }
                Log.i("onActivityResult", "" + str);
            }
        }
    }
}





