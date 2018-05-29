package vn.javis.tourde.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.RegisterFragment;
import vn.javis.tourde.utils.Constant;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String email = getIntent().getStringExtra(Constant.KEY_CHANGE_INFO);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_CHANGE_INFO,email);
        RegisterFragment ccl =new RegisterFragment();
        ccl.setArguments(bundle);
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
