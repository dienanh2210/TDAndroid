package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.BasicInfomation.BasicInfoActivity;

public class MenuPageActivity extends BaseActivity {

    TextView btn_Close,btn_Tutorial,btn_basic,btn_UserRegistration;
    RelativeLayout btn_Login;

    RelativeLayout basic_Info,badge_Cllection,new_User,login,logout,app_Info,how_To,inquiry,privacy,rateInApp,version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_menu_page);
        btn_Login=findViewById( R.id.login );
        btn_Login.setOnClickListener( onClickLogin );

        btn_Close=findViewById( R.id.tv_close );
        btn_Close.setOnClickListener( onClick );

        btn_Tutorial=findViewById( R.id.btn_Tutorial );
        btn_Tutorial.setOnClickListener( onClickTutorial );

        btn_basic=findViewById( R.id.btn_Basic );
        btn_basic.setOnClickListener( onClickBasicInfo );

        btn_UserRegistration=findViewById( R.id.btn_UserRegistration );
        btn_UserRegistration.setOnClickListener( onClickNewUser );
    }
    View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( MenuPageActivity.this, LoginActivity.class );
            startActivity( intent );
        }
    };
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( MenuPageActivity.this, CourseListActivity.class );
            startActivity( intent );
        }
    };

    View.OnClickListener onClickTutorial = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( MenuPageActivity.this, ViewPageActivity.class );
            startActivity( intent );
        }
    };
    View.OnClickListener onClickBasicInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( MenuPageActivity.this, BasicInfoActivity.class );
            startActivity( intent );
        }
    };
    View.OnClickListener onClickNewUser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( MenuPageActivity.this, MenuEntryActivity.class );
            startActivity( intent );
        }
    };
}





