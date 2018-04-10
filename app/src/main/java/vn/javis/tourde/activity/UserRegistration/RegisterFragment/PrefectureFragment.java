package vn.javis.tourde.activity.UserRegistration.RegisterFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.javis.tourde.R;

public class PrefectureFragment extends Fragment {

    private RecyclerView rcv_list;
    private List<Data> dataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_prefecture_fragment, container, false);
        rcv_list = view.findViewById(R.id.rcv_list);
        createData();
        return view;
    }
    private void createData() {
        String[] contentList1 = new String[]{"北海道"};
        String[] contentList2 = new String[]{"青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県"};
        String[] contentList3 = new String[]{"茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県"};
        String[] contentList4 = new String[]{"新潟県", "富山県", "石川県", "福井県", "長野県", "山梨県", "岐阜県","静岡県","愛知県"};
        String[] contentList5 = new String[]{"三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県"};
        String[] contentList6 = new String[]{"鳥取県", "島根県", "岡山県", "広島県", "山口県"};
        String[] contentList7 = new String[]{"徳島県", "香川県", "愛媛県", "愛媛県", "高知県"};
        String[] contentList8 = new String[]{"福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県","沖縄県"};
        dataList = new ArrayList<>();
        Data data1 = new Data();
        data1.setTitle("北海道地方");
        data1.setContent(Arrays.asList(contentList1));
        dataList.add(data1);
        Data data2 = new Data();
        data2.setTitle("東北地方");
        data2.setContent(Arrays.asList(contentList2));
        dataList.add(data2);
        Data data3 = new Data();
        data3.setTitle("関東地方");
        data3.setContent(Arrays.asList(contentList3));
        dataList.add(data3);
        Data data4 = new Data();
        data4.setTitle("中部地方");
        data4.setContent(Arrays.asList(contentList4));
        dataList.add(data4);
        Data data5 = new Data();
        data5.setTitle("近畿地方");
        data5.setContent(Arrays.asList(contentList5));
        dataList.add(data5);
        Data data6 = new Data();
        data6.setTitle("中国地方");
        data6.setContent(Arrays.asList(contentList6));
        dataList.add(data6);
        Data data7 = new Data();
        data7.setTitle("四国地方");
        data7.setContent(Arrays.asList(contentList7));
        dataList.add(data7);
        Data data8 = new Data();
        data8.setTitle("四国地方");
        data8.setContent(Arrays.asList(contentList8));
        dataList.add(data7);
        rcv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_list.setAdapter(new ListAdapter(getContext(), dataList));
    }
}
