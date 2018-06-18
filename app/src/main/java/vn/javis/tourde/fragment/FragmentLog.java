package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListSpotLog;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.model.CheckedSpot;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;

import static vn.javis.tourde.fragment.FragmentTabLayoutRunning.KEY_SHARED_BASETIME;

public class FragmentLog extends BaseFragment {
    View mView;
    CourseListActivity mActivity;
    @BindView(R.id.txt_prezent)
    TextView txtPrezent;
    @BindView(R.id.recycler_spot)
    RecyclerView recyclerSpot;
    ListSpotLog listSpotLogAdapter;
    List<Spot> spotDataList;
    List<CheckedSpot> lstCheckedSpot;


    public static FragmentLog intance(List<CheckedSpot> lstCheckedSpot) {
        FragmentLog frg = new FragmentLog();
        frg.lstCheckedSpot = lstCheckedSpot;
        return  frg;
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.log_fragment, container, false);
        return mView;
    }
    int courseId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (CourseListActivity) getActivity();
        if (SharedPreferencesUtils.getInstance(getContext()).getLongValue(KEY_SHARED_BASETIME) == 0) {
            courseId = mActivity.getmCourseID();
        } else {
            courseId = SharedPreferencesUtils.getInstance(getContext()).getIntValue("CourseID");

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        SharedPreferencesUtils.getInstance(getContext()).setIntValue("CourseID", courseId);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerSpot.setLayoutManager(layoutManager);
        recyclerSpot.setNestedScrollingEnabled(false);
        ProcessDialog.showProgressDialog(mActivity, "Loading", false);
        GetCourseDataAPI.getCourseData(courseId, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("error")) {
                    Log.i("error", "aaaa");
                } else {
                    CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);
                    if (txtPrezent == null)
                        return;
                    txtPrezent.setText(mCourseDetail.getmCourseData().getTitle());
                    setRecyclerSpot();
                }
                ProcessDialog.hideProgressDialog();
            }

            @Override
            public void onError(VolleyError error) {
                ProcessDialog.hideProgressDialog();
            }
        });

    }
     void setRecyclerSpot(){

        listSpotLogAdapter = new ListSpotLog(lstCheckedSpot, mActivity);
        if(mView==null && recyclerSpot==null)
        {
            return;
        }
        if (recyclerSpot == null)
            recyclerSpot = mView.findViewById(R.id.recycler_spot);
        recyclerSpot.setAdapter(listSpotLogAdapter);
    }
    public void updateCheckedSpot(List<CheckedSpot> lstCheckedSpot){
        this.lstCheckedSpot = lstCheckedSpot;
        setRecyclerSpot();
    }

}
