package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.fragment.FragmentTabLayoutMyCourse;
import vn.javis.tourde.utils.Constant;

public class MenuPageActivity extends BaseActivity {

    TextView tv_close, tv_basic, tv_userregistration;

    RelativeLayout tv_login,tv_tutorial,logout;
    @BindView( R.id.rlt_newuserregister )
    View rlt_newuserregisterl;
    @BindView( R.id.ll_logout )
    View ll_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_page);
        super.onCreate(savedInstanceState);
        tv_login = findViewById(R.id.login);
        tv_login.setOnClickListener(onClickLogin);

        tv_close = findViewById(R.id.tv_close);
        tv_close.setOnClickListener(onClick);

        tv_tutorial = findViewById(R.id.rlt_tutorial);
        tv_tutorial.setOnClickListener(onClickTutorial);

        tv_basic = findViewById(R.id.tv_basic);
        tv_basic.setOnClickListener(onClickBasicInfo);

        tv_userregistration = findViewById(R.id.tv_userregistration);
        tv_userregistration.setOnClickListener(onClickNewUser);

        ll_logout.setOnClickListener( onClickLogout );


    }

    View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuPageActivity.this, LoginSNSActivity.class);
            startActivityForResult(intent, 1);
        }
    };
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuPageActivity.this, CourseListActivity.class);
            startActivity(intent);

        }
    };

    View.OnClickListener onClickTutorial = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuPageActivity.this, ViewPageActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onClickBasicInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuPageActivity.this, BasicInfoActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onClickNewUser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuPageActivity.this, MenuEntryActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onClickLogout = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rlt_newuserregisterl.setVisibility( View.VISIBLE );
            ll_logout.setVisibility( View.GONE );
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("onBackPressed", "true");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                boolean str = data.getBooleanExtra( Constant.KEY_LOGIN_SUCCESS, false);
                rlt_newuserregisterl.setVisibility( View.GONE );
                ll_logout.setVisibility( View.VISIBLE );
                Log.i("onActivityResult",""+ str);
            }
        }
    }
}





