package vn.javis.tourde.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import vn.javis.tourde.fragment.CourseDetailFragment;
import vn.javis.tourde.fragment.CourseListFragment;
import vn.javis.tourde.R;

/**
 * Created by admin on 3/23/2018.
 */

public class CourseListActivity extends AppCompatActivity {

    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        setHearder();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        CourseListFragment courseListFragment = new CourseListFragment();
        fragmentTransaction.replace(R.id.container_fragment, courseListFragment);
        fragmentTransaction.commit();
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
    public void showCourseListPage(){

        openPage(new CourseListFragment());
    }
    public void ShowCourseDetail(){
      openPage(new CourseDetailFragment());
    }
     void openPage(android.support.v4.app.Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
