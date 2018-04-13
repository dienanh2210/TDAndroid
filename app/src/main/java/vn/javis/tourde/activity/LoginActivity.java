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

    //TextView tv_back_basicinfo_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  this.requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.course_list_view);
       /* tv_back_basicinfo_login=findViewById( R.id.tv_back_basicinfo_login );
        tv_back_basicinfo_login.setOnClickListener( onClickBackBasicInfoLogin );
    }
    View.OnClickListener onClickBackBasicInfoLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( LoginActivity.this, MenuPageActivity.class );
            startActivity( intent );
        }
    };*/
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
