package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;

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
        openPage(new LoginFragment());
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // for twitter
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.container_fragment);
        loginFragment.onActivityResult(requestCode, resultCode, data);
    }


}
