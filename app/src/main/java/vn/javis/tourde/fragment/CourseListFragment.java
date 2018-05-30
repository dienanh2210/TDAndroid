package vn.javis.tourde.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.MenuPageActivity;
import vn.javis.tourde.adapter.ListCourseAdapter;
import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.model.SpotData;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ProcessDialog;


public class CourseListFragment extends BaseFragment implements ServiceCallback {

    @BindView(R.id.lst_courses_recycleview)
    RecyclerView lstCourseRecycleView;
    @BindView(R.id.course_list_footer)
    LinearLayout layoutFooter;
    @BindView(R.id.btn_menu)
    ImageButton btnMenu;
    @BindView(R.id.txt_page_number)
    TextView txtPageNumber;
    @BindView(R.id.btn_next_page)
    ImageButton btnNextPage;
    @BindView(R.id.btn_previous_page)
    ImageButton btnPreviousPage;
    @BindView(R.id.btn_search)
    ImageButton btnSearch;
    @BindView(R.id.btn_badge_collection)
    RelativeLayout btnBadge;
    private int mCurrentPage;
    ListCourseAdapter listCourseAdapter;
    CourseListActivity mActivity;
    @BindView(R.id.img_home)
    ImageView imgHomeBtn;
    @BindView(R.id.txt_home)
    TextView txtHomeBtn;
    @BindView(R.id.txt_no_course)
    TextView txtNoCourse;
    @BindView(R.id.btn_my_course_footer)
    RelativeLayout btnMyCourse;
    @BindView(R.id.content_course_list)
    NestedScrollView contentCourseList;

    private int mTotalPage = 1;

    private static final int NUMBER_COURSE_ON_PAGE = 5;
    private static final int DEFAULT_PAGE = 1;
    HashMap<String, String> paramsSearch;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        lstCourseRecycleView.setLayoutManager(layoutManager);
        lstCourseRecycleView.setNestedScrollingEnabled(false);
        setFooter();
        Bundle bundle = getArguments();
        paramsSearch = new HashMap<String, String>();
        boolean search = false;
        if (bundle != null) {
            String getStr = bundle.getString("searching");
            if (getStr != null && getStr != "") {
                paramsSearch = (HashMap<String, String>) bundle.getSerializable("params");
                Log.i("lst course 97", "" + paramsSearch);
                search = true;
            }

        }
        getData(search);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuPage = new Intent(mActivity, MenuPageActivity.class);
                mActivity.startActivity(menuPage);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showSearchPage();
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
        mCurrentPage = DEFAULT_PAGE;


        btnBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showBadgeCollection();
            }
        });
        btnMyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showMyCourse();
            }
        });
        imgHomeBtn.setBackground(getResources().getDrawable(R.drawable.icon_homeclick));
        txtHomeBtn.setTextColor(getResources().getColor(R.color.SkyBlue));
        String token = LoginFragment.getmUserToken();
        Intent intent = mActivity.getIntent();
        if (intent.hasExtra(Constant.KEY_LOGOUT_SUCCESS)) {
            if (token == "" && intent.getIntExtra(Constant.KEY_LOGOUT_SUCCESS, 0) == 1) {
                ProcessDialog.showDialogOk(getContext(), "", "ログアウトしました。");
            }
        }

    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
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

    void getData(boolean searching) {

        if (!searching) {
            ListCourseAPI.getJsonValues(this);
        } else {
            ListCourseAPI.getJsonValueSearch(paramsSearch, this);
        }
        Log.i("lst course 184", searching + "" + paramsSearch);
    }

    void changePage(int nextPage) {

        try {
            int totalCourse = ListCourseAPI.getInstance().getCourseSize();
            Log.i("aaa", ListCourseAPI.getInstance().getCourseSize() + "");
            if (totalCourse == 0) {
                txtNoCourse.setVisibility(View.VISIBLE);
                contentCourseList.setVisibility(View.GONE);
            } else {
                txtNoCourse.setVisibility(View.GONE);
                contentCourseList.setVisibility(View.VISIBLE);
                mTotalPage = totalCourse / NUMBER_COURSE_ON_PAGE == 0 ? 1 : totalCourse / NUMBER_COURSE_ON_PAGE;
                int currentValue = mCurrentPage;
                mCurrentPage += nextPage;
                if (mCurrentPage > mTotalPage) mCurrentPage = mTotalPage;
                if (mCurrentPage < 1) mCurrentPage = 1;
                if (mCurrentPage != currentValue || nextPage == 0) {
                    txtPageNumber.setText(mCurrentPage + "/" + mTotalPage);
                    setRecycle();
                }

                changeButtonBackground();
            }
        } catch (Exception e) {

        }
    }

    void setRecycle() {
        List<Course> list_courses = ListCourseAPI.getInstance().getCourseByPage(mCurrentPage);
        listCourseAdapter = new ListCourseAdapter(list_courses, mActivity);
        lstCourseRecycleView.setAdapter(listCourseAdapter);
        listCourseAdapter.setOnItemClickListener(new ListCourseAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int position) {
                mActivity.ShowCourseDetail(position);
            }
        });
    }

    private void changeButtonBackground() {
        if (mCurrentPage == 1) {
            btnPreviousPage.setBackgroundResource(R.drawable.btn_previous_page);
        } else {
            btnPreviousPage.setBackgroundResource(R.drawable.btn_previous_red);
        }

        if (mCurrentPage == mTotalPage) {
            btnNextPage.setBackgroundResource(R.drawable.btn_next_page_gray);
        } else {
            btnNextPage.setBackgroundResource(R.drawable.btn_next_page);
        }

    }


    @Override
    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
        ListCourseAPI.setAllCourses((JSONObject) response);
        changePage(0);
    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

