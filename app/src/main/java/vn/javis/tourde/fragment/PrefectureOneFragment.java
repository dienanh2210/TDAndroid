package vn.javis.tourde.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.AreaAdapter;
import vn.javis.tourde.model.MultiCheckGenre;
import vn.javis.tourde.utils.SearchCourseUtils;

public class PrefectureOneFragment extends Fragment {

    private RecyclerView rcv_list;
    private List<MultiCheckGenre> dataList;
    List<String> listContent = new ArrayList<>();

    private Button btn_choose;
    private OnFragmentInteractionListener listener;
    private String contentArea = "北海道";
    TextView tv_close, tv_back_menu_entry;
    AreaAdapter listAdapter;

    public static PrefectureOneFragment newInstance(View.OnClickListener listener,String txtSelected) {
        PrefectureOneFragment fragment = new PrefectureOneFragment();
        fragment.listener = (OnFragmentInteractionListener) listener;
      // String[] s1 = txtSelected.split(Pattern.quote("["));

      //   fragment.listContent.add(s1[0]);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prefecture_one_fragment, container, false);
        rcv_list = view.findViewById(R.id.rcv_list);
        btn_choose = view.findViewById(R.id.btn_choose);

        tv_close = view.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(onClickClose);
        tv_back_menu_entry = view.findViewById(R.id.tv_back_menu_entry);
        tv_back_menu_entry.setOnClickListener(onClickBack);


        initRecycleView();
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    List<String> contents = listAdapter.getListText();
                    StringBuilder stringBuilder = new StringBuilder();
                    String prefix = "";
                    for (String str : contents) {
                        stringBuilder.append(prefix);
                        prefix = ",";
                        stringBuilder.append(str);
                    }
                    listener.onFragmentInteraction(stringBuilder.toString());
                   if (getActivity() != null) {
                       getActivity().onBackPressed();
                    }

                }
            }
        });
        return view;
    }

    View.OnClickListener onClickClose = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), CourseListActivity.class);
            startActivity(intent);
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
        dataList = searchCourseUtils.createDataSelectArea();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rcv_list.setLayoutManager(linearLayoutManager);
        listAdapter = new AreaAdapter(dataList);

        rcv_list.setAdapter(listAdapter);
    }

    public interface OnFragmentInteractionListener extends View.OnClickListener {
        void onFragmentInteraction(String content);

        @Override
        void onClick(View v);
    }

    private void getAllContent() {
//        for (Map.Entry<String, Boolean> entry : listAdapter.getMapContent().entrySet()) {
//            String content = entry.getKey();
//            Boolean isPick = entry.getValue();
//            if (isPick) listContent.add(content);
//
//        }
    }

}
