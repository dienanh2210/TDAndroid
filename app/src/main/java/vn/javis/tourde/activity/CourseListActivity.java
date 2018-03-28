package vn.javis.tourde.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import vn.javis.tourde.adapter.CourseDatabaseAdapter;
import vn.javis.tourde.adapter.ListCourseViewAdapter;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.R;

/**
 * Created by admin on 3/23/2018.
 */

public class CourseListActivity extends AppCompatActivity {
    CourseDatabaseAdapter databaseAdapter;
    ListCourseViewAdapter listCourseViewAdapter;
    private boolean show_search_part;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        setHearder();
        showCourses();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // LinearLayout menuLayout = (LinearLayout) findViewById(R.id.course_list_actionbar);
        //  getMenuInflater().inflate(R.menu.course_list_menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setHearder() {
        ActionBar actionBar = getSupportActionBar();
        //  actionBar.setTitle("List Courses");

        //   actionBar.setDisplayShowHomeEnabled(true);
        //   actionBar.setLogo(R.mipmap.ic_launcher_round);
        //  getSupportActionBar().setDisplayUseLogoEnabled(true);
        LinearLayout menuLayout = (LinearLayout) findViewById(R.id.course_list_actionbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.course_list_action_bar);

        final ImageButton btn_seach = (ImageButton) findViewById(R.id.ic_right);

        btn_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                show_search_part = !show_search_part;
                if (show_search_part) {
                    btn_seach.setBackgroundResource(R.drawable.up_arrow);
                    //show searching part

                } else {
                    btn_seach.setBackgroundResource(R.drawable.down_arrow);
                    //hide searching part
                }
            }
        });

    }

    private void showCourses() {
        databaseAdapter = new CourseDatabaseAdapter(this);
        ListView list_view = (ListView) findViewById(R.id.lst_courses);
        ArrayList<Course> list_courses = databaseAdapter.getAllCourses();
        listCourseViewAdapter = new ListCourseViewAdapter(this, R.layout.course_view_row, list_courses);
        list_view.setAdapter(listCourseViewAdapter);


    }
}
