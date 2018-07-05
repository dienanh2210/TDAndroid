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
import vn.javis.tourde.utils.SharedPreferencesUtils;

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
                SharedPreferencesUtils.getInstance(getApplicationContext()).setStringValue("rule", "finish");
                if (SharedPreferencesUtils.getInstance(getApplicationContext()).getStringValue("Tutorial").equals(""))
                    Rule();
            }
        });
    }

    void Rule() {
        Intent intent = new Intent(this, UsePageActivity.class);
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
    }
}
