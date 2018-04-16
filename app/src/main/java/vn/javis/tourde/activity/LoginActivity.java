package vn.javis.tourde.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.app.Activity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linecorp.linesdk.auth.LineLoginApi;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.BaseActivity;
import vn.javis.tourde.activity.BasicInfomation.BasicInfoActivity;

import static vn.javis.tourde.activity.LoginFragment.RC_LN_SIGN_IN;

public class LoginActivity extends BaseActivity {

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
