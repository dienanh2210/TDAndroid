package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class FragmentLog extends BaseFragment {
    View mView;
    CourseListActivity mActivity;
    @BindView(R.id.txt_prezent)
    TextView txtPrezent;
    @BindView(R.id.recycler_spot)
    RecyclerView recyclerSpot;
    ListSpotLog listSpotLogAdapter;
    List<Spot> spotDataList;

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.log_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        final int courseId = mActivity.getmCourseID();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerSpot.setLayoutManager(layoutManager);
        recyclerSpot.setNestedScrollingEnabled(false);
        GetCourseDataAPI.getCourseData(courseId, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;

                CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);
                txtPrezent.setText(mCourseDetail.getmCourseData().getTitle());
                spotDataList = mCourseDetail.getSpot();
                listSpotLogAdapter = new ListSpotLog(spotDataList, mActivity);
                if (recyclerSpot == null)
                    recyclerSpot = mView.findViewById(R.id.recycler_spot);
                recyclerSpot.setAdapter(listSpotLogAdapter);

            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }
}
