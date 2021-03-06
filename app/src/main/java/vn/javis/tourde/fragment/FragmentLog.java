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
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.model.SaveCourseRunning;
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
    @BindView(R.id.txt_course_distance)
    TextView txtDistance;
    @BindView(R.id.recycler_spot)
    RecyclerView recyclerSpot;
    ListSpotLog listSpotLogAdapter;
    List<Spot> spotDataList;
    SaveCourseRunning saveCourseRunning;
    float distance;


    public static FragmentLog intance(SaveCourseRunning saveCourseRunning,float distance) {
        FragmentLog frg = new FragmentLog();
        frg.saveCourseRunning = saveCourseRunning;
        frg.distance =distance;
        return frg;
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
        if (saveCourseRunning == null) {
            courseId = mActivity.getmCourseID();
        } else {
            courseId = saveCourseRunning.getCourseID();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SharedPreferencesUtils.getInstance(getContext()).setIntValue("CourseID", courseId);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerSpot.setLayoutManager(layoutManager);
        recyclerSpot.setNestedScrollingEnabled(false);
        txtDistance.setText("走行距離 "+String.format("%.1f", distance) +"km");
        showProgressDialog();

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

                }
                hideProgressDialog();
                setRecyclerSpot();
            }

            @Override
            public void onError(VolleyError error) {
                hideProgressDialog();
            }
        });

    }

    void setRecyclerSpot() {
        if (saveCourseRunning == null)
            return;
        listSpotLogAdapter = new ListSpotLog(saveCourseRunning.getLstCheckedSpot(), mActivity);
        if (mView == null && recyclerSpot == null) {
            return;
        }
        if (recyclerSpot == null)
            recyclerSpot = mView.findViewById(R.id.recycler_spot);
        recyclerSpot.setAdapter(listSpotLogAdapter);
    }

    public void updateCheckedSpot(SaveCourseRunning saveCourseRunning) {
        this.saveCourseRunning = saveCourseRunning;
        setRecyclerSpot();
    }

}
