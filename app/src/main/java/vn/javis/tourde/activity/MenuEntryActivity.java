package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;

import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class MenuEntryActivity extends BaseActivity {
    private static final String TAG = MenuEntryActivity.class.getSimpleName();
    RelativeLayout rlt_usermailrigister;
    TextView tv_back_menu, tv_loginEntry, tv_searchskip;
    MenuEntryActivity activity;
    public static CallbackManager mFaceBookCallbackManager;
    public static TwitterAuthClient twitterAuthClient;
    public static GoogleSignInClient mGoogleSignInClient;
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_entry);
        super.onCreate(savedInstanceState);


        //Init Api
        LoginUtils.initAPI(this);
        twitterAuthClient = LoginUtils.getTwitterAuthClient();
        //Todo init FaceBook
        mFaceBookCallbackManager = LoginUtils.getmFaceBookCallbackManager();
        LoginUtils.addFBCallback(this, mFaceBookCallbackManager);

        rlt_usermailrigister = findViewById(R.id.rlt_usermailrigister);
        rlt_usermailrigister.setOnClickListener(onClickNewUserMail);
        tv_back_menu = findViewById(R.id.tv_back_menu);
        tv_back_menu.setOnClickListener(onClickBackMenuPage);
        tv_loginEntry = findViewById(R.id.tv_loginEntry);
        tv_loginEntry.setOnClickListener(onClickLoginEntry);
        tv_searchskip = findViewById(R.id.tv_searchskip);
        tv_searchskip.setOnClickListener(onClickSearchSkip);
        if (SharedPreferencesUtils.getInstance(this).getStringValue("MenuEntry").equals(""))
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

        }
    };
    View.OnClickListener onClickLoginEntry = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuEntryActivity.this, LoginSNSActivity.class);
            startActivity(intent);

        }
    };
    View.OnClickListener onClickSearchSkip = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuEntryActivity.this, CourseListActivity.class);
            startActivity(intent);

        }
    };
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFaceBookCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);// twitter
        if (requestCode == LoginUtils.RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            LoginUtils.handleGoogleSignIn(this, task);
        }
        if (requestCode == LoginUtils.RC_LINE_SIGN_IN) {
            LoginUtils.handleLineLoginResult(this, data);
        }
    }

    @OnClick(R.id.rlt_facebook_login)
    void loginByFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(PUBLIC_PROFILE, EMAIL));
    }

    @OnClick(R.id.rlt_twitter_login)
    void loginByTwitter() {
        LoginUtils.addTwCallback(this);
    }

    @OnClick(R.id.rlt_google_login)
    public void loginByGoogle() {
        mGoogleSignInClient = LoginUtils.getmGoogleSignInClient();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, LoginUtils.RC_GOOGLE_SIGN_IN);
    }

    @OnClick(R.id.rlt_line_login)
    public void signInLine() {
        Intent loginIntent = LineLoginApi.getLoginIntent(this, LoginUtils.LINE_CHANEL_ID);
        startActivityForResult(loginIntent, LoginUtils.RC_LINE_SIGN_IN);

    }


}
