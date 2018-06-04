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
import vn.javis.tourde.adapter.ListAdapter;
import vn.javis.tourde.adapter.ListSearchAdapter;
import vn.javis.tourde.adapter.ListSearchCourseAdapter;
import vn.javis.tourde.model.Data;
import vn.javis.tourde.adapter.ListRegisterAdapter;
import vn.javis.tourde.utils.SearchCourseUtils;
import vn.javis.tourde.view.WrappingLinearLayoutManager;

public class SearchCourseFragment extends Fragment implements View.OnClickListener, PrefectureSearchFragment.OnFragmentInteractionListener, PrefectureOneFragment.OnFragmentInteractionListener {

    private RecyclerView rcv_list;
    private RecyclerView selectAreaRecyclerView;
    private RecyclerView moreConditionRecyclerView;
    private List<Data> dataList;
    private Button bt_search_course;
    private OnFragmentInteractionListener listener;
    private String contentArea = "北海道";

    String prefecture = "エリアを選択";
    String prefecturetext = "こだわり条件を指定";
    String seasonSelected = "";
    String tagSelected = "";

    TextView tv_prefecture, tv_searchtwo, tv_close;
    EditText edt_search;
    ImageView im_select_area, im_more_searching;
    private CourseListActivity mActivity;
    ListSearchCourseAdapter listSearchCourseAdapter;
    List<String> listContent = new ArrayList<>();

    private SearchCourseUtils mSearchCourseUtils;
    private List<Data> mSelectAreaData;
    private List<Data> mAdditionalConditionData;
    private ListAdapter mSelectAreaAdapter;
    private ListSearchAdapter mAdditionalConditionAdapter;

    private int selectAreaVisible = View.GONE;
    private int additionalConditionVisible = View.GONE;

    public static SearchCourseFragment newInstance(View.OnClickListener listener) {
        SearchCourseFragment fragment = new SearchCourseFragment();
      //  fragment.listener = (OnFragmentInteractionListener) listener;
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
        selectAreaRecyclerView = view.findViewById(R.id.select_area_list);
        moreConditionRecyclerView = view.findViewById(R.id.more_condition_list);
        bt_search_course = view.findViewById(R.id.bt_search_course);
        bt_search_course.setOnClickListener(this);
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
        mSearchCourseUtils = new SearchCourseUtils();
        initData();
        return view;
    }

    @Override
    public void onClick(View v) {

        boolean gender = false;
        switch (v.getId()) {
            case R.id.im_select_area:

                //  mActivity.openPage(PrefectureOneFragment.newInstance(this,tv_prefecture.getText().toString()), true);

                selectAreaVisible = selectAreaVisible == View.GONE ? View.VISIBLE : View.GONE;
                selectAreaRecyclerView.setVisibility(selectAreaVisible);
                if (selectAreaVisible == View.GONE) {
                    im_select_area.setImageResource(R.drawable.icon_next);
                } else {
                    im_select_area.setImageResource(R.drawable.icon_down);
                }

                break;
            case R.id.im_more_searching:

                additionalConditionVisible = additionalConditionVisible == View.GONE ? View.VISIBLE : View.GONE;
                moreConditionRecyclerView.setVisibility(additionalConditionVisible);
                if (additionalConditionVisible == View.GONE) {
                    im_more_searching.setImageResource(R.drawable.icon_next);
                } else {
                    im_more_searching.setImageResource(R.drawable.icon_down);
                }
                break;
            case R.id.tv_close:
//                Intent intent = new Intent(getActivity(), CourseListActivity.class);
//                startActivity(intent);
                mActivity.onBackPressed();
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

    private void initData() {
        dataList = mSearchCourseUtils.createResearchCourseData();
        mSelectAreaData = mSearchCourseUtils.createDataSelectArea();
        mAdditionalConditionData = mSearchCourseUtils.createAdditionalConditionData();

        rcv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        listSearchCourseAdapter = new ListSearchCourseAdapter(getContext(), dataList, new ListSearchCourseAdapter.OnClickItem() {
            @Override
            public void onClick(Map content) {

            }
        });
        rcv_list.setAdapter(listSearchCourseAdapter);


        selectAreaRecyclerView.setLayoutManager(new WrappingLinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        selectAreaRecyclerView.setNestedScrollingEnabled(false);
        selectAreaRecyclerView.setHasFixedSize(false);
        selectAreaRecyclerView.setVisibility(selectAreaVisible);
        mSelectAreaAdapter = new ListAdapter(getContext(), mSelectAreaData, new ListAdapter.OnClickItem() {
            @Override
            public void onClick(Map content) {
                setTextOnTextView(tv_prefecture, content);

            }
        });
        selectAreaRecyclerView.setAdapter(mSelectAreaAdapter);

        moreConditionRecyclerView.setLayoutManager(new WrappingLinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        moreConditionRecyclerView.setNestedScrollingEnabled(false);
        moreConditionRecyclerView.setHasFixedSize(false);
        moreConditionRecyclerView.setVisibility(additionalConditionVisible);
        mAdditionalConditionAdapter = new ListSearchAdapter(getContext(), mAdditionalConditionData, new ListSearchAdapter.OnClickItem() {
            @Override
            public void onClick(Map content) {
                setTextOnTextView(tv_searchtwo, content);
            }
        });
        moreConditionRecyclerView.setAdapter(mAdditionalConditionAdapter);
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
        String typeStr = "-1";

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
            default:
                distance = "4";
                break;
        }


        if (evelation.equals("激坂")) {
            isOver = "1";
        } else if (evelation.equals("坂少"))
            isLess = "1";

        List<String> type = new ArrayList<>();
        if (listCourseType.contains("片道")) {
            if (typeStr == "-1")
                typeStr = "1";
            else typeStr += ",1";
        }
        if (listCourseType.contains("往復")) {
            if (typeStr == "-1")
                typeStr = "2";
            else typeStr += ",2";
        }
        if (listCourseType.contains("1周")) {
            if (typeStr == "-1")
                typeStr = "3";
            else typeStr += ",3";
        }


        HashMap<String, String> map = new HashMap<>();
        if (tv_prefecture.getText().toString() != "エリアを選択") {

            String[] str = tv_prefecture.getText().toString().trim().split(",");
            String preStr = "";
            for (int i = 0; i < str.length; i++) {
                if (i < str.length - 1)
                    preStr += mSearchCourseUtils.getIndexPrefecture(str[i]) + ",";
                else
                    preStr += mSearchCourseUtils.getIndexPrefecture(str[i]);
            }
            map.put("prefecture", preStr);
        }

        if (tv_searchtwo.getText().toString() != prefecturetext) {
            String[] str = tv_searchtwo.getText().toString().trim().split(",");
            for (int i = 0; i < str.length; i++) {
                String s = mSearchCourseUtils.getIndexSeason(str[i]);
                if (s != "-1") {
                    if (strSeason == "-1")
                        strSeason = s;
                    else
                        strSeason += "," + s;
                } else {
                    if (strTag == "-1")
                        strTag = str[i];
                    else
                        strTag += "," + str[i];
                }

            }
        }


        for (int i = 0; i < type.size(); i++) {

        }
        if (distance != "-1")
            map.put("distance_type", distance);
        if (isOver != "-1")
            map.put("over_elevation", isOver);
        if (isLess != "-1")
            map.put("less_elevation", isLess);
        if (typeStr != "-1")
            map.put("course_type", typeStr);
        if (!edt_search.getText().toString().matches(""))
            map.put("freeword", edt_search.getText().toString());
        if (strSeason != "-1")
            map.put("season", strSeason);
        if (strTag != "-1")
            map.put("tag", strTag);
        Log.i("search_frg_302", map.toString());
        //   mActivity.onBackCLickToList(map);

        mActivity.showCourseListPage(map);

    }

}
