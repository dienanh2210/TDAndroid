package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
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

import com.linecorp.linesdk.api.LineApiClient;
import com.linecorp.linesdk.api.LineApiClientBuilder;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.fragment.BaseFragment;
import vn.javis.tourde.service.ServiceCallback;
import vn.javis.tourde.service.ServiceResult;


public class LoginFragment extends BaseFragment implements LoginView {

    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String PUBLISH_ACTIONS = "publish_actions";
    private static final String PUBLIC_PROFILE = "public_profile";
    private CallbackManager mCallbackManager;
    TwitterAuthClient twitterAuthClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String LINE_CHANEL_ID = "1573462307";
    public static final int RC_LN_SIGN_IN = 006;
    private static LineApiClient lineApiClient;
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onInit() {
        // facebook
        mCallbackManager = CallbackManager.Factory.create();

        // Register a callback to respond to the user
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    String user_lastname = me.optString("last_name");
                                    String user_firstname = me.optString("first_name");
                                    String user_email = response.getJSONObject().optString("email");
                                    Logger.i("GraphRequest", user_lastname + "-" + user_firstname + "-" + user_email);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "last_name,first_name,email");
                request.setParameters(parameters);
                request.executeAsync();
                Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException e) {
                // Handle exception
                Toast.makeText(getContext(), "Login failure", Toast.LENGTH_SHORT).show();
            }
        });
        //googlePlus
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        //line


    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        onInit();
        View mView =inflater.inflate(R.layout.activity_login_view, container, false);

        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);// facebook
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);// twitter
        //googleplus
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            handleSignInResult(task);
        }
        //line
        if (requestCode == RC_LN_SIGN_IN)
        {
            handleLoginResult(data);
        }else
        {
            Log.e(TAG, "onActivityResult: Unknown Request response");
        }
    }

    @OnClick({R.id.rl_facebook, R.id.rl_twitter, R.id.rl_googleplus, R.id.rl_line})
    public void onClickView(View view) {
        Log.d(TAG, "onClickView: "+view);
        switch (view.getId()) {
            case R.id.rl_facebook:
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList(PUBLIC_PROFILE, EMAIL));
                break;
            case R.id.rl_twitter:
                twitterAuthClient.authorize(getActivity(), new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> twitterSessionResult) {
                        // Success
                        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                        TwitterAuthToken authToken = session.getAuthToken();
                     //   String token = authToken.token;
                      //  String secret = authToken.secret;
                        callPostAPISNS(""+session.getUserId(),"1");
                        Toast.makeText(getContext(), "Login success " +session.getUserId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case R.id.rl_googleplus:
                signInGoogle();
                break;
            case R.id.rl_line:
                signInLine();
                break;
        }
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signInLine() {
                Intent loginIntent = LineLoginApi.getLoginIntent(getActivity(), LINE_CHANEL_ID);
                startActivityForResult(loginIntent,RC_LN_SIGN_IN);
    }



    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
//            updateUI(account);/
            Log.i("GooglePlus", "success");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GooglePlus", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    private void handleLoginResult(Intent data){
        //Todo Handling the login result
        LineLoginResult loginResult = LineLoginApi.getLoginResultFromIntent(data);
        switch (loginResult.getResponseCode())
        {
            case SUCCESS: //Todo example Get Username
                Toast.makeText(getActivity(),"Login success: " + loginResult.getLineProfile().getDisplayName() ,Toast.LENGTH_SHORT).show();
                break;
            case CANCEL:
                Log.e("ERROR", "LINE Login Canceled by user!!");
                break;
            default:
                Log.e("ERROR", "Login FAILED!");
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig("E9SgLRW6z3YDe1tkZjA8gWNdu",
                "Esa5wkgYc4jjSKCGFb7QJlnLkOBlJkWI41Yixc318OP6p8ClJc");
        TwitterConfig.Builder builder=new TwitterConfig.Builder(getActivity());
        builder.twitterAuthConfig(authConfig);
        Twitter.initialize(builder.build());
        twitterAuthClient = new TwitterAuthClient();

    }
    void callPostAPISNS(String sns_id,String sns_kind){
        LoginAPI.loginSNS(sns_id, sns_kind, new vn.javis.tourde.services.ServiceCallback() {
            @Override
            public void onSuccess(vn.javis.tourde.services.ServiceResult resultCode, Object response) {
                Log.i("login sns",response.toString());
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }


}
