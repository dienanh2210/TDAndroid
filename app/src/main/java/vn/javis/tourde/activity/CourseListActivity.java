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

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private int mCoursePosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        setHearder();
        showCourseListPage();

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
        mCoursePosition =position;
        openPage(new CourseDetailFragment(), true);
    }

    public int getmCoursePosition() {
        return mCoursePosition;
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
}
