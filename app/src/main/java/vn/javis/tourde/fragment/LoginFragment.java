package vn.javis.tourde.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.MenuEntryActivity;
import vn.javis.tourde.model.Account;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.LoginView;
import vn.javis.tourde.activity.MenuPageActivity;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.utils.SharedPreferencesUtils;


public class LoginFragment extends BaseFragment implements LoginView {
    @BindView(R.id.edt_emaillogin)
    EditText edt_emaillogin;
    @BindView(R.id.edt_passwordlogin)
    EditText edt_passwordlogin;

    public static String getmUserToken() {
        if(mUserToken ==null)
            mUserToken="must be log in";
        return mUserToken;
    }

    private static String mUserToken;
    private static Account mAccount;

    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String PUBLISH_ACTIONS = "publish_actions";
    private static final String PUBLIC_PROFILE = "public_profile";
    private CallbackManager mCallbackManager;
    TwitterAuthClient twitterAuthClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    private static final String LINE_CHANEL_ID = "1574725654";
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
                                    Log.i("face", me.optString("id"));
                                    callPostAPISNS(me.optString("id"), "2");

                                    Intent intent = new Intent();
                                    intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                    getActivity().setResult(Activity.RESULT_OK, intent);
                                    getActivity().finish();
                                    getActivity().onBackPressed();

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
            public void onCancel() {
            }

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
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView txbackBasicinfoLogin = getView().findViewById(R.id.tv_back_basicinfo_login);
        TextView txclose = getView().findViewById(R.id.tv_close);
        txbackBasicinfoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MenuPageActivity.class);
                startActivity(intent);
            }
        });
        txclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MenuPageActivity.class);
                startActivity(intent);
            }
        });
        edt_emaillogin.setText(SharedPreferencesUtils.getInstance(getContext()).getStringValue("Email"));
        edt_passwordlogin.setText(SharedPreferencesUtils.getInstance(getContext()).getStringValue("Pass"));
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        onInit();
        View mView = inflater.inflate(R.layout.activity_login_view, container, false);

        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);// facebook
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

    @OnClick({R.id.rl_facebook, R.id.rl_twitter, R.id.rl_googleplus, R.id.rl_line})
    public void onClickView(View view) {

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
                        callPostAPISNS("" + session.getUserId(), "1");
                        Toast.makeText(getContext(), "Login success " + session.getUserId(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent();
                        intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();


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
        startActivityForResult(loginIntent, RC_LN_SIGN_IN);
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            callPostAPISNS(account.getId().toString(), "3");
            Log.i("GooglePlus", "success: " + account.getId());

            Intent intent = new Intent();
            intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();

        } catch (ApiException e) {
            Log.w("GooglePlus", "signInResult:failed code=" + e.getMessage());
        }
    }

    private void handleLoginResult(Intent data) {

        //Todo Handling the login result
        LineLoginResult loginResult = LineLoginApi.getLoginResultFromIntent(data);

        switch (loginResult.getResponseCode()) {
            case SUCCESS: //Todo example Get Username
                String accessToken = loginResult.getLineCredential().getAccessToken().getAccessToken();
                callPostAPISNS(loginResult.getLineProfile().getUserId().toString(), "4");
                Toast.makeText(getActivity(), "Login success: " + accessToken, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();

                break;
            case SERVER_ERROR:
                Log.e("ERROR", "SERVER ERROR!!");
                break;
            case NETWORK_ERROR:
                Log.e("ERROR", "NETWORK_ERROR!!");
                break;
            case INTERNAL_ERROR:
                Log.e("ERROR", "INTERNAL_ERROR!!");
                break;
            case AUTHENTICATION_AGENT_ERROR:
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
        TwitterConfig.Builder builder = new TwitterConfig.Builder(getActivity());
        builder.twitterAuthConfig(authConfig);
        Twitter.initialize(builder.build());
        twitterAuthClient = new TwitterAuthClient();
        LineApiClientBuilder apiClientBuilder = new LineApiClientBuilder(getActivity(), LINE_CHANEL_ID);
        lineApiClient = apiClientBuilder.build();


    }

    void callPostAPISNS(String sns_id, String sns_kind) {
        LoginAPI.loginSNS(sns_id, sns_kind, new vn.javis.tourde.services.ServiceCallback() {
            @Override
            public void onSuccess(vn.javis.tourde.services.ServiceResult resultCode, Object response) {
                Log.i("login sns", response.toString());
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @OnClick(R.id.bt_login)
    void loginToApp() {
        final boolean gender = false;
        String email = edt_emaillogin.getText().toString();
        String password = edt_passwordlogin.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            LoginAPI.loginEmail(email, password, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) {
                    JSONObject jsonObject = (JSONObject) response;
                    if (jsonObject.has("success"))
                    {
                        Log.d(edt_emaillogin.getText().toString(), edt_passwordlogin.getText().toString() + "yes" + response.toString());
//                        Intent intent = new Intent( getActivity(), MenuPageLoginActivity.class );
//                        startActivity( intent );
                        Intent intent = new Intent();
                        intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                        if (jsonObject.has("token"))
                        {
                            try {
                                mUserToken = jsonObject.getString("token");
                                getAccount();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Intent intent1 = new Intent(getActivity(), MenuPageActivity.class);
                        startActivity(intent1);
                    } else {
                        Log.d(edt_emaillogin.toString(), edt_passwordlogin.toString() + "error");
                    }

                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
    }

    public void getAccount() {

        LoginAPI.pushToken(mUserToken, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("account_id")) {
                    mAccount = Account.getData(jsonObject.toString());
                }
            }

            @Override
            public void onError(VolleyError error) {
            }
        });
    }

    public static Account getmAccount() {
        return mAccount;
    }

    public static void setmAccount(Account mAccount) {
        LoginFragment.mAccount = mAccount;
    }
}
