package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.BaseFragment;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class MenuEntryActivity extends BaseActivity {
    RelativeLayout rlt_usermailrigister;
    TextView tv_back_menu,tv_loginEntry,tv_searchskip;
    MenuEntryActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_entry);
        rlt_usermailrigister = findViewById(R.id.rlt_usermailrigister);
        rlt_usermailrigister.setOnClickListener(onClickNewUserMail);
        tv_back_menu = findViewById(R.id.tv_back_menu);
        tv_back_menu.setOnClickListener(onClickBackMenuPage);
        tv_loginEntry=findViewById( R.id.tv_loginEntry );
        tv_loginEntry.setOnClickListener( onClickLoginEntry );
        tv_searchskip=findViewById( R.id.tv_searchskip );
        tv_searchskip.setOnClickListener( onClickSearchSkip  );
        if(SharedPreferencesUtils.getInstance(this).getStringValue("MenuEntry")=="")
            tv_back_menu.setVisibility(View.GONE);
        else
            tv_back_menu.setVisibility(View.VISIBLE);
        SharedPreferencesUtils.getInstance(this).setStringValue("MenuEntry", "1");

    }

    View.OnClickListener onClickNewUserMail = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuEntryActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onClickBackMenuPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuEntryActivity.this, MenuPageActivity.class);
            startActivity(intent);
            //finish();
        }
    };
    View.OnClickListener onClickLoginEntry  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuEntryActivity.this, LoginSNSActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onClickSearchSkip  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuEntryActivity.this, CourseListActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
