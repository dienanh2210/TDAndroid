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
    RelativeLayout btn_UserMailRegister;
    TextView btn_Back_Menu_Entry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_menu_entry );
        btn_UserMailRegister=findViewById( R.id.btn_UserMailRigister );
        btn_UserMailRegister.setOnClickListener( onClickNewUserMail );
        btn_Back_Menu_Entry=findViewById( R.id.btn_Back_Menu_Entry );
        btn_Back_Menu_Entry.setOnClickListener(onClickBackMenuPage  );
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
