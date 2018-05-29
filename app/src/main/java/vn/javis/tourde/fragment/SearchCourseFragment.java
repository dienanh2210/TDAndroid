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
    private SearchCourseActivity mActivity;
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
            }
        });
        selectAreaRecyclerView.setAdapter(mSelectAreaAdapter);

        moreConditionRecyclerView.setLayoutManager(new WrappingLinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        moreConditionRecyclerView.setNestedScrollingEnabled(false);
        moreConditionRecyclerView.setHasFixedSize(false);
        moreConditionRecyclerView.setVisibility(additionalConditionVisible);
        mAdditionalConditionAdapter = new ListSearchAdapter( getContext(), mAdditionalConditionData, new ListSearchAdapter.OnClickItem() {
            @Override
            public void onClick(Map content) {

            }
        } );
        moreConditionRecyclerView.setAdapter(mAdditionalConditionAdapter);
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


//        for (Map.Entry<String, Boolean> entry : listSearchCourseAdapter.getMapContent().entrySet()) {
//            String content = entry.getKey();
//            Boolean isPick = entry.getValue();
//            if (isPick) listContent.add(content);
//            Log.d(listContent.toString(), "text");
//
//        }
        String listCourseType = listSearchCourseAdapter.getListCourseType().toString();
        String evelation = listSearchCourseAdapter.getTxtElevation();
        String distance = listSearchCourseAdapter.getTxtDistance();

        switch (distance) {
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

        String isOver = "0";
        String isLess = "0";
        if (evelation.equals("激坂")) {
            isOver = "1";
        } else isLess = "1";
        List<String> type = new ArrayList<>();
        if (listCourseType.contains("片道")) {
            type.add("1");
        }
        if (listCourseType.contains("往復")) {
            type.add("2");
        }
        if (listCourseType.contains("1周")) {
            type.add("3");
        }
        List<String> seasonValue = new ArrayList<>();
        if (seasonSelected.contains("1月")) seasonValue.add("1");
        if (seasonSelected.contains("2月")) seasonValue.add("2");
        if (seasonSelected.contains("3月")) seasonValue.add("4");
        if (seasonSelected.contains("4月")) seasonValue.add("8");
        if (seasonSelected.contains("5月")) seasonValue.add("16");
        if (seasonSelected.contains("6月")) seasonValue.add("32");
        if (seasonSelected.contains("7月")) seasonValue.add("64");
        if (seasonSelected.contains("8月")) seasonValue.add("128");
        if (seasonSelected.contains("9月")) seasonValue.add("256");
        if (seasonSelected.contains("10月")) seasonValue.add("512");
        if (seasonSelected.contains("11月")) seasonValue.add("1024");
        if (seasonSelected.contains("12月")) seasonValue.add("2048");


        HashMap<String, String> map = new HashMap<>();
        map.put("prefecture", tv_prefecture.getText().toString());
        map.put("distance_type", distance);
        map.put("over_elevation", isOver);
        map.put("less_elevation", isLess);
        map.put("course_type", type.toString());
        map.put("freeword", edt_search.getText().toString());
        map.put("season", seasonValue.toString());
        map.put("tag", tagSelected.toString());
        Log.i("map", map.toString());
        mActivity.onBackCLickToList(map);
    }

}
