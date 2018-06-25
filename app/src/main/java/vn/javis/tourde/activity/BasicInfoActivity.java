package vn.javis.tourde.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ListArea;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.view.CircleTransform;

public class BasicInfoActivity extends BaseActivity {

    TextView tv_back_basicInfo, tv_close_basicInfo, tv_UserEmail, tv_Username, sex, age, prefecture;
    ImageView img_avatar;
    Button updateInfo;
    String token;

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
        updateInfo = findViewById(R.id.updateInfo);
        updateInfo.setOnClickListener(changeBasicInfo);
        //Get token from device
        token = SharedPreferencesUtils.getInstance(getApplicationContext()).getStringValue(LoginUtils.TOKEN);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        LoginAPI.pushToken(token, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                tv_Username.setText(jsonObject.getString("nickname"));
                tv_UserEmail.setText(jsonObject.getString("email"));
                if (jsonObject.getInt("sex") == 1)
                    sex.setText("男性");
                else
                    sex.setText("女性");

                age.setText(jsonObject.getInt("age") + "代");

                int area = jsonObject.getInt("area");
                prefecture.setText(ListArea.getAreaName(area - 1));

                /*if (LoginFragment.getmAccount() != null && LoginFragment.getmAccount().getImage() != "" && LoginFragment.getmAccount().getImage() != null) {
                    Log.i("avatar", LoginFragment.getmAccount().getImage());
                    PicassoUtil.getSharedInstance(getApplicationContext()).load(LoginFragment.getmAccount().getImage()).resize(0, 200).onlyScaleDown().transform(new CircleTransform()).into(img_avatar);
                }*/
                if (!TextUtils.isEmpty(jsonObject.getString("image")))
                    PicassoUtil.getSharedInstance(BasicInfoActivity.this).load(jsonObject.getString("image")).resize(0, 200).onlyScaleDown().transform(new CircleTransform()).into(img_avatar);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

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
            startActivity(intent);
        }
    };

}
