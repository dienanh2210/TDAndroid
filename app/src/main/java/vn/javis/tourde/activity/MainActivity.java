package vn.javis.tourde.activity;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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

        if(!isNetworkAvailable())
        {
            ProcessDialog.showDialogOk(MainActivity.this, "", "サーバーに接続できません。");
        }
        else {
            checkMaintenanceAndAppVersion();
        }
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
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService( MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void checkServerDone() {

        Log.i("Tutorial", "-------?" + SharedPreferencesUtils.getInstance(this).getStringValue(LoginUtils.TOKEN));
        if (TextUtils.isEmpty(SharedPreferencesUtils.getInstance(this).getStringValue(LoginUtils.TOKEN))) {
            if (TextUtils.isEmpty(SharedPreferencesUtils.getInstance(this).getStringValue("rule")))
                changeActivity();
            else if (TextUtils.isEmpty(SharedPreferencesUtils.getInstance(this).getStringValue("Tutorial")))
                Rule();
            else
                changeCourseListPage();
        } else
            changeCourseListActivity();
    }

    void Rule() {
        Intent intent = new Intent(this, UsePageActivity.class);
        startActivity(intent);
        finish();
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
        intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
        MainActivity.this.setResult(Activity.RESULT_OK, intent);
        startActivity(intent);
        finish();
    }


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
                        //show title page(wait design)

                    } else {
                        try {
                            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            String version = pInfo.versionName;
                            ApplicationVersionAPI.checkAppVersion(version, "android", new ServiceCallback() {
                                @Override
                                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                    JSONObject jsonObject1 = (JSONObject) response;
                                    if (jsonObject1.has("check")) {
                                        Log.i("version---", jsonObject1.getString("check"));
                                        switch (jsonObject1.getString("check")) {
                                            case "1": {
                                                ProcessDialog.showDialogOk(MainActivity.this, "", "このアプリは最新バージョンにアップデート可能です。", new ProcessDialog.OnActionDialogClickOk() {
                                                    @Override
                                                    public void onOkClick() {
                                                        checkServerDone();
                                                    }
                                                });
                                                break;
                                            }
                                            case "2": {
                                                final String packageName = "tour.de.nippon.app";
                                                ProcessDialog.showDialogOk(MainActivity.this, "", "このアプリは最新バージョンにアップデート可能です。", new ProcessDialog.OnActionDialogClickOk() {
                                                    @Override
                                                    public void onOkClick() {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=tour.de.nippon.app&hl=ja" + packageName)));
                                                    }
                                                });
                                                break;
                                            }
                                            case "0":
                                                checkServerDone();
                                                break;
                                            default:
                                                checkServerDone();
                                                break;
                                        }
                                    }
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    Log.e("", "onError: ", error);
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





