package vn.javis.tourde.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListAdapter;
import vn.javis.tourde.adapter.ListSearchAdapter;
import vn.javis.tourde.adapter.ListSearchCourseAdapter;
import vn.javis.tourde.model.Data;
import vn.javis.tourde.utils.SearchCourseUtils;
import vn.javis.tourde.view.WrappingLinearLayoutManager;

public  class SearchCourseFragment extends Fragment implements View.OnClickListener, PrefectureSearchFragment.OnFragmentInteractionListener, PrefectureOneFragment.OnFragmentInteractionListener {


    private RecyclerView rcv_list;
    private List<Data> dataList;
    private Button bt_search_course;
    private OnFragmentInteractionListener listener;
    private String contentArea = "北海道";

    String prefecture = "";
    String prefecturetext = "";
    String seasonSelected = "";
    String tagSelected = "";

    TextView tv_prefecture;
    TextView tv_searchtwo;
    TextView tv_close;
    EditText edt_search;
    ImageView im_select_area, im_more_searching;
    RelativeLayout rlt_prefecture,more_searching_layout;
    private CourseListActivity mActivity;
    ListSearchCourseAdapter listSearchCourseAdapter;
    List<String> listContent = new ArrayList<>();

    private SearchCourseUtils mSearchCourseUtils;

    public static SearchCourseFragment newInstance(OnFragmentInteractionListener listener) {
        SearchCourseFragment fragment = new SearchCourseFragment();
        fragment.listener = listener;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (CourseListActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_course_fragment, container, false);
        rcv_list = view.findViewById(R.id.rcv_list);
        bt_search_course = view.findViewById(R.id.bt_search_course);
        bt_search_course.setOnClickListener(this);
        im_select_area = view.findViewById(R.id.im_select_area);
        im_select_area.setOnClickListener(this);

        rlt_prefecture=view.findViewById( R.id.rlt_prefecture );
        rlt_prefecture.setOnClickListener( this );
        more_searching_layout=view.findViewById( R.id.more_searching_layout );
        more_searching_layout.setOnClickListener( this );
        im_more_searching = view.findViewById(R.id.im_more_searching);
        im_more_searching.setOnClickListener(this);
        tv_prefecture = view.findViewById(R.id.tv_prefecture);
        tv_prefecture.setText(prefecture);
        tv_searchtwo = view.findViewById(R.id.tv_searchtwo);
        tv_searchtwo.setText(prefecturetext);
        edt_search = view.findViewById(R.id.edt_search);
        tv_close = view.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(this);

        mSearchCourseUtils = new SearchCourseUtils();
        initData();
        return view;
    }

    @Override
    public void onClick(View v) {

        boolean gender = false;
        switch (v.getId()) {
            case R.id.rlt_prefecture:
                if (!prefecture.isEmpty()) {
                    prefecture = tv_prefecture.getText().toString().trim();
                }
                mActivity.openPage(PrefectureOneFragment.newInstance(this, prefecture), true, true);
               // mActivity.openPage(GoalFragment.newInstance(this), true, true);
                break;
            case R.id.more_searching_layout:
                if (!prefecturetext.isEmpty()) {
                    prefecturetext = tv_searchtwo.getText().toString().trim();
                }
                mActivity.openPage(PrefectureSearchFragment.newInstance(this, prefecturetext), true, true);
                break;
            case R.id.tv_close:
                mActivity.onBackPressed();
                break;
            case R.id.bt_search_course:
                //  String tv_prefecture=getContext().toString();
                //   String tv_searchtwo=getContext().toString();
                //  String edt_search=getContext().toString();
                Log.d(tv_prefecture.getText().toString(), tv_searchtwo.getText().toString());
                Log.d(edt_search.getText().toString(), "edit text");

                getAllContent();
                mActivity.onBackPressed();

                break;
        }
    }

    private void initData() {
        dataList = mSearchCourseUtils.createResearchCourseData();
        rcv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        listSearchCourseAdapter = new ListSearchCourseAdapter(getContext(), dataList, new ListSearchCourseAdapter.OnClickItem() {
            @Override
            public void onClick(Map content) {


            }
        });
        rcv_list.setAdapter(listSearchCourseAdapter);

    }

    private void setTextOnTextView(TextView textView, Map content) {
        if (content.size() == 0) {
            textView.setText(prefecture);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String prefix = "";
        for (Object str : content.keySet()) {
            stringBuilder.append(prefix);
            prefix = ",";
            stringBuilder.append(str);
        }
        textView.setText(stringBuilder.toString());
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(HashMap<String, String> map);

    }

    @Override
    public void onFragmentInteraction(String content) {
        prefecture = content;
        if (content.isEmpty()) {
            tv_prefecture.setText(mActivity.getResources().getString(R.string.search_course_select_area));
        } else {
            tv_prefecture.setText(content);
        }




    }

    @Override
    public void onFragment(String content) {
        prefecturetext = content;
        if (content.isEmpty()) {
           tv_searchtwo.setText(mActivity.getResources().getString(R.string.search_course_more_searching_description));
        } else {
            tv_searchtwo.setText(content);
        }
    }

    @Override
    public void onFragment(String season, String tag) {
        seasonSelected = season;
        tagSelected = tag;
    }

    private void getAllContent() {

        String listCourseType = listSearchCourseAdapter.getListCourseType().toString();
        String evelation = listSearchCourseAdapter.getTxtElevation();
        String dist = listSearchCourseAdapter.getTxtDistance();
        String distance = "-1";
        String isOver = "-1";
        String isLess = "-1";
        String strTag = "-1";
        String strSeason = "-1";
        HashMap<String, String> map = new HashMap<>();
        //check distance
        switch (dist) {
            case "〜20km":
                distance = "1";
                break;
            case "〜50km":
                distance = "2";
                break;
            case "〜100km":
                distance = "3";
                break;
            case "100km〜":
                distance = "4";
                break;
            default:
                distance = "4";
                break;
        }


        if (evelation.equals("激坂")) {
            isOver = "1";
        } else if (evelation.equals("坂少"))
            isLess = "1";

        List<String> type = new ArrayList<>();
        List<String> typeValue = new ArrayList<>();
        if (listCourseType.contains("片道")) {
            typeValue.add("1");
        }
        if (listCourseType.contains("往復")) {
            typeValue.add("2");
        }
        if (listCourseType.contains("1周")) {
            typeValue.add("3");
        }


        List<String> lstPrefectureValue = new ArrayList<String>();
        if (tv_prefecture.getText().toString() != "エリアを選択") {

            String[] str = tv_prefecture.getText().toString().trim().split(",");

            String preStr = "";
            for (int i = 0; i < str.length; i++) {
                lstPrefectureValue.add(String.valueOf(mSearchCourseUtils.getIndexPrefecture(str[i])));

            }
        }
        List<String> lstSeasonValue = new ArrayList<String>();
        List<String> lstTagValue = new ArrayList<String>();
        if (tv_searchtwo.getText().toString() != prefecturetext) {
            String[] str = tv_searchtwo.getText().toString().trim().split(",");
            for (int i = 0; i < str.length; i++) {
                String s = mSearchCourseUtils.getIndexSeason(str[i]);
                if (s != "-1") {
                    lstSeasonValue.add(s);
                } else {
                    lstTagValue.add(str[i]);
                }

            }
        }


        for (int i = 0; i < type.size(); i++) {

        }
        map.put("limit", "5");

        if (distance != "-1")
            map.put("distance_type", distance);
        if (isOver != "-1")
            map.put("over_elevation", isOver);
        if (isLess != "-1")
            map.put("less_elevation", isLess);
        for (int i = 0; i < lstPrefectureValue.size(); i++) {
            map.put("prefecture[" + i + "]", lstPrefectureValue.get(i));
        }
        for (int i = 0; i < typeValue.size(); i++) {
            map.put("course_type[" + i + "]", typeValue.get(i));
        }
        if (!edt_search.getText().toString().matches(""))
            map.put("freeword", edt_search.getText().toString());

        for (int i = 0; i < lstSeasonValue.size(); i++) {
            map.put("season[" + i + "]", lstSeasonValue.get(i));
            Log.i("", "getAllContent: "+i);
        }
        for (int i = 0; i < lstTagValue.size(); i++) {
            map.put("tag[" + i + "]", lstTagValue.get(i));
            Log.i("", "getAllContent: "+i);
        }
        Log.i("search_frg_302", map.toString());
        //   mActivity.onBackCLickToList(map);

     //   mActivity.showCourseListPage(map);
        listener.onFragmentInteraction(map);
    }

}
