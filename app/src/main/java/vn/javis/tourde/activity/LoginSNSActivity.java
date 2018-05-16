package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.LoginFragment;

public class LoginSNSActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_view);
    }

    @Override
    protected void onResume() {
        openPage(new LoginFragment(), false);
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // for twitter
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        loginFragment.onActivityResult(requestCode, resultCode, data);
    }

    public void openPage(Fragment fragment, boolean isBackStack) {
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
