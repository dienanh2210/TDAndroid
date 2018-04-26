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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.activity.SearchCourseActivity;
import vn.javis.tourde.adapter.ListSearchCourseAdapter;
import vn.javis.tourde.model.Data;
import vn.javis.tourde.adapter.ListRegisterAdapter;

public class SearchCourseFragment extends Fragment implements View.OnClickListener, PrefectureSearchFragment.OnFragmentInteractionListener, PrefectureOneFragment.OnFragmentInteractionListener{

    private RecyclerView rcv_list;
    private List<Data> dataList;
    private Button btn_choose;
    private OnFragmentInteractionListener listener;
    private String contentArea = "北海道";

    String prefecture = "エリアを選択";
    String prefecturetext = "こだわり条件を指定";
    TextView tv_prefecture, tv_searchtwo,tv_close;
    ImageView im_select_area, im_more_searching;
    private SearchCourseActivity activity;

    public static SearchCourseFragment newInstance(View.OnClickListener listener) {
        SearchCourseFragment fragment = new SearchCourseFragment();
        fragment.listener = (OnFragmentInteractionListener) listener;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        activity = (SearchCourseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.search_course_fragment, container, false );
        rcv_list = view.findViewById( R.id.rcv_list );
        btn_choose = view.findViewById( R.id.btn_choose );
        createData();

        im_select_area = view.findViewById( R.id.im_select_area );
        im_select_area.setOnClickListener( this );
        im_more_searching = view.findViewById( R.id.im_more_searching );
        im_more_searching.setOnClickListener( this );
        tv_prefecture = view.findViewById( R.id.tv_prefecture );
        tv_prefecture.setText( prefecture );
        tv_searchtwo = view.findViewById( R.id.tv_searchtwo );
        tv_searchtwo.setText( prefecturetext );

        tv_close=view.findViewById( R.id.tv_close );
        tv_close.setOnClickListener( this );
        return view;
    }
    @Override
    public void onClick(View v) {

        boolean gender = false;
        switch (v.getId()) {
            case R.id.im_select_area:
                activity.openPage( PrefectureOneFragment.newInstance( this ), true );
                break;
            case R.id.im_more_searching:
                activity.openPage( PrefectureSearchFragment.newInstance( this ), true );
                break;
            case R.id.tv_close:
                Intent intent = new Intent( getActivity(), CourseListActivity.class );
                startActivity( intent );
                break;
        }
    }

    private void createData() {
        String[] contentList1 = new String[]{"〜20km","〜50km","〜100km","〜100km"};
        String[] contentList2 = new String[]{"激坂", "坂少"};
        String[] contentList3 = new String[]{"片道", "往復", "1周",""};

        dataList = new ArrayList<>();
        Data data1 = new Data();
        data1.setTitle( "距離" );
        data1.setContent( Arrays.asList( contentList1 ) );
        dataList.add( data1 );
        Data data2 = new Data();
        data2.setTitle( "獲得標高" );
        data2.setContent( Arrays.asList( contentList2 ) );
        dataList.add( data2 );
        Data data3 = new Data();
        data3.setTitle( "コース形態" );
        data3.setContent( Arrays.asList( contentList3 ) );
        dataList.add( data3 );
        Data data4 = new Data();

        rcv_list.setLayoutManager( new LinearLayoutManager( getContext() ) );
        rcv_list.setAdapter( new ListSearchCourseAdapter( getContext(), dataList, new ListSearchCourseAdapter.OnClickItem() {
            @Override
            public void onClick(String content) {
                contentArea = content;
            }
        } ) );
    }

    public interface OnFragmentInteractionListener extends View.OnClickListener {
        void onFragmentInteraction(String content);
        @Override
        void onClick(View v);
    }

    @Override
    public void onFragmentInteraction(String content) {
        prefecture = content;
        tv_prefecture.setText( content );

    }

    @Override
    public void onFragment(String content) {
        prefecturetext = content;
        tv_searchtwo.setText( content );
    }
}
