package vn.javis.tourde.fragment;

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
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.adapter.ListRegisterAdapter;
import vn.javis.tourde.model.Data;

public class AgeFragment extends Fragment {

    private RegisterActivity mActivity;
    private RecyclerView rcv_list;
    private OnFragmentInteractionListener listener;
    private String contentAge = "10代";
    TextView txtbackmenu;
    TextView txtclose;
    int valueAge;
    public static AgeFragment newInstance(View.OnClickListener listener) {
        AgeFragment fragment = new AgeFragment();
        fragment.listener = (OnFragmentInteractionListener) listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_age_fragment, container, false );
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
                    listener.onAgeFragmentInteraction(valueAge, contentAge );
                    ((RegisterActivity) getActivity()).onBackPressed();
                }
            }
        } );
        return view;
    }

    private void createData() {
        final String[] contentList = new String[]{"10代", "20代", "30代", "40代", "50代", "60代以上"};
        final int[] valueList = new int[]{10, 20, 30, 40, 50, 60};
        List<Data> dataList = new ArrayList<>();
        Data data = new Data();
        data.setContent( Arrays.asList( contentList) );
        dataList.add(data);
        rcv_list.setLayoutManager( new LinearLayoutManager( getContext() ) );
        rcv_list.setAdapter( new ListRegisterAdapter( getContext(), dataList,true, new ListRegisterAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                contentAge =contentList[position];
                valueAge = valueList[position];
            }

            @Override
            public void onClick(String content) {

            }
        } ) );
    }

    public interface OnFragmentInteractionListener extends View.OnClickListener {
        @Override
        void onClick(View v);

        void onAgeFragmentInteraction(int valueAge, String content);
    }

}
