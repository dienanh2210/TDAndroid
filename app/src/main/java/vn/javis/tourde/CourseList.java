package vn.javis.tourde;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;

import java.util.ArrayList;

import vn.javis.tourde.Adapters.DatabaseAdapter;
import vn.javis.tourde.Adapters.ListCourseViewAdapter;
import vn.javis.tourde.Models.Course;

/**
 * Created by admin on 3/23/2018.
 */

public class CourseList extends AppCompatActivity{
    DatabaseAdapter databaseAdapter;
    ListCourseViewAdapter listCourseViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        setHearder();
        showCourses();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.course_list_menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setHearder(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List Courses");

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }
    private void showCourses(){
        databaseAdapter = new DatabaseAdapter(this);
        ListView list_view = (ListView) findViewById(R.id.lst_courses);
        ArrayList<Course> list_courses = databaseAdapter.getAllCourses();
        listCourseViewAdapter = new ListCourseViewAdapter(this,R.layout.course_view_row,list_courses);
        list_view.setAdapter(listCourseViewAdapter);
    }
}
