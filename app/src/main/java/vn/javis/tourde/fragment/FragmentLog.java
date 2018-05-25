package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListSpotCheckinAdapter;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;


public class FragmentLog extends BaseFragment {
    View mView;
    CourseListActivity mActivity;
    @BindView(R.id.txt_prezent)
    TextView txtPrezent;

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.log_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        int courseId = mActivity.getmCourseID();
        Log.i("xxx","xxxx");
        GetCourseDataAPI.getCourseData(courseId, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;

                    CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);
                    txtPrezent.setText(mCourseDetail.getmCourseData().getTitle());

            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }
}
