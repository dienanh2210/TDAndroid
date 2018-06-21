package vn.javis.tourde.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.LoginSNSActivity;
import vn.javis.tourde.activity.MenuPageActivity;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.adapter.ListCourseAdapter;
import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.model.ListCourses;
import vn.javis.tourde.model.SpotData;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ProcessDialog;


public class CourseListFragment extends BaseFragment implements ServiceCallback, SearchCourseFragment.OnFragmentInteractionListener {

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
    private static final int NUMBER_COURSE_ON_PAGE = 10;
    private static final int DEFAULT_PAGE = 1;
    int totalCourseSize =0;
    String token = LoginFragment.getmUserToken();
    HashMap<String, String> paramsSearch = new HashMap<String, String>();;
    List<Course> list_courses = new ArrayList<>();
    boolean search = false;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        lstCourseRecycleView.setLayoutManager(layoutManager);
        lstCourseRecycleView.setNestedScrollingEnabled(false);
        setFooter();
        Bundle bundle = getArguments();
        mCurrentPage=1;
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
                   mActivity.showSearchPage(CourseListFragment.this);
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
                if (token != "")
                    mActivity.showBadgeCollection();
                else
                    mActivity.showDialogWarning();
            }
        });
        btnMyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (token != "")
                    mActivity.showMyCourse();
                else
                    mActivity.showDialogWarning();
            }
        });
        imgHomeBtn.setBackground(getResources().getDrawable(R.drawable.icon_homeclick));
        txtHomeBtn.setTextColor(getResources().getColor(R.color.SkyBlue));
        Intent intent = mActivity.getIntent();
        if (intent.hasExtra(Constant.KEY_LOGOUT_SUCCESS)) {
            if (token == "" && intent.getIntExtra(Constant.KEY_LOGOUT_SUCCESS, 0) == 1) {
                ProcessDialog.showDialogOk(getContext(), "", "ログアウトしました。");
                intent.removeExtra(Constant.KEY_LOGOUT_SUCCESS);
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
        ProcessDialog.showProgressDialog(mActivity,"Loading",false);
        if (!searching) {
            ListCourseAPI.getJsonValues(this,mCurrentPage,NUMBER_COURSE_ON_PAGE);
        } else {
            ListCourseAPI.getJsonValueSearch(paramsSearch, this,mCurrentPage,NUMBER_COURSE_ON_PAGE);
        }
        Log.i("lst course 184", searching + "" + paramsSearch);
    }

    void changePage(int nextPage) {

        try {

            Log.i("aaa", ListCourseAPI.getInstance().getCourseSize() + "");
            if (totalCourseSize == 0) {
                txtNoCourse.setVisibility(View.VISIBLE);
                contentCourseList.setVisibility(View.GONE);
            }
            else {
                txtNoCourse.setVisibility(View.GONE);
                contentCourseList.setVisibility(View.VISIBLE);

                mTotalPage = totalCourseSize / NUMBER_COURSE_ON_PAGE == 0 ? 1 : (totalCourseSize / NUMBER_COURSE_ON_PAGE)+1;

                int currentValue = mCurrentPage;
                mCurrentPage += nextPage;
                if (mCurrentPage > mTotalPage) mCurrentPage = mTotalPage;
                if (mCurrentPage < 1) mCurrentPage = 1;
                if (mCurrentPage != currentValue) {
                    txtPageNumber.setText(mCurrentPage + "/" + mTotalPage);
                    getData(search);
                }

                changeButtonBackground();
            }
        } catch (Exception e) {
            Log.i("CourseListError 216", e.getMessage());
        }
    }

    void setRecycle() {
        listCourseAdapter = new ListCourseAdapter(list_courses, mActivity);
        lstCourseRecycleView.setAdapter(listCourseAdapter);
        listCourseAdapter.setOnItemClickListener(new ListCourseAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int id) {
                mActivity.ShowCourseDetailById(id);
            }

            @Override
            public void openPage(Fragment fragment) {
                Intent intent = new Intent(mActivity, LoginSNSActivity.class);
                startActivity(intent);
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
    void setAllCourses(JSONObject jsonObject) {
        list_courses.clear();
        List<Course> list1 =new ArrayList<>();
        try {
            int abc = 0;
            JSONObject allJsonObject = jsonObject.getJSONObject("list");
            Iterator<String> key = allJsonObject.keys();
            while (key.hasNext())
            {
                abc++;
                String id = key.next();
                JSONObject singleJsonObject = allJsonObject.getJSONObject(id).getJSONObject("data");
                JSONArray singleJsonObjectTag = allJsonObject.getJSONObject(id).getJSONArray("tag");
                Gson gson = new GsonBuilder().serializeNulls().create();
                String vl = singleJsonObject.toString();
                ArrayList<String> listTag = new ArrayList<String>();
                Course thisCourse = Course.getData(vl);

//                if (thisCourse != null) {
//
//                    List<String> lst1 =new ArrayList<>();
//                    for (int i = 0; i < singleJsonObjectTag.length(); i++) {
//                        TagClass tagClass = TagClass.getData(singleJsonObjectTag.get(i).toString());
//                        lst1.add(tagClass.getTag());
//                    }
//                    thisCourse.setListTag(lst1);
//                    list1.add(thisCourse);
//
//                }


            }

            Collections.sort(list1, new Comparator<Course>() {
                @Override
                public int compare(Course course, Course t1) {
                    return course.getCourseId() -t1.getCourseId();
                }
            });
            list_courses =list1;
            Log.i("qa",""+abc+"s"+list1.size());
//            for(int i=list1.size()-1;i>=0;i--)
//            {
//                list_courses.add(list1.get(i));
//            }

        } catch (JSONException e) {
            System.out.println("error_" + e.getMessage());
        }
    }
    void setAllCourses(List<ListCourses.CourseArray> list1) {
        list_courses.clear();

        for(int i=0;i<list1.size();i++)
        {
           Course thisCourse = list1.get(i).getData();
           List<String> lstTag = new ArrayList<>();
            if (thisCourse != null) {

                List<String> lst1 =new ArrayList<>();
             //   int ss =list1.get(a).getTag().size()
                for (int a = 0; a < list1.get(i).getTag().size(); a++) {
                    Log.i("abcxxx",a+""+list1.get(a).getTag().size());
                    lst1.add( list1.get(i).getTag().get(a).getTag());
                }
                thisCourse.setListTag(lst1);
                list_courses.add(list1.get(i).getData());
            }

        }
        Collections.sort(list_courses, new Comparator<Course>() {
            @Override
            public int compare(Course course, Course t1) {
                return   t1.getCourseId() - course.getCourseId();
            }
        });
    }
    @Override
    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
       JSONObject jsonObject =(JSONObject) response;
        ListCourses listCourses = ListCourses.getData(response.toString());

//        totalCourseSize =Integer.parseInt(jsonObject.get("total_count").toString());
//        changePage(0);
//        setAllCourses(jsonObject);
//        setRecycle();
//        ProcessDialog.hideProgressDialog();

        totalCourseSize =listCourses.getTotalCount();
        changePage(0);
        setAllCourses(listCourses.getList());
        setRecycle();
        ProcessDialog.hideProgressDialog();
    }

    @Override
    public void onError(VolleyError error) {
        ProcessDialog.hideProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onFragmentInteraction(HashMap<String, String> map) {
        paramsSearch = map;
        search = true;
//        getData(true);
    }
}

