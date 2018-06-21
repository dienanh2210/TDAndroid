package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.FavoriteCourseAdapter;
import vn.javis.tourde.adapter.RunningCourseAdapter;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.apiservice.RunningCourseAPI;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.model.RunningCourse;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.ProcessDialog;

public class FragmentRunning extends BaseFragment {
    View mView;
    CourseListActivity mActivity;

    @BindView(R.id.recycler_running)
    RecyclerView recyclerRunning;
    RunningCourseAdapter runningCourseAdapter;
    List<RunningCourse> listRunningCourse = new ArrayList<>();

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.tab_running_course, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerRunning.setLayoutManager(layoutManager);
        final String token = LoginFragment.getmUserToken();

        RunningCourseAPI.getListRunningCourse(token, 1, 5, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                Log.i("favorite", response.toString());
                try {
                    JSONArray list = new JSONArray(response.toString());

                    for (int i = 0; i < list.length(); i++) {
                        RunningCourse model = RunningCourse.getData(list.get(i).toString());
                        // List<FavoriteCourse> listFavorCourse = FavoriteCourseAPI.getFavorites(response);
                        listRunningCourse.add(model);
                    }
                    runningCourseAdapter = new RunningCourseAdapter(listRunningCourse, mActivity);
                    if (recyclerRunning == null)
                        recyclerRunning = view.findViewById(R.id.recycler_running);
                    recyclerRunning.setAdapter(runningCourseAdapter);

                } catch (Exception e) {
                    Log.i("Error Running", e.getMessage() + "-" + recyclerRunning + "-" + runningCourseAdapter);
                }

            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }
}
