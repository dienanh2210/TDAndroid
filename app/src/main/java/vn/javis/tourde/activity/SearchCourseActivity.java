package vn.javis.tourde.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.PrefectureSearchFragment;
import vn.javis.tourde.fragment.RegisterFragment;
import vn.javis.tourde.fragment.SearchCourseFragment;

public class SearchCourseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course );
        openPage(new SearchCourseFragment(), false);
    }
    public void openPage(android.support.v4.app.Fragment fragment, boolean isBackStack) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isBackStack)
            tx.addToBackStack(null);
        tx.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("onBackPressed", "true");
    }

}
