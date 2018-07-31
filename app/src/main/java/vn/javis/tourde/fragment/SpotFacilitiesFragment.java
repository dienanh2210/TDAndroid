package vn.javis.tourde.fragment;

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

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.adapter.ListSpotFacilitiesAdapter;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.model.Data;
import vn.javis.tourde.adapter.ListRegisterAdapter;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class SpotFacilitiesFragment extends Fragment {

    private RecyclerView rcv_list;
    private List<Data> dataList;
    private Button btn_choose;
    private OnFragmentInteractionListener listener;
    private String contentArea = "北海道";
    TextView tv_back_sppot_faclities;
    HashMap<String,String> params = new HashMap<>();
    CourseListActivity activity;
    int spotId;
    boolean isCheckingSpot=false;
    String[] contentList2 = new String[]{"toilet", "parking", "accommodation", "bath", "shower", "locker", "dressing_room", "bicycle_delivery", "tourist_information", "cycle_rack", "bicycle_rental", "cycling_guide", "tool_rental", "floor_pump_rental", "mechanic_maintenance"};
    String token = SharedPreferencesUtils.getInstance(getContext()).getStringValue(LoginUtils.TOKEN);
    public static SpotFacilitiesFragment newInstance(View.OnClickListener listener) {
        SpotFacilitiesFragment fragment = new SpotFacilitiesFragment();
        //  fragment.listener = (OnFragmentInteractionListener) listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spot_facilities_fragment, container, false);
        rcv_list = view.findViewById(R.id.rcv_list);
        btn_choose = view.findViewById(R.id.btn_choose);
        tv_back_sppot_faclities = view.findViewById(R.id.tv_back_sppot_faclities);
        spotId = getArguments().getInt(CourseListActivity.SPOT_ID);
        isCheckingSpot = getArguments().getBoolean(CourseListActivity.IS_CHECKING_SPOT,false);
        Log.i("spotId","abccc"+spotId);
        params.put("token", token);
        params.put("spot_id", String.valueOf(spotId));
        for (int i = 0; i < contentList2.length; i++) {
            params.put(contentList2[i], "-1"); //change to 0 for no report, wait for api
        }
        createData();
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onFragmentInteraction( contentArea );
//                   // ((RegisterActivity) getActivity()).onBackPressed();
//                }
                SpotDataAPI.postReviewSpotEquipment(params, callback);
            }
        });
        tv_back_sppot_faclities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCheckingSpot)
                    ((CourseListActivity) getActivity()).showCheckPointFragment();
                else
                ((CourseListActivity) getActivity()).onBackPressed();
            }
        });

        return view;
    }

    private void createData() {
        String[] contentList1 = new String[]{"トイレ", "駐車場", "宿泊設備", "入浴設備", "シャワー", "ロッカー", "更衣室", "自転車配送（受取/発送）", "観光案内", "サイクルラック", "レンタサイクル", "サイクリングガイド", "工具貸出", "フロアポンプ貸出", "メカニック/メンテナンス"};

        dataList = new ArrayList<>();
        Data data1 = new Data();
        data1.setTitle("設備の有無に関してご記入ください");
        data1.setContent(Arrays.asList(contentList1));
        dataList.add(data1);

        rcv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_list.setAdapter(new ListSpotFacilitiesAdapter(getContext(), dataList, new ListSpotFacilitiesAdapter.OnClickItem() {
            @Override
            public void onClick(int pos, int val) {
                params.put(contentList2[pos], String.valueOf(val));
                Log.i("spotFacility 84", pos + "-" + val);
            }
        }));
    }

    public interface OnFragmentInteractionListener extends View.OnClickListener {
        void onFragmentInteraction(String content);
        @Override
        void onClick(View v);
    }
    //call back when call api post review
    ServiceCallback callback = new ServiceCallback() {
        @Override
        public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
          //  Log.i("spotFacility111", response.toString());
         //   ((CourseListActivity) getActivity()).onBackPressed();
            //    activity.openPage(CourseDetailSpotImagesFragment.newInstance(this), true, false);
            ((CourseListActivity) getActivity()).showSpotImages(spotId);
        }

        @Override
        public void onError(VolleyError error) {

        }
    };
}
