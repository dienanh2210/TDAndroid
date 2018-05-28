package vn.javis.tourde.fragment;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.javis.tourde.R;

public class GoalFragment extends BaseFragment {

    public static GoalFragment newInstance(View.OnClickListener listener) {
        GoalFragment fragment = new GoalFragment();
      //  fragment.listener = (RenewPasswordPageFragment.OnFragmentInteractionListener) listener;
        return fragment;
    }
    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate( R.layout.goal_fragment, container, false );

    }
}

