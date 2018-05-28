package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.FavoriteCourseAdapter;
import vn.javis.tourde.adapter.ListCommentAdapter;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.model.Review;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class FragmentFavorites extends BaseFragment {
    View mView;
    CourseListActivity mActivity;

    @BindView(R.id.recycler_favorite)
    RecyclerView recyclerFavorite;
    FavoriteCourseAdapter favoriteCourseAdapter;
    List<FavoriteCourse> listFavorCourse;

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.tab_favorites_course, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerFavorite.setItemAnimator(new DefaultItemAnimator());
        recyclerFavorite.setLayoutManager(layoutManager);
        String token = LoginFragment.getmUserToken();
        FavoriteCourseAPI.getListFavoriteCourse(token, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                Log.i("favorite",response.toString());
                listFavorCourse = FavoriteCourseAPI.getFavorites(response);
               // List<FavoriteCourse> listFavorCourse = FavoriteCourseAPI.getFavorites(response);
                favoriteCourseAdapter = new FavoriteCourseAdapter(listFavorCourse, getActivity());
                recyclerFavorite.setAdapter(favoriteCourseAdapter);
                favoriteCourseAdapter.setOnItemClickListener(new FavoriteCourseAdapter.OnItemClickedListener() {
                    @Override
                    public void onItemClick(int position) {
                        mActivity.ShowFavoriteCourseDetail(listFavorCourse.get(position).getCourseId());
                    }
                });
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }
}
