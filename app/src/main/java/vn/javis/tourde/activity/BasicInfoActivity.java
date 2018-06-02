package vn.javis.tourde.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ListArea;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.view.CircleTransform;

public class BasicInfoActivity extends BaseActivity {

    TextView tv_back_basicInfo, tv_close_basicInfo, tv_UserEmail, tv_Username, sex, age, prefecture;
    ImageView img_avatar;
    Button updateInfo;
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
        sex = findViewById(R.id.sex);
        age = findViewById(R.id.age);
        prefecture = findViewById(R.id.prefecture);
        updateInfo=findViewById(R.id.updateInfo);
        updateInfo.setOnClickListener(changeBasicInfo);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        tv_Username.setText(LoginFragment.getmAccount().getNickname());
        tv_UserEmail.setText(LoginFragment.getmAccount().getEmail());
        if(LoginFragment.getmAccount().getSex().equals("1"))
            sex.setText("男性");
        else
            sex.setText("女性");

        age.setText(LoginFragment.getmAccount().getAge()+"代");

        int area = Integer.parseInt(LoginFragment.getmAccount().getArea());

        prefecture.setText(ListArea.getAreaName(area-1));

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
    View.OnClickListener changeBasicInfo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(BasicInfoActivity.this, RegisterActivity.class);
            intent.putExtra(Constant.KEY_CHANGE_INFO,"1");
            startActivity(intent);
        }
    };

}
