package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.FavoriteCourseAdapter;
import vn.javis.tourde.adapter.RunningCourseAdapter;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class FragmentRunning extends BaseFragment{
    View mView;
    CourseListActivity mActivity;

    @BindView(R.id.recycler_running)
    RecyclerView recyclerRunning;
    RunningCourseAdapter runningCourseAdapter;


    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.tab_running_course, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerRunning.setLayoutManager(layoutManager);
        String token = LoginFragment.getmUserToken();
//        FavoriteCourseAPI.getListFavoriteCourse(token, new ServiceCallback() {
//            @Override
//            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
//                List<FavoriteCourse> listRunCourse = FavoriteCourseAPI.getFavorites(response);
//                runningCourseAdapter = new RunningCourseAdapter(listRunCourse, getActivity());
//                recyclerRunning.setAdapter(runningCourseAdapter);
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//
//            }
//        });

    }
}
