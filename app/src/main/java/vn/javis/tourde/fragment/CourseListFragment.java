package vn.javis.tourde.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListCourseAdapter;
import vn.javis.tourde.database.ListCourseAPI;
import vn.javis.tourde.model.Course;


public class CourseListFragment extends BaseFragment {

    @BindView(R.id.lst_courses_recycleview)
    RecyclerView lstCourseRecycleView;
    @BindView(R.id.course_list_footer)
    LinearLayout layoutFooter;
    ListCourseAdapter listCourseAdapter;
    Activity activity;

    @Override
    public void onStart() {
        super.onStart();

        activity = (CourseListActivity) getActivity();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);

        List<Course> list_courses = ListCourseAPI.getInstance().getAllCourses();
        listCourseAdapter = new ListCourseAdapter(list_courses, activity);
        lstCourseRecycleView.setAdapter(listCourseAdapter);
        lstCourseRecycleView.setItemAnimator(new DefaultItemAnimator());
        lstCourseRecycleView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));
        lstCourseRecycleView.setLayoutManager(layoutManager);
        setFooter();
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_course_list_view, container, false);
    }

    void setFooter() {
        lstCourseRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                  //  layoutFooter.setVisibility(View.VISIBLE);
                } else {
                  //  layoutFooter.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }
}

