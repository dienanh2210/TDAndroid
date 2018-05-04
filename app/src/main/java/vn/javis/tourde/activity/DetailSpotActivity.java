package vn.javis.tourde.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.CourseDetailSpotImagesFragment;
import vn.javis.tourde.fragment.LoginFragment;

public class DetailSpotActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail_spot );
        openPage(new CourseDetailSpotImagesFragment(), false);
    }
    public void openPage(Fragment fragment, boolean isBackStack) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isBackStack)
            tx.addToBackStack(null);
        tx.commit();
    }
}
