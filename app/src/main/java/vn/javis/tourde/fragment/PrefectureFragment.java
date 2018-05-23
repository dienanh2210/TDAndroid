package vn.javis.tourde.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.model.Data;
import vn.javis.tourde.adapter.ListRegisterAdapter;

public class PrefectureFragment extends Fragment {

    private RegisterActivity mActivity;
    private RecyclerView rcv_list;
    private OnFragmentInteractionListener listener;
    private String contentArea = "北海道";
    TextView txtbackmenu;
    TextView txtclose;
    int valueArea;
    public static PrefectureFragment newInstance(View.OnClickListener listener) {
        PrefectureFragment fragment = new PrefectureFragment();
        fragment.listener = (OnFragmentInteractionListener) listener;
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_prefecture_fragment, container, false );
        rcv_list = view.findViewById( R.id.rcv_list );
        Button btn_choose = view.findViewById(R.id.btn_choose);
        createData();
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
                    listener.onFragmentInteraction( valueArea, contentArea );
                    ((RegisterActivity) getActivity()).onBackPressed();
                }
            }
        } );
        return view;
    }

    private void createData() {
        final String[] contentList1 = new String[]{"北海道"};
        final String[] contentList2 = new String[]{"青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県"};
        final String[] contentList3 = new String[]{"茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県"};
        final String[] contentList4 = new String[]{"新潟県", "富山県", "石川県", "福井県", "長野県", "山梨県", "岐阜県", "静岡県", "愛知県"};
        final String[] contentList5 = new String[]{"三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県"};
        final String[] contentList6 = new String[]{"鳥取県", "島根県", "岡山県", "広島県", "山口県"};
        final String[] contentList7 = new String[]{"徳島県", "香川県", "愛媛県", "愛媛県", "高知県"};
        final String[] contentList8 = new String[]{"福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"};

        final ArrayList<String>listArea = new ArrayList<>();
        listArea.addAll(Arrays.asList(contentList1));
        listArea.addAll(Arrays.asList(contentList2));
        listArea.addAll(Arrays.asList(contentList3));
        listArea.addAll(Arrays.asList(contentList4));
        listArea.addAll(Arrays.asList(contentList5));
        listArea.addAll(Arrays.asList(contentList6));
        listArea.addAll(Arrays.asList(contentList7));
        listArea.addAll(Arrays.asList(contentList8));

        List<Data> dataList = new ArrayList<>();
        Data data1 = new Data();
        data1.setTitle( "北海道地方" );
        data1.setContent( Arrays.asList( contentList1 ) );
        dataList.add( data1 );
        final Data data2 = new Data();
        data2.setTitle( "東北地方" );
        data2.setContent( Arrays.asList( contentList2 ) );
        dataList.add( data2 );
        Data data3 = new Data();
        data3.setTitle( "関東地方" );
        data3.setContent( Arrays.asList( contentList3 ) );
        dataList.add( data3 );
        Data data4 = new Data();
        data4.setTitle( "中部地方" );
        data4.setContent( Arrays.asList( contentList4 ) );
        dataList.add( data4 );
        Data data5 = new Data();
        data5.setTitle( "近畿地方" );
        data5.setContent( Arrays.asList( contentList5 ) );
        dataList.add( data5 );
        Data data6 = new Data();
        data6.setTitle( "中国地方" );
        data6.setContent( Arrays.asList( contentList6 ) );
        dataList.add( data6 );
        Data data7 = new Data();
        data7.setTitle( "四国地方" );
        data7.setContent( Arrays.asList( contentList7 ) );
        dataList.add( data7 );
        Data data8 = new Data();
        data8.setTitle( "四国地方" );
        data8.setContent( Arrays.asList( contentList8 ) );
        dataList.add( data8 );
        rcv_list.setLayoutManager( new LinearLayoutManager( getContext() ) );
        rcv_list.setAdapter( new ListRegisterAdapter( getContext(), dataList,false, new ListRegisterAdapter.OnClickItem() {

            @Override
            public void onClick(int position) {


            }
            @Override
            public void onClick(String content) {
                int pos = listArea.indexOf(content);
                valueArea = pos;
                contentArea = content;
            }
        } ) );
    }

    public interface OnFragmentInteractionListener extends View.OnClickListener {
        void onFragmentInteraction(int valueArea, String content);
        void onClick(View v);

    }

}
