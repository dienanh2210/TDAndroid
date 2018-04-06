package vn.javis.tourde.activity;

import android.app.Activity;
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
import vn.javis.tourde.model.Course;
import vn.javis.tourde.R;

/**
 * Created by admin on 3/23/2018.
 */

public class CourseListActivity extends AppCompatActivity {

    ListCourseAdapter listCourseAdapter;
    private boolean mShowSearchPart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
        setHearder();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    private void setHearder() {
        ActionBar actionBar = getSupportActionBar();
        RelativeLayout menuLayout = (RelativeLayout) findViewById(R.id.course_list_actionbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.hide();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.course_list_action_bar);
        final ImageButton btnSearch = (ImageButton) findViewById(R.id.ic_right);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                mShowSearchPart = !mShowSearchPart;
                if (mShowSearchPart) {
                    //show searching part
                } else {
                    //hide searching part
                }
            }
        });
    }
}
