package vn.javis.tourde.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.view.Window;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.volley.TourDeService;
import vn.javis.tourde.volley.callback.ServiceCallback;
import vn.javis.tourde.volley.common.ServiceResult;
import vn.javis.tourde.volley.endpoint.ApiEndpoint;

public class MainActivity extends Activity {

    ImageButton btn_Next;

    public static MainActivity getInstance() {

        return instance;
    }

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        instance = this;
        ListCourseAPI api = new ListCourseAPI(this);
        Intent intent = new Intent(this, MenuPageActivity.class);
        startActivity(intent);

    }

    public void loadCourseList() {

        Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
        startActivity(intent);
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



}





