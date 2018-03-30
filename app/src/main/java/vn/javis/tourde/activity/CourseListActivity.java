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
import vn.javis.tourde.adapter.ListCourseViewAdapter;
import vn.javis.tourde.database.ListCourseAPI;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.R;

/**
 * Created by admin on 3/23/2018.
 */

public class CourseListActivity extends AppCompatActivity {
    ListCourseViewAdapter listCourseViewAdapter;
    private boolean mShowSearchPart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        setHearder();
        showCourses();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    private void setHearder() {
        ActionBar actionBar = getSupportActionBar();
        LinearLayout menuLayout = (LinearLayout) findViewById(R.id.course_list_actionbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.course_list_action_bar);
        final ImageButton btnSearch = (ImageButton) findViewById(R.id.ic_right);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                mShowSearchPart = !mShowSearchPart;
                if (mShowSearchPart) {
                    btnSearch.setBackgroundResource(R.drawable.up_arrow);
                    //show searching part

                } else {
                    btnSearch.setBackgroundResource(R.drawable.down_arrow);
                    //hide searching part
                }
            }
        });
    }
    private void showCourses() {
        ListView list_view = (ListView) findViewById(R.id.lst_courses);
        ArrayList<Course> list_courses = ListCourseAPI.getInstance().getAllCourses();
        listCourseViewAdapter = new ListCourseViewAdapter(this, R.layout.course_view_row, list_courses);
        list_view.setAdapter(listCourseViewAdapter);


    }
}
