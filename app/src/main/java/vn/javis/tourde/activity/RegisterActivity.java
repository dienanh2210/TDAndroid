package vn.javis.tourde.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.CourseListFragment;
import vn.javis.tourde.fragment.RegisterFragment;
import vn.javis.tourde.fragment.SearchCourseFragment;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ProcessDialog;

public class RegisterActivity extends BaseActivity {
    JSONObject data;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterFragment ccl =new RegisterFragment();
        openPage(ccl, false);
    }

    public void openPage(Fragment fragment, boolean isBackStack, boolean isAnimation) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        if (isAnimation)
            tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (isBackStack)
            tx.addToBackStack(null);
        tx.commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    //transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("onBackPressed", "true");
    }

}
