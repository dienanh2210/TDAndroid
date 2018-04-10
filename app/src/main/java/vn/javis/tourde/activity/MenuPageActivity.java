package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.BasicInfomation.BasicInfoActivity;

public class MenuPageActivity extends BaseActivity {

    TextView tv_close,tv_tutorial,tv_basic,tv_userregistration;
    RelativeLayout tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_menu_page);
        tv_login=findViewById( R.id.login );
        tv_login.setOnClickListener( onClickLogin );

        tv_close=findViewById( R.id.tv_close );
        tv_close.setOnClickListener( onClick );

        tv_tutorial=findViewById( R.id.tv_tutorial );
        tv_tutorial.setOnClickListener( onClickTutorial );

        tv_basic=findViewById( R.id.tv_basic );
        tv_basic.setOnClickListener( onClickBasicInfo );

        tv_userregistration=findViewById( R.id.tv_userregistration );
        tv_userregistration.setOnClickListener( onClickNewUser );
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





