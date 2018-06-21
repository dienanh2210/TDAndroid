package vn.javis.tourde.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.linecorp.linesdk.api.LineApiClient;
import com.linecorp.linesdk.api.LineApiClientBuilder;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.fragment.BaseFragment;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class MenuEntryActivity extends BaseActivity {
    private static final String TAG = MenuEntryActivity.class.getSimpleName();
    RelativeLayout rlt_usermailrigister;
    TextView tv_back_menu, tv_loginEntry, tv_searchskip;
    MenuEntryActivity activity;
    public static CallbackManager mFaceBookCallbackManager;
    public static TwitterAuthClient twitterAuthClient;
    public static GoogleSignInClient mGoogleSignInClient;
    public static LineApiClient lineApiClient;
    public static final String LINE_CHANEL_ID = "1574725654";
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String PUBLISH_ACTIONS = "publish_actions";
    private static final String PUBLIC_PROFILE = "public_profile";
    private static final int RC_SIGN_IN = 9001;
    public static final int RC_LN_SIGN_IN = 006;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_entry);
        super.onCreate(savedInstanceState);


        //Init Api
        //Todo init FaceBook
        mFaceBookCallbackManager = CallbackManager.Factory.create();
        addFBCallback(mFaceBookCallbackManager);
        //Todo init Twitter
        TwitterAuthConfig authConfig = new TwitterAuthConfig("E9SgLRW6z3YDe1tkZjA8gWNdu",
                "Esa5wkgYc4jjSKCGFb7QJlnLkOBlJkWI41Yixc318OP6p8ClJc");
        TwitterConfig.Builder builder = new TwitterConfig.Builder(this);
        builder.twitterAuthConfig(authConfig);
        Twitter.initialize(builder.build());
        twitterAuthClient = new TwitterAuthClient();

        //Todo init Line
        LineApiClientBuilder apiClientBuilder = new LineApiClientBuilder(this, LINE_CHANEL_ID);
        lineApiClient = apiClientBuilder.build();

        //Todo init Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


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
            //finish();
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
     //   super.onBackPressed();
     //   this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFaceBookCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);// twitter
        //googleplus
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        //line
        if (requestCode == RC_LN_SIGN_IN) {
            handleLoginResult(data);
        }
    }

    public static void addFBCallback(CallbackManager callbackManager) {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "onSuccess: ");

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {


                                }
                            }
                        });

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.i(TAG, "onError: " + error.toString());
            }

        });

    }

    public static void addTwCallback(final Activity activity) {
        twitterAuthClient.authorize(activity, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                Toast.makeText(activity, "Login success " + session.getUserId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    @OnClick(R.id.rlt_facebook_login)
    void loginByFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(PUBLIC_PROFILE, EMAIL));
    }

    @OnClick(R.id.rlt_twitter_login)
    void loginByTwitter() {
        addTwCallback(this);
    }

    @OnClick(R.id.rlt_google_login)
    public void loginByGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.rlt_line_login)
    public void signInLine() {
        Intent loginIntent = LineLoginApi.getLoginIntent(this, LINE_CHANEL_ID);
        startActivityForResult(loginIntent, RC_LN_SIGN_IN);

    }

    void callPostAPISNS(String sns_id, String sns_kind) {
        LoginAPI.loginSNS(sns_id, sns_kind, new vn.javis.tourde.services.ServiceCallback() {
            @Override
            public void onSuccess(vn.javis.tourde.services.ServiceResult resultCode, Object response) {
                Log.i("login sns", response.toString());
            }

            @Override
            public void onError(VolleyError error) {
                //Toast.makeText( getContext(),"エラーメッセージ",Toast.LENGTH_LONG ).show();
                ProcessDialog.showDialogOk(getApplicationContext(), "", "エラーメッセージ");
            }
        });
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            callPostAPISNS(account.getId().toString(), "3");
            Log.i("GooglePlus", "success: " + account.getId());

            Intent intent = new Intent();
            intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
            this.setResult(Activity.RESULT_OK, intent);
            finish();

        } catch (ApiException e) {
            Log.w("GooglePlus", "signInResult:failed code=" + e.getMessage());
            Toast.makeText(this, "エラーメッセージ", Toast.LENGTH_LONG).show();

        }
    }

    private void handleLoginResult(Intent data) {


        //Todo Handling the login result
        LineLoginResult loginResult = LineLoginApi.getLoginResultFromIntent(data);

        switch (loginResult.getResponseCode()) {
            case SUCCESS: //Todo example Get Username
                String accessToken = loginResult.getLineCredential().getAccessToken().getAccessToken();
                callPostAPISNS(loginResult.getLineProfile().getUserId().toString(), "4");
                Toast.makeText(this, "Login success: " + accessToken, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                this.setResult(Activity.RESULT_OK, intent);
                finish();

                break;
            case SERVER_ERROR:
                Log.e("ERROR", "SERVER ERROR!!");
                Toast.makeText(this, "エラーメッセージ", Toast.LENGTH_LONG).show();
                break;
            case NETWORK_ERROR:
                Log.e("ERROR", "NETWORK_ERROR!!");
                Toast.makeText(this, "エラーメッセージ", Toast.LENGTH_LONG).show();
                break;
            case INTERNAL_ERROR:
                Log.e("ERROR", "INTERNAL_ERROR!!");
                Toast.makeText(this, "エラーメッセージ", Toast.LENGTH_LONG).show();
                break;
            case AUTHENTICATION_AGENT_ERROR:
                break;
            case CANCEL:
                Log.e("ERROR", "LINE Login Canceled by user!!");
                Toast.makeText(this, "エラーメッセージ", Toast.LENGTH_LONG).show();
                break;
            default:
                Log.e("ERROR", "Login FAILED!");
                Toast.makeText(this, "エラーメッセージ", Toast.LENGTH_LONG).show();
        }

    }

}
