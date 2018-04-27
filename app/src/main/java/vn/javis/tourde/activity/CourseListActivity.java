package vn.javis.tourde.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.fragment.CourseDetailFragment;
import vn.javis.tourde.fragment.CourseListFragment;
import vn.javis.tourde.R;
import vn.javis.tourde.fragment.FragmentTabLayoutMyCourse;
import vn.javis.tourde.fragment.PostCommentFragment;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
/**
 * Created by admin on 3/23/2018.
 */

public class CourseListActivity extends AppCompatActivity implements ServiceCallback {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public int getmCourseID() {
        return mCourseID;
    }

    private int mCourseID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        setHearder();
        Intent intent =getIntent();
        boolean searching = intent.getStringExtra("searching") == "true"?true:false;
        HashMap<String, String> params = (HashMap<String, String>)intent.getSerializableExtra("searchValue");
        if(!searching)
            ListCourseAPI.getJsonValues(this);
        else
            ListCourseAPI.getJsonValueSearch(params,this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setHearder() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void showCourseListPage() {

        openPage(new CourseListFragment(), false);
    }

    public void ShowCourseDetail(int position) {
        mCourseID = ListCourseAPI.getInstance().getCourseIdByPosition(position);
        openPage(new CourseDetailFragment(), true);
    }

    public void ShowMyCourse() {
        openPage(new FragmentTabLayoutMyCourse(), true);
    }

    public void showCommentPost() {
        openPage(new PostCommentFragment(), true);

    }



    public void openPage(android.support.v4.app.Fragment fragment, boolean isBackStack) {
        android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isBackStack)
            tx.addToBackStack(null);
        tx.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
        ListCourseAPI.setAllCourses((JSONObject)response);
        showCourseListPage();
    }

    @Override
    public void onError(VolleyError error) {

    }
}
