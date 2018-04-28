package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.javis.tourde.R;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class BasicInfoActivity extends BaseActivity {

    TextView tv_back_basicInfo, tv_close_basicInfo, tv_UserEmail, tv_Username;
    ImageView img_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        tv_back_basicInfo = findViewById(R.id.tv_back_basicInfo);
        tv_back_basicInfo.setOnClickListener(onClickBackBasicInfo);
        tv_close_basicInfo = findViewById(R.id.tv_close_basicInfo);
        tv_close_basicInfo.setOnClickListener(onClickCloseBasicInfo);
        tv_Username = findViewById(R.id.tv_Username);
        tv_UserEmail = findViewById(R.id.tv_UserEmail);
        img_avatar = findViewById(R.id.avatar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_Username.setText(SharedPreferencesUtils.getInstance(getBaseContext()).getStringValue("Username"));
        tv_UserEmail.setText(SharedPreferencesUtils.getInstance(getBaseContext()).getStringValue("Email"));

    }

    View.OnClickListener onClickCloseBasicInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(BasicInfoActivity.this, CourseListActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener onClickBackBasicInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(BasicInfoActivity.this, MenuPageActivity.class);
            startActivity(intent);
        }
    };


}
