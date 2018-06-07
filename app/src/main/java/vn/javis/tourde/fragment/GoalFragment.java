package vn.javis.tourde.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;

public class GoalFragment extends BaseFragment {

    @BindView(R.id.take_photo)
    Button takePhoto;
    int spotID;
    CourseListActivity mActivity;
    public static GoalFragment newInstance(View.OnClickListener listener) {
        GoalFragment fragment = new GoalFragment();
      //  fragment.listener = (RenewPasswordPageFragment.OnFragmentInteractionListener) listener;
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {

        return inflater.inflate( R.layout.goal_fragment, container, false );

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        spotID =1;
        mActivity = (CourseListActivity) getActivity();
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showTakePhoto(spotID);
            }
        });
    }
}

