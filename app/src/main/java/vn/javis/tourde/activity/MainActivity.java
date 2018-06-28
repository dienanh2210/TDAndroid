package vn.javis.tourde.activity;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;

import com.android.volley.VolleyError;
import com.growthbeat.BuildConfig;
import com.growthbeat.message.handler.ShowMessageHandler;
import com.growthpush.GrowthPush;
import com.growthpush.model.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.ApplicationVersionAPI;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.apiservice.MaintenanceAPI;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.model.Account;
import vn.javis.tourde.model.CheckAppVersion;
import vn.javis.tourde.model.MaintenanceStatus;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayShowTitleEnabled(false);

        checkMaintenanceAndAppVersion();
        GrowthPush.getInstance().initialize(this, "Qv0VSxaiIeXNuJpg", "Ap0swMx7kBjvztfAp0pzQyWhwB3VIpYZ",
                BuildConfig.DEBUG ? Environment.development : Environment.production);
        GrowthPush.getInstance().requestRegistrationId("1079044899926");
        GrowthPush.getInstance().trackEvent("Launch");
        GrowthPush.getInstance().trackEvent("AllowPushPermission", null, new ShowMessageHandler() {
            @Override
            public void complete(MessageRenderHandler renderHandler) {
                Log.i("GrowthMessage", "run renderHandler, show message.");
                renderHandler.render();
            }

            @Override
            public void error(String error) {
                Log.d("GrowthMessage", error);
            }
        });


    }

    void checkServerDone() {
        Log.i("Tutorial", "-------?"+SharedPreferencesUtils.getInstance(this).getStringValue("Tutorial"));
        if (TextUtils.isEmpty(SharedPreferencesUtils.getInstance(this).getStringValue(LoginUtils.TOKEN))) {
            if (SharedPreferencesUtils.getInstance(this).getStringValue("Tutorial").equals(""))
                changeActivity();
            else
                changeCourseListPage();
        } else
            changeCourseListActivity();
    }

    void changeActivity() {
     //   Intent intent = new Intent(this, ViewPageActivity.class);
        Intent intent = new Intent(this, SrcollViewImageActivity.class);
        startActivity(intent);
        finish();
    }


    void changeCourseListPage() {
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
        finish();
    }

    void changeCourseListActivity() {
            Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
            startActivity(intent);
            intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
            MainActivity.this.setResult(Activity.RESULT_OK, intent);
       //loginToApp();
        //openPage(new LoginFragment());
    }

   /* public void loginToApp() {
        final boolean gender = false;
        String email = SharedPreferencesUtils.getInstance(getApplicationContext()).getStringValue("Email");
        String password = SharedPreferencesUtils.getInstance(getApplicationContext()).getStringValue("Pass");
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            LoginAPI.loginEmail(email, password, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) {
                    JSONObject jsonObject = (JSONObject) response;
                    if (jsonObject.has("success")) {
//                        Intent intent = new Intent( getActivity(), MenuPageLoginActivity.class );
//                        startActivity( intent );
                        Intent intent = new Intent(MainActivity.this,CourseListActivity.class);
                        startActivity(intent);
                        intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                        MainActivity.this.setResult(Activity.RESULT_OK, intent);
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
                        //Log.d(edt_emaillogin.toString(), edt_passwordlogin.toString() + "error");
                    }

                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
    }*/

    public static void getAccount(String token) {

        LoginAPI.pushToken(token, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("account_id")) {
                    setmAccount(Account.getData(jsonObject.toString()));
                }
            }

            @Override
            public void onError(VolleyError error) {
            }
        });
    }

    public static Account getmAccount() {
        return getmAccount();
    }

    public static void setmAccount(Account mAccount) {
        LoginFragment.setmAccount(mAccount);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    public void openPage(Fragment fragment) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tx.addToBackStack(null);
        tx.commit();

    }

    void checkMaintenanceAndAppVersion() {
        MaintenanceAPI.getMaintenanceData(new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (!jsonObject.has("error")) {
                    MaintenanceStatus maintenanceStatus = MaintenanceStatus.getData(response.toString());
                    if (maintenanceStatus.getStatus().equals("1")) {
                        Log.i("maintenance", "111111");
                        //show title page(wait design)

                    } else {
                        Log.i("maintenance", "222222");
                        try {
                            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            String version = pInfo.versionName;
                            ApplicationVersionAPI.checkAppVersion(version, "android", new ServiceCallback() {
                                @Override
                                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                    JSONObject jsonObject1 = (JSONObject) response;
                                    if (jsonObject1.has("check")) {
                                        switch (jsonObject1.getString("check")) {
                                            case "1":
                                            {
                                                ProcessDialog.showDialogOk(getApplicationContext(),"","このアプリは最新バージョンにアップデート可能です。");
                                                break;
                                            }
                                            case "2":
                                            {
                                                final String packageName = "com.navitime.local.navitime";
                                                ProcessDialog.showDialogOk(getApplicationContext(), "", "このアプリは最新バージョンにアップデート可能です。", new ProcessDialog.OnActionDialogClickOk() {
                                                    @Override
                                                    public void onOkClick() {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.navitime.local.navitime&hl=ja " + packageName)));
                                                    }
                                                });
                                                break;
                                            }
                                            case "0":
                                            default:


                                        }
                                    }
                                    checkServerDone();
                                }

                                @Override
                                public void onError(VolleyError error) {

                                }
                            });
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

}





