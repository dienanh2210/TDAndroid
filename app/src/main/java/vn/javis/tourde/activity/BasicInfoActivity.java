package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.view.CircleTransform;

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
        tv_Username.setText(LoginFragment.getmAccount().getNickname());
        tv_UserEmail.setText(LoginFragment.getmAccount().getEmail());
        if(LoginFragment.getmAccount() !=null && LoginFragment.getmAccount().getImage() !="" && LoginFragment.getmAccount().getImage() !=null)
        {
            Log.i("avatar",LoginFragment.getmAccount().getImage());
            Picasso.with(this).load(LoginFragment.getmAccount().getImage()).transform(new CircleTransform()).into(img_avatar);
        }
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
