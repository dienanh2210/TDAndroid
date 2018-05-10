package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.javis.tourde.R;

public class MenuEntryActivity extends BaseActivity {
    RelativeLayout rlt_usermailrigister;
    TextView tv_back_menu_entry,tv_loginEntry,tv_searchskip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_entry);
        rlt_usermailrigister = findViewById(R.id.rlt_usermailrigister);
        rlt_usermailrigister.setOnClickListener(onClickNewUserMail);
//        tv_back_menu_entry = findViewById(R.id.tv_back_menu_entry);
//        tv_back_menu_entry.setOnClickListener(onClickBackMenuPage);
        tv_loginEntry=findViewById( R.id.tv_loginEntry );
        tv_loginEntry.setOnClickListener( onClickLoginEntry );
        tv_searchskip=findViewById( R.id.tv_searchskip );
        tv_searchskip.setOnClickListener( onClickSearchSkip  );

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
}
