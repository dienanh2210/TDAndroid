package vn.javis.tourde.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import vn.javis.tourde.adapter.ListCourseAdapter;
import vn.javis.tourde.database.ListCourseAPI;
import vn.javis.tourde.fragment.CourseListFragment;
import vn.javis.tourde.fragment.CourseSearchFragment;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.R;

/**
 * Created by admin on 3/23/2018.
 */

public class CourseListActivity extends AppCompatActivity {

    private static CourseListActivity instance;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        setHearder();
        instance =this;
        fragmentManager = getFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new CourseListFragment());
        fragmentTransaction.commit();
    }
    @Override
    protected void onDestroy() {
        instance=null;
        super.onDestroy();
    }
    public static CourseListActivity getInstance(){
        return instance;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setHearder() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
    public void showSearchPage(){
        fragmentTransaction =fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container_fragment);
        fragmentTransaction.remove(currentFragment);
        fragmentTransaction.add(R.id.container_fragment,new CourseSearchFragment());
        fragmentTransaction.commit();
    }
    public void showCourseListPage(){
        fragmentTransaction =fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container_fragment);
        fragmentTransaction.remove(currentFragment);
        fragmentTransaction.add(R.id.container_fragment,new CourseListFragment());
        fragmentTransaction.commit();
    }

}
