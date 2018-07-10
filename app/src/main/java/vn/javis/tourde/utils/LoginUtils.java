package vn.javis.tourde.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.AccessToken;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.linecorp.linesdk.api.LineApiClient;
import com.linecorp.linesdk.api.LineApiClientBuilder;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONException;
import org.json.JSONObject;

import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.MainActivity;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.fragment.LoginFragment;

public class LoginUtils {

    private static String TAG = LoginUtils.class.getSimpleName();

    private static CallbackManager mFaceBookCallbackManager;
    private static TwitterAuthClient twitterAuthClient;
    private static GoogleSignInClient mGoogleSignInClient;
    private static LineApiClient lineApiClient;
    // Sns Kind
    public static final String TWITTER_SNS_KIND = "1";
    public static final String FACEBOOK_SNS_KIND = "2";
    public static final String GOOGLE_SNS_KIND = "3";
    public static final String LINE_SNS_KIND = "4";
    //Api key
    public static final String LINE_CHANEL_ID = "1590519133";
    private static LoginUtils sInstance;
    public static final String TWITTER_API_KEY = "ENT3zPPX1vuUX3UBK0kOrKRYJ";
    public static final String TWITTER_API_SECRET = "XzF9MXhUhs9Ra8z9hBB6IoscT0tNkg4G3DwrJzhyQeJQNNKjMm";
    //Request Code
    public static final int RC_GOOGLE_SIGN_IN = 9001;
    public static final int RC_LINE_SIGN_IN = 006;

    //
    public static final String TOKEN = "token";
    public static final String TOKEN_SNS = "token_sns";
    public static final String LOGIN_TYPE = "login_type";

    public static LoginUtils newInstance() {
        if (sInstance == null) sInstance = new LoginUtils();
        return sInstance;
    }

    public static CallbackManager getmFaceBookCallbackManager() {
        return mFaceBookCallbackManager;
    }


    public static TwitterAuthClient getTwitterAuthClient() {
        return twitterAuthClient;
    }


    public static GoogleSignInClient getmGoogleSignInClient() {
        return mGoogleSignInClient;
    }


    public static LineApiClient getLineApiClient() {
        return lineApiClient;
    }

    public static void setLineApiClient(LineApiClient lineApiClient) {
        LoginUtils.lineApiClient = lineApiClient;
    }

    public static void initAPI(Activity activity) {
        //Init Api
        //Todo init FaceBook
        mFaceBookCallbackManager = CallbackManager.Factory.create();
        //Todo init Twitter
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_API_KEY,
                TWITTER_API_SECRET);
        TwitterConfig.Builder builder = new TwitterConfig.Builder(activity);
        builder.twitterAuthConfig(authConfig);
        Twitter.initialize(builder.build());
        twitterAuthClient = new TwitterAuthClient();

        //Todo init Line
        LineApiClientBuilder apiClientBuilder = new LineApiClientBuilder(activity, LINE_CHANEL_ID);
        lineApiClient = apiClientBuilder.build();

        //Todo init Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public static void addFBCallback(final Activity activity, CallbackManager callbackManager) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (!isLoggedIn) {
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.i(TAG, "onSuccess: ");
                    processApiResponse(activity, loginResult.getAccessToken().getUserId(), FACEBOOK_SNS_KIND);


                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    Log.i(TAG, "onError: " + error.toString());
                }

            });
        } else {

            processApiResponse(activity, accessToken.getUserId(), FACEBOOK_SNS_KIND);
        }


    }

    public static void addTwCallback(final Activity activity) {
        twitterAuthClient.authorize(activity, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                String twitterUserId = String.valueOf(session.getUserId());
                processApiResponse(activity, twitterUserId, TWITTER_SNS_KIND);

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    public static void handleGoogleSignIn(Activity activity, Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            processApiResponse(activity, account.getId(), GOOGLE_SNS_KIND);

        } catch (ApiException e) {
            Log.w("GooglePlus", "signInResult:failed code=" + e.getMessage());

        }
    }

    private static void processApiResponse(final Activity activity, String sns_id, final String sns_kind) {
        LoginAPI.loginSNS(sns_id, sns_kind, new vn.javis.tourde.services.ServiceCallback() {
            @Override
            public void onSuccess(vn.javis.tourde.services.ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("success")) {
                    //Todo save token to device
                    Log.i(TAG, "onSuccess: " + sns_kind + response.toString());
                    String token = jsonObject.getString("token");
                    SharedPreferencesUtils.getInstance(activity).setStringValue(TOKEN, token);
                    SharedPreferencesUtils.getInstance(activity).setBooleanValue(TOKEN_SNS, true);
                    //Add type of Login SNS
                    SharedPreferencesUtils.getInstance(activity).setStringValue(LOGIN_TYPE, sns_kind);
                    Intent intent = new Intent(activity, CourseListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                    activity.setResult(Activity.RESULT_OK, intent);
                    activity.startActivity(intent);
                }

            }

            @Override
            public void onError(VolleyError error) {
                ProcessDialog.showDialogOk(activity, "", "エラーメッセージ");
            }
        });
    }

    public static void handleLineLoginResult(Activity activity, Intent data) {


        //Todo Handling the login result
        LineLoginResult loginResult = LineLoginApi.getLoginResultFromIntent(data);

        switch (loginResult.getResponseCode()) {
            case SUCCESS: //Todo example Get Username
                String userId = loginResult.getLineProfile().getUserId();
                if (!TextUtils.isEmpty(userId)) processApiResponse(activity, userId, LINE_SNS_KIND);
                break;
            case SERVER_ERROR:
                Log.e("ERROR", "SERVER ERROR!!");
                Toast.makeText(activity, "エラーメッセージ", Toast.LENGTH_LONG).show();
                break;
            case NETWORK_ERROR:
                Log.e("ERROR", "NETWORK_ERROR!!");
                Toast.makeText(activity, "エラーメッセージ", Toast.LENGTH_LONG).show();
                break;
            case INTERNAL_ERROR:
                Log.e("ERROR", "INTERNAL_ERROR!!");
                Toast.makeText(activity, "エラーメッセージ", Toast.LENGTH_LONG).show();
                break;
            case AUTHENTICATION_AGENT_ERROR:
                break;
            case CANCEL:
                Log.e("ERROR", "LINE Login Canceled by user!!");
                Toast.makeText(activity, "エラーメッセージ", Toast.LENGTH_LONG).show();
                break;
            default:
                Log.e("ERROR", "Login FAILED!");
                Toast.makeText(activity, "エラーメッセージ", Toast.LENGTH_LONG).show();
        }

    }

    public static void checkGoogleLastLogin(Activity activity) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(activity);
        if (acct != null) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                        }
                    });
        }
    }

}
