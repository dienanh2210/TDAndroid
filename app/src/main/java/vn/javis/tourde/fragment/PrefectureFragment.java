package vn.javis.tourde.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import vn.javis.tourde.R;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.adapter.AreaAdapter;
import vn.javis.tourde.model.Data;
import vn.javis.tourde.adapter.ListRegisterAdapter;
import vn.javis.tourde.model.MultiCheckGenre;
import vn.javis.tourde.utils.SearchCourseUtils;

public class PrefectureFragment extends Fragment {

    private RegisterActivity mActivity;
    private RecyclerView rcv_list;
    private AreaAdapter mListAdapter;
    private OnFragmentInteractionListener listener;
    private List<MultiCheckGenre> dataList;
    private String contentArea = "北海道";
    TextView txtbackmenu;
    TextView txtclose;
    int valueArea;
    private static List<String> sAreaChosen = new ArrayList<>();

    public static PrefectureFragment newInstance(View.OnClickListener listener,int valueArea, String contentArea) {
        PrefectureFragment fragment = new PrefectureFragment();
        fragment.listener = (OnFragmentInteractionListener) listener;
        fragment.valueArea = valueArea;
        sAreaChosen.clear();
        sAreaChosen.add(contentArea);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_prefecture_fragment, container, false );
        rcv_list = view.findViewById( R.id.rcv_list );
        Button btn_choose = view.findViewById(R.id.btn_choose);
        initRecycleView();
        mActivity = (RegisterActivity) getActivity();
        txtbackmenu = view.findViewById(R.id.tv_back_menu_entry);
        txtclose = view.findViewById(R.id.tv_close);
        txtbackmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    contentArea = mListAdapter.getAreaContent();
                    valueArea = mListAdapter.getAreaValue();
                    listener.onFragmentInteraction( valueArea, contentArea );
                    Log.i("test area","prefecture fragment 71: "+valueArea + "-"+contentArea);
                    ((RegisterActivity) getActivity()).onBackPressed();
                }
            }
        } );
        return view;
    }

    private void initRecycleView() {
        SearchCourseUtils searchCourseUtils = new SearchCourseUtils();
        dataList = searchCourseUtils.createDataSelectArea();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rcv_list.setLayoutManager(linearLayoutManager);
        mListAdapter = new AreaAdapter(dataList, sAreaChosen, false);

        rcv_list.setAdapter(mListAdapter);
    }



    public interface OnFragmentInteractionListener extends View.OnClickListener {
        void onFragmentInteraction(int valueArea, String content);
        void onClick(View v);

    }

}
