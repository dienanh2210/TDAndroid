package vn.javis.tourde.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.twitter.sdk.android.core.services.SearchService;

import org.json.JSONObject;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.BaseActivity;
import vn.javis.tourde.activity.BasicInfomation.BasicInfoActivity;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class LoginActivity extends BaseActivity  implements ServiceCallback{

    TextView tv_back_basicinfo_login;
    Button bt_login;
    private EditText edt_emaillogin,edt_passwordlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //  this.requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_login_view );
        tv_back_basicinfo_login=findViewById( R.id.tv_back_basicinfo_login );
        tv_back_basicinfo_login.setOnClickListener( onClickBackBasicInfoLogin );


        edt_emaillogin=findViewById( R.id.edt_emaillogin );
        edt_passwordlogin=findViewById( R.id.edt_passwordlogin );

        bt_login=findViewById( R.id.bt_login );
        bt_login.setOnClickListener( onClickLogin );
    }
    View.OnClickListener onClickBackBasicInfoLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( LoginActivity.this, MenuPageActivity.class );
            startActivity( intent );
        }
    };


    View.OnClickListener onClickLogin  = new View.OnClickListener() {
        boolean gender = true;
        @Override
        public void onClick(View view) {
            Log.d(edt_emaillogin.getText().toString(), edt_passwordlogin.getText().toString());
            LoginAPI.loginEmail( edt_emaillogin.getText().toString(), edt_passwordlogin.getText().toString(),LoginActivity.this);
//abc();
        }

    };
  //  void abc(){
     //   LoginAPI.loginEmail( edt_emaillogin.getText().toString(), edt_passwordlogin.getText().toString(),this);
  //  }
    @Override
    public void onSuccess(ServiceResult resultCode, Object response) {
        JSONObject jsonObject = (JSONObject) response;
        if(jsonObject.has("success"))
        {
            Log.d(edt_emaillogin.getText().toString(), edt_passwordlogin.getText().toString()+"yes");

            Intent intent = new Intent( LoginActivity.this,CourseListActivity.class );
            startActivity( intent );

        }
        else {
            Log.d(edt_emaillogin.toString(), edt_passwordlogin.toString()+"error");
        }
    }

    @Override
    public void onError(VolleyError error) {

    }
}
