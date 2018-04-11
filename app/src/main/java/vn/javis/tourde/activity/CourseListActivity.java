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
    public void showCourseListPage(){

        fragmentTransaction =fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container_fragment);
        fragmentTransaction.remove(currentFragment);
        fragmentTransaction.add(R.id.container_fragment,new CourseListFragment());
        fragmentTransaction.commit();
    }
    public void ShowCourseDetail(){
        fragmentTransaction =fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container_fragment);
        fragmentTransaction.remove(currentFragment);
        fragmentTransaction.add(R.id.container_fragment,new CourseDetailFragment());
        fragmentTransaction.commit();
    }

}
