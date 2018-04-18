package vn.javis.tourde.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.PrefectureFragment;
import vn.javis.tourde.fragment.RegisterFragment;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search );
        openPage(new PrefectureFragment(), false);
    }
    public void openPage(Fragment fragment, boolean isBackStack) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.containert, fragment, fragment.getClass().getSimpleName());
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
