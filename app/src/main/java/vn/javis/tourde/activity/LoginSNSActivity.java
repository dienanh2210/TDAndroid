package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import vn.javis.tourde.R;
import static vn.javis.tourde.activity.LoginFragment.RC_LN_SIGN_IN;

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
