package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.UserRegistration.RegisterFragment.RegisterActivity;
import vn.javis.tourde.activity.UserRegistration.RegisterFragment.RegisterFragment;

public class MenuEntryActivity extends BaseActivity {
    RelativeLayout rlt_usermailrigister;
    TextView tv_back_menu_entry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_menu_entry );
        rlt_usermailrigister=findViewById( R.id.rlt_usermailrigister);
        rlt_usermailrigister.setOnClickListener( onClickNewUserMail );
        tv_back_menu_entry=findViewById( R.id.tv_back_menu_entry );
        tv_back_menu_entry.setOnClickListener(onClickBackMenuPage  );
    }
    View.OnClickListener onClickNewUserMail = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( MenuEntryActivity.this, RegisterActivity.class );
            startActivity( intent );
        }
    };
    View.OnClickListener onClickBackMenuPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( MenuEntryActivity.this, MenuPageActivity.class );
            startActivity( intent );
        }
    };
}
