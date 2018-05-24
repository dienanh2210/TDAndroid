package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.apiservice.LogoutAccount;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class MenuPageActivity extends BaseActivity {

    TextView tv_close, tv_basic, tv_userregistration;
    RelativeLayout tv_login,tv_tutorial,logout, rlt_register, basic_Info;
    @BindView( R.id.rlt_newuserregister )
    View rlt_newuserregisterl;
    @BindView( R.id.ll_logout )
    View ll_logout;
    @BindView(R.id.line_register)
    ImageView lineRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_page);
        super.onCreate(savedInstanceState);
        tv_login = findViewById(R.id.login);
        tv_login.setOnClickListener(onClickLogin);

        rlt_register=findViewById(R.id.rlt_register);
        rlt_register.setOnClickListener(onClickNewUser);
        tv_close = findViewById(R.id.tv_close);
        tv_close.setOnClickListener(onClick);

        tv_tutorial = findViewById(R.id.rlt_tutorial);
        tv_tutorial.setOnClickListener(onClickTutorial);

        tv_basic = findViewById(R.id.tv_basic);
        tv_basic.setOnClickListener(onClickBasicInfo);

        tv_userregistration = findViewById(R.id.tv_userregistration);
        tv_userregistration.setOnClickListener(onClickNewUser);

        ll_logout.setOnClickListener( onClickLogout );
        basic_Info=findViewById(R.id.basic_Info);
        basic_Info.setVisibility(View.GONE);
        String token = LoginFragment.getmUserToken();
        if(token != "" ){
            ll_logout.setVisibility( View.VISIBLE );
            tv_login.setVisibility(View.GONE);
            rlt_register.setVisibility(View.GONE);
            lineRegister.setVisibility(View.GONE);
            basic_Info.setVisibility(View.VISIBLE);
        }

    }

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
            ProcessDialog.showDialogConfirm(MenuPageActivity.this, "", "ログアウトしますか？", new ProcessDialog.OnActionDialogClickOk() {
                @Override
                public void onOkClick() {
                Intent intent = new Intent(MenuPageActivity.this,CourseListActivity.class);
                startActivity(intent);
                    final String token = LoginFragment.getmUserToken();
                    LogoutAccount.logOut( token, new ServiceCallback() {
                        @Override
                        public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                            JSONObject jsonObject = (JSONObject)response;
                            // Log.d("logout", jsonObject.toString());
                            Log.d("logout", token+jsonObject);
                            if (jsonObject.has("success")) {
                                rlt_register.setVisibility( View.VISIBLE );
                                lineRegister.setVisibility(View.VISIBLE);
                                tv_login.setVisibility(View.VISIBLE);
                                ll_logout.setVisibility( View.GONE );
                                basic_Info.setVisibility(View.GONE);
                                LoginFragment.setmAccount(null);
                                LoginFragment.setmUserToken("");
                                SharedPreferencesUtils.getInstance(getApplicationContext()).setStringValue("Email", "");
                                SharedPreferencesUtils.getInstance(getApplicationContext()).setStringValue("Pass", "");
                                SharedPreferencesUtils.getInstance(getApplicationContext()).setStringValue("Username", "");

                            } else {

                            }
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    } );
                }
            });
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
            if(resultCode == RESULT_OK) {
                boolean str = data.getBooleanExtra( Constant.KEY_LOGIN_SUCCESS, false);
                rlt_newuserregisterl.setVisibility( View.GONE );
                ll_logout.setVisibility( View.VISIBLE );
                String token = LoginFragment.getmUserToken();
                if(token != ""){
                    ll_logout.setVisibility( View.VISIBLE );
                    tv_login.setVisibility(View.GONE);
                }
                Log.i("onActivityResult",""+ str);
            }
        }
    }
//    private Response.Listener<JSONObject> successListener() {
//        return new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("logout", response.toString());
//                if (response.has("success")) {
//
//
//                } else {
//
//                }
//            }
//        };
//
//    }
//    private Response.ErrorListener errorListener() {
//        return new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("logout error", error.getMessage());
//            }
//        };
//    }
}





