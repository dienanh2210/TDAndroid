package vn.javis.tourde.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.activity.SearchCourseActivity;
import vn.javis.tourde.adapter.ListSearchAdapter;
import vn.javis.tourde.adapter.ListSearchCourseAdapter;
import vn.javis.tourde.model.Data;
import vn.javis.tourde.adapter.ListRegisterAdapter;

public class SearchCourseFragment extends Fragment implements View.OnClickListener, PrefectureSearchFragment.OnFragmentInteractionListener, PrefectureOneFragment.OnFragmentInteractionListener {

    private RecyclerView rcv_list;
    private List<Data> dataList;
    private Button bt_search_course;
    private OnFragmentInteractionListener listener;
    private String contentArea = "北海道";

    String prefecture = "エリアを選択";
    String prefecturetext = "こだわり条件を指定";
    TextView tv_prefecture, tv_searchtwo, tv_close;
    EditText edt_search;
    ImageView im_select_area, im_more_searching;
    private SearchCourseActivity mActivity;
    ListSearchCourseAdapter listSearchCourseAdapter;
    List<String> listContent = new ArrayList<>();

    public static SearchCourseFragment newInstance(View.OnClickListener listener) {
        SearchCourseFragment fragment = new SearchCourseFragment();
        fragment.listener = (OnFragmentInteractionListener) listener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (SearchCourseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_course_fragment, container, false);
        rcv_list = view.findViewById(R.id.rcv_list);
        bt_search_course = view.findViewById(R.id.bt_search_course);
        bt_search_course.setOnClickListener(this);
        createData();

        im_select_area = view.findViewById(R.id.im_select_area);
        im_select_area.setOnClickListener(this);
        im_more_searching = view.findViewById(R.id.im_more_searching);
        im_more_searching.setOnClickListener(this);
        tv_prefecture = view.findViewById(R.id.tv_prefecture);
        tv_prefecture.setText(prefecture);
        tv_searchtwo = view.findViewById(R.id.tv_searchtwo);
        tv_searchtwo.setText(prefecturetext);
        edt_search = view.findViewById(R.id.edt_search);

        tv_close = view.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        boolean gender = false;
        switch (v.getId()) {
            case R.id.im_select_area:
                mActivity.openPage(PrefectureOneFragment.newInstance(this), true);
                break;
            case R.id.im_more_searching:
                mActivity.openPage(PrefectureSearchFragment.newInstance(this), true);
                break;
            case R.id.tv_close:
                Intent intent = new Intent(getActivity(), CourseListActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_search_course:
                //  String tv_prefecture=getContext().toString();
                //   String tv_searchtwo=getContext().toString();
                //  String edt_search=getContext().toString();


                Log.d(tv_prefecture.getText().toString(), tv_searchtwo.getText().toString());
                Log.d(edt_search.getText().toString(), "edit text");

                getAllContent();

                break;
        }
    }

    private void createData() {
        String[] contentList1 = new String[]{"〜20km", "〜50km", "〜100km", "〜100km"};
        String[] contentList2 = new String[]{"激坂", "坂少"};
        String[] contentList3 = new String[]{"片道", "往復", "1周", ""};

        dataList = new ArrayList<>();
        Data data1 = new Data();
        data1.setTitle("距離");
        data1.setContent(Arrays.asList(contentList1));
        dataList.add(data1);
        Data data2 = new Data();
        data2.setTitle("獲得標高");
        data2.setContent(Arrays.asList(contentList2));
        dataList.add(data2);
        Data data3 = new Data();
        data3.setTitle("コース形態");
        data3.setContent(Arrays.asList(contentList3));
        dataList.add(data3);

        rcv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        listSearchCourseAdapter = new ListSearchCourseAdapter(getContext(), dataList, new ListSearchCourseAdapter.OnClickItem() {
            @Override
            public void onClick(Map content) {

            }
        });
        rcv_list.setAdapter(listSearchCourseAdapter);
    }

    public interface OnFragmentInteractionListener extends View.OnClickListener {
        void onFragmentInteraction(String content);

        @Override
        void onClick(View v);

    }

    @Override
    public void onFragmentInteraction(String content) {
        prefecture = content;
        tv_prefecture.setText(content);

    }

    @Override
    public void onFragment(String content) {
        prefecturetext = content;
        tv_searchtwo.setText(content);
    }


    private void getAllContent() {
        HashMap<String, String> map = new HashMap<>();
        map.put("prefecture", "abc,xxx");
        map.put("distance_type", "1");
        map.put("over_elevation", "0");
        map.put("less_elevation", "0");
        map.put("course_type", "0");
        map.put("freeword", "0");
        map.put("season", "0");
        map.put("tag", "0");

        for (Map.Entry<String, Boolean> entry : listSearchCourseAdapter.getMapContent().entrySet()) {
            String content = entry.getKey();
            Boolean isPick = entry.getValue();
            if (isPick) listContent.add(content);
            Log.d(listContent.toString(), "text");

        }
        mActivity.onBackCLickToList(map);
    }

}
