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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.AreaAdapter;
import vn.javis.tourde.adapter.ListAdapter;
import vn.javis.tourde.adapter.ListSearchAdapter;
import vn.javis.tourde.adapter.MoreConditionAdapter;
import vn.javis.tourde.model.Data;
import vn.javis.tourde.model.MultiCheckGenre;
import vn.javis.tourde.utils.SearchCourseUtils;

public class PrefectureSearchFragment extends Fragment {

    private RecyclerView rcv_list;
    private List<MultiCheckGenre> dataList;
    private Button btn_choose;
    private OnFragmentInteractionListener listener;
    TextView tv_close,tv_back_menu_entry;
    List<String> listContent = new ArrayList<>();

    private String contentArea = "北海道";
    MoreConditionAdapter listSearchAdapter;
    private static List<String> sMoreChosen = new ArrayList<>();

    public static PrefectureSearchFragment newInstance(View.OnClickListener listener,  String txtSelected) {
        PrefectureSearchFragment fragment = new PrefectureSearchFragment();
        fragment.listener = (OnFragmentInteractionListener) listener;
        sMoreChosen.clear();
        if (!txtSelected.isEmpty()) {

            String[] strings = txtSelected.split(Pattern.quote(" 、"));
            sMoreChosen.addAll(Arrays.asList(strings));
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.prefecture_search_fragment, container, false);
        rcv_list = view.findViewById(R.id.rcv_list);
        btn_choose = view.findViewById(R.id.btn_choose);

        tv_close=view.findViewById( R.id.tv_close );
        tv_close.setOnClickListener( onClickClose );
        tv_back_menu_entry=view.findViewById( R.id.tv_back_menu_entry );
        tv_back_menu_entry.setOnClickListener( onClickBack );

        initRecycleView();
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    List<String> contents = listSearchAdapter.getListText();
                    StringBuilder stringBuilder = new StringBuilder();
                    String prefix = "";
                    for (String str : contents) {
                        stringBuilder.append(prefix);
                        prefix = " 、";
                        stringBuilder.append(str);
                    }
                    listener.onFragment(stringBuilder.toString());
                    if (getActivity() != null) {
                        getActivity().onBackPressed();
                    }
                    //listener.onFragment( listSearchAdapter.getListSeason().toString(),listSearchAdapter.getListTag().toString() );
                }
            }
        });
        return view;
    }
    View.OnClickListener onClickClose = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( getActivity(), CourseListActivity.class );
            startActivity( intent );
        }
    };
    View.OnClickListener onClickBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        }
    };

    private void initRecycleView() {
        SearchCourseUtils searchCourseUtils = new SearchCourseUtils();
        dataList = searchCourseUtils.createAdditionalConditionData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rcv_list.setLayoutManager(linearLayoutManager);
        listSearchAdapter = new MoreConditionAdapter(dataList, getContext(), sMoreChosen);

        rcv_list.setAdapter(listSearchAdapter);
    }
    public interface OnFragmentInteractionListener {
        // void onFragmentInteraction(String content);
        void onFragment(String content);
        void onFragment(String season,String tag);
    }

}
