package vn.javis.tourde.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.ApplicationVersionAPI;
import vn.javis.tourde.apiservice.MaintenanceAPI;
import vn.javis.tourde.model.CheckAppVersion;
import vn.javis.tourde.model.MaintenanceStatus;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.ProcessDialog;

public class SrcollViewImageActivity extends BaseActivity {
Button bt_rule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srcoll_view_image);
        bt_rule = findViewById(R.id.bt_rule);
        bt_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rule();
            }
        });
    }

    void Rule() {
        Intent intent = new Intent(this, ViewPageActivity.class);
        //  Intent intent = new Intent(this, UsePageActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
    @Override
    protected void onResume() {
        super.onResume();
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
