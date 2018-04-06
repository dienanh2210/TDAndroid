package vn.javis.tourde.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.login.MenuPage;
import vn.javis.tourde.adapter.ListCourseAdapter;
import vn.javis.tourde.database.ListCourseAPI;
import vn.javis.tourde.model.Course;


public class CourseListFragment extends BaseFragment {

    @BindView(R.id.lst_courses_recycleview)
    RecyclerView lstCourseRecycleView;
    @BindView(R.id.course_list_footer)
    LinearLayout layoutFooter;
    @BindView(R.id.btn_menu)
    ImageButton btnMenu;
    ListCourseAdapter listCourseAdapter;
    Activity activity;
    @BindView(R.id.txt_page_number)
    TextView txtPageNumber;
    @BindView(R.id.btn_next_page)
    ImageButton btnNextPage;
    @BindView(R.id.btn_previous_page)
    ImageButton btnPreviousPage;
    @BindView(R.id.btn_search)
    ImageButton btnSearch;
    private int mCurrentPage;

    @Override
    public void onStart() {
        super.onStart();

        activity = (CourseListActivity) getActivity();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        lstCourseRecycleView.setItemAnimator(new DefaultItemAnimator());
        lstCourseRecycleView.setLayoutManager(layoutManager);
        lstCourseRecycleView.addItemDecoration(new DividerItemDecoration(activity,0));
        setFooter();

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuPage = new Intent(activity, MenuPage.class);
                activity.startActivity(menuPage);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              CourseListActivity.getInstance().showSearchPage();
            }
        });
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(1);
            }
        });
        btnPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(-1);
            }
        });
        mCurrentPage = 1;
        changePage(0);
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

    void changePage(int nextPage) {

        int totalCourse = ListCourseAPI.getInstance().getCourseSize();
        int totalPage = totalCourse / 3 + 1;
        int currentValue = mCurrentPage;
        mCurrentPage += nextPage;
        if (mCurrentPage > totalPage) mCurrentPage = totalPage;
        if (mCurrentPage < 1) mCurrentPage = 1;
        if (mCurrentPage != currentValue || nextPage == 0) {
            txtPageNumber.setText(mCurrentPage + "/" + totalPage);
            setRecycle();
        }
    }

    void setRecycle() {

        List<Course> list_courses = ListCourseAPI.getInstance().getCourseByPage(mCurrentPage);
        listCourseAdapter = new ListCourseAdapter(list_courses, activity);
        lstCourseRecycleView.setAdapter(listCourseAdapter);
    }
}

