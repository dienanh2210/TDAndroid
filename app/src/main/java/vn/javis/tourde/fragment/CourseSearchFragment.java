package vn.javis.tourde.fragment;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;

public class CourseSearchFragment extends BaseFragment{

    @BindView(R.id.btn_close_seach_page)
    TextView btnClose;
    Activity activity;
    @Override
    public void onStart() {
        super.onStart();
        activity = (CourseListActivity) getActivity();
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseListActivity.getInstance().showCourseListPage();
            }
        });
    }
    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.search_page, container, false);
    }
}
