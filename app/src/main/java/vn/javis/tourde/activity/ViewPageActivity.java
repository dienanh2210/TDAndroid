package vn.javis.tourde.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.ApplicationVersionAPI;
import vn.javis.tourde.apiservice.MaintenanceAPI;
import vn.javis.tourde.fragment.tutorial.FireFragment;
import vn.javis.tourde.fragment.tutorial.FourFragment;
import vn.javis.tourde.fragment.tutorial.SixFragment;
import vn.javis.tourde.fragment.tutorial.TwoFragment;
import vn.javis.tourde.fragment.tutorial.OneFragment;
import vn.javis.tourde.fragment.tutorial.ThreeFragment;
import vn.javis.tourde.model.CheckAppVersion;
import vn.javis.tourde.model.MaintenanceStatus;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;

import android.view.Window;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewPageActivity extends AppCompatActivity {

    ViewPager viewPager;
    private int dotscount;
    private ImageView[] dots;
    LinearLayout sliderDotspanel;
    TextView btnSkip;
    MainActivity.ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_page);
        //Button skip
        btnSkip = findViewById(R.id.skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (viewPager.getCurrentItem() != 5) {
//                    viewPager.setCurrentItem(5, true);
//                } else {
//                    Intent intent = new Intent(ViewPageActivity.this, MenuEntryActivity.class);
//                    SharedPreferencesUtils.getInstance(getApplicationContext()).setStringValue("Tutorial", "finish");
//                    startActivity(intent);
//                }
                Intent intent = new Intent(ViewPageActivity.this, MenuPageActivity.class);
                SharedPreferencesUtils.getInstance(getApplicationContext()).setStringValue("Tutorial", "finish");
                startActivity(intent);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];
        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), vn.javis.tourde.R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(15, 0, 15, 0);
            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), vn.javis.tourde.R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), vn.javis.tourde.R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
//    @Override
//    public void onBackPressed() {
//        // Do Here what ever you want do on back press;
//    }
    private void setupViewPager(ViewPager viewPager) {
        adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "One");
        adapter.addFragment(new TwoFragment(), "Two");
        adapter.addFragment(new ThreeFragment(), "Three");
        adapter.addFragment(new FourFragment(), "four");
        adapter.addFragment(new FireFragment(), "fire");
        adapter.addFragment(new SixFragment(), "six");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(6);
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
