package vn.javis.tourde.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.linecorp.linesdk.LineProfile;
import com.linecorp.linesdk.api.LineApiClient;
import com.linecorp.linesdk.api.LineApiClientBuilder;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.ListArea;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.MyTwitterApiClient;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.view.CircleTransform;

import static vn.javis.tourde.utils.LoginUtils.LINE_CHANEL_ID;
import static vn.javis.tourde.utils.LoginUtils.LOGIN_TYPE;
import static vn.javis.tourde.utils.LoginUtils.TWITTER_API_KEY;
import static vn.javis.tourde.utils.LoginUtils.TWITTER_API_SECRET;

public class BasicInfoActivity extends BaseActivity {

    TextView tv_back_basicInfo, tv_close_basicInfo, tv_UserEmail, tv_Username, sex, age, prefecture;
    ImageView img_avatar;
    Button updateInfo;
    String token;
    String loginType;

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
        loginType = SharedPreferencesUtils.getInstance(this).getStringValue(LOGIN_TYPE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        getInfo(loginType);

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

    private void getInfo(String loginType) {
        switch (loginType) {
            case ""://User login by email
                getBasicLoginInfo();
                break;
            case "1":// User login by Twitter
                getTwitterLoginInfo();
                disableUpdateInfoButton();
                break;
            case "2":  //User Login by Facebook
                getFacebookLoginInfo();
                disableUpdateInfoButton();
                break;
            case "3":// User login by Google plus
                getGoogleLoginInfo();
                disableUpdateInfoButton();
                break;
            case "4":// User login by Line
                showProgressDialog();
                new getLineLoginInfo(this).execute();
                disableUpdateInfoButton();
                break;
        }
    }


    private void getTwitterLoginInfo() {

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_API_KEY,
                TWITTER_API_SECRET);
        TwitterConfig.Builder builder = new TwitterConfig.Builder(this);
        builder.twitterAuthConfig(authConfig);
        Twitter.initialize(builder.build());
        // Call the MyTwitterApiClient for user/show session new
        final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        Long userId = session.getUserId();
        showProgressDialog();
        new MyTwitterApiClient(session).getCustomService().showProfile(userId)
                .enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        tv_Username.setText(session.getUserName());
                        tv_UserEmail.setText(result.data.email);
                        if (!TextUtils.isEmpty(result.data.profileImageUrl))
                            PicassoUtil.getSharedInstance(BasicInfoActivity.this).load(result.data.profileImageUrl).resize(0, 200).onlyScaleDown().transform(new CircleTransform()).into(img_avatar);
                        hideProgressDialog();
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.e("Failed", exception.toString());
                        hideProgressDialog();
                    }
                });
    }

    private void getFacebookLoginInfo() {

        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            Bundle params = new Bundle();
            params.putString("fields", "name,email,gender,picture.type(large)");
            showProgressDialog();
            new GraphRequest(accessToken, "me", params, HttpMethod.GET,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            if (response != null) {
                                try {
                                    JSONObject data = response.getJSONObject();
                                    tv_Username.setText(data.getString("name"));
                                    tv_UserEmail.setText(data.getString("email"));
                                    if (data.has("picture")) {
                                        String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                        if (!TextUtils.isEmpty(profilePicUrl))
                                            PicassoUtil.getSharedInstance(BasicInfoActivity.this).load(profilePicUrl).resize(200, 200).onlyScaleDown().transform(new CircleTransform()).into(img_avatar);
                                    }
                                    hideProgressDialog();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    hideProgressDialog();
                                }
                            }
                        }
                    }).executeAsync();
        }
    }

    private void getGoogleLoginInfo() {
        showProgressDialog();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            tv_Username.setText(acct.getDisplayName());
            tv_UserEmail.setText(acct.getEmail());
            if (acct.getPhotoUrl() != null)
                PicassoUtil.getSharedInstance(BasicInfoActivity.this).load(acct.getPhotoUrl()).resize(200, 200).onlyScaleDown().transform(new CircleTransform()).into(img_avatar);
        }
        hideProgressDialog();
    }

    private void getBasicLoginInfo() {
        showProgressDialog();
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
                if (!TextUtils.isEmpty(jsonObject.getString("image")))
                    PicassoUtil.getSharedInstance(BasicInfoActivity.this).load(jsonObject.getString("image")).resize(200, 200).onlyScaleDown().transform(new CircleTransform()).into(img_avatar);
                hideProgressDialog();
            }

            @Override
            public void onError(VolleyError error) {
                hideProgressDialog();
            }
        });
    }

    private void disableUpdateInfoButton() {
        updateInfo.setBackgroundColor(getResources().getColor(R.color.lightGray));
        updateInfo.setClickable(false);
    }

    private static class getLineLoginInfo extends AsyncTask<String, Integer, LineProfile> {
        private WeakReference<BasicInfoActivity> activityReference;

        // only retain a weak reference to the activity
        getLineLoginInfo(BasicInfoActivity context) {
            activityReference = new WeakReference<>(context);
        }


        @Override
        protected LineProfile doInBackground(String... strings) {
            BasicInfoActivity basicInfoActivity = activityReference.get();
            LineApiClientBuilder apiClientBuilder = new LineApiClientBuilder(basicInfoActivity, LINE_CHANEL_ID);
            LineApiClient lineApiClient = apiClientBuilder.build();
            return lineApiClient.getProfile().getResponseData();
        }

        @Override
        protected void onPostExecute(LineProfile lineProfile) {
            BasicInfoActivity basicInfoActivity = activityReference.get();
            TextView txtUserName = basicInfoActivity.findViewById(R.id.tv_Username);
            TextView txtUserInfo = basicInfoActivity.findViewById(R.id.tv_UserEmail);
            ImageView imageUserPicture = basicInfoActivity.findViewById(R.id.avatar);
            txtUserName.setText(lineProfile.getDisplayName());
            txtUserInfo.setText(lineProfile.getStatusMessage());
            if (lineProfile.getPictureUrl() != null)
                PicassoUtil.getSharedInstance(basicInfoActivity).load(lineProfile.getPictureUrl()).resize(200, 200).onlyScaleDown().transform(new CircleTransform()).into(imageUserPicture);
            basicInfoActivity.hideProgressDialog();
            super.onPostExecute(lineProfile);

        }
    }


}
