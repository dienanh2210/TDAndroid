package vn.javis.tourde.fragment.UsePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.MenuEntryActivity;
import vn.javis.tourde.activity.MenuPageActivity;
import vn.javis.tourde.activity.UsePageActivity;
import vn.javis.tourde.activity.ViewPageActivity;
import vn.javis.tourde.fragment.CourseListFragment;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class UsePageSevenFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.use_page_seven_fragment, null );
        return view;
    }
}