package vn.javis.tourde.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.SearchCourseActivity;
import vn.javis.tourde.adapter.ListAdapter;
import vn.javis.tourde.model.Data;

public class PrefectureSearchFragment extends Fragment {

    private RecyclerView rcv_list;
    private List<Data> dataList;
    private Button btn_choose;
    private OnFragmentInteractionListener listener;
    TextView tv_close,tv_back_menu_entry;
    private SearchCourseActivity activity;

    private String contentArea = "北海道";

    public static PrefectureSearchFragment newInstance(View.OnClickListener listener) {
        PrefectureSearchFragment fragment = new PrefectureSearchFragment();
        fragment.listener = (OnFragmentInteractionListener) listener;
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
        activity= (SearchCourseActivity) getActivity();

        createData();
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    //listener.onFragmentInteraction(contentArea);
                    listener.onFragment(contentArea);
                    ((SearchCourseActivity)getActivity()).onBackPressed();
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
            activity.onBackPressed();
        }
    };

    private void createData() {
        String[] contentList1 = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
        String[] contentList2 = new String[]{"グルメが充実", "絶景・景観が充実", "名所・観光地が充実", "フォトスポットが充実", "トレーニング要素が充実"};
        String[] contentList3 = new String[]{"都会・街中", "田舎・村里", "森林・林間"};
        String[] contentList4 = new String[]{"グループライド向き", "ソロ向き", "女性向き", "ファミリー向き", "カップル向き"};
        String[] contentList5 = new String[]{"初級者向き", "中級者向き", "上級者向き"};
        String[] contentList6 = new String[]{"初級者の脚で３時間", "初級者の脚で半日", "初級者の脚で１日", "２日間以上"};
        String[] contentList7 = new String[]{"信号が少ない", "交通量が少ない", "路面状態が良好", "オフロードあり"};
        String[] contentList8 = new String[]{"坂好き向き", "坂嫌い向き"};
        String[] contentList9 = new String[]{"サイクリング大会公式コース", "アプリ内イベント専用コース","季節限定コース"};
        dataList = new ArrayList<>();
        Data data1 = new Data();
        data1.setTitle("おすすめの月");
        data1.setContent(Arrays.asList(contentList1));
        dataList.add(data1);
        Data data2 = new Data();
        data2.setTitle("コーステーマ");
        data2.setContent(Arrays.asList(contentList2));
        dataList.add(data2);
        Data data3 = new Data();
        data3.setTitle("コースエリア");
        data3.setContent(Arrays.asList(contentList3));
        dataList.add(data3);
        Data data4 = new Data();
        data4.setTitle("属性適正");
        data4.setContent(Arrays.asList(contentList4));
        dataList.add(data4);
        Data data5 = new Data();
        data5.setTitle("レベル適正");
        data5.setContent(Arrays.asList(contentList5));
        dataList.add(data5);
        Data data6 = new Data();
        data6.setTitle("所要時間");
        data6.setContent(Arrays.asList(contentList6));
        dataList.add(data6);
        Data data7 = new Data();
        data7.setTitle("道路環境");
        data7.setContent(Arrays.asList(contentList7));
        dataList.add(data7);
        Data data8 = new Data();
        data8.setTitle("登坂");
        data8.setContent(Arrays.asList(contentList8));
        dataList.add(data8);
        Data data9 = new Data();
        data9.setTitle("特殊コース");
        data9.setContent(Arrays.asList(contentList9));
        dataList.add(data9);
        rcv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_list.setAdapter( new ListAdapter( getContext(), dataList, new ListAdapter.OnClickItem() {
            @Override
            public void onClick(String content) {
                contentArea = content;
            }
        } ) );
    }
    public interface OnFragmentInteractionListener {
        // void onFragmentInteraction(String content);
        void onFragment(String content);
    }

}
