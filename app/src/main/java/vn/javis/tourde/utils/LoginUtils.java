package vn.javis.tourde.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.linecorp.linesdk.LineProfile;
import com.linecorp.linesdk.api.LineApiClient;
import com.linecorp.linesdk.api.LineApiClientBuilder;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.BaseActivity;
import vn.javis.tourde.activity.BasicInfoActivity;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.LoginSNSActivity;
import vn.javis.tourde.activity.MainActivity;
import vn.javis.tourde.apiservice.LoginAPI;
import vn.javis.tourde.fragment.BaseFragment;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.view.CircleTransform;

public class LoginUtils {
    BaseActivity baseActivity;
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
        LoginManager.getInstance().logOut();
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

    private static void processApiResponse(final Activity activity,final String sns_id, final String sns_kind) {
        ProcessDialog.showloading(activity,true);
        LoginAPI.loginSNS(sns_id, sns_kind, new vn.javis.tourde.services.ServiceCallback() {
            @Override
            public void onSuccess(vn.javis.tourde.services.ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                if (jsonObject.has("success")) {
                    //Todo save token to device
                    Log.i(TAG, "sns_id: " + sns_id + response.toString());
                    String token = jsonObject.getString("token");
                    SharedPreferencesUtils.getInstance(activity).setStringValue(TOKEN, token);
                    SharedPreferencesUtils.getInstance(activity).setBooleanValue(TOKEN_SNS, true);
                    //Add type of Login SNS

                    SharedPreferencesUtils.getInstance(activity).setStringValue(LOGIN_TYPE, sns_kind);
//                    Intent intent = new Intent(activity, CourseListActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
//                    activity.setResult(Activity.RESULT_OK, intent);
//                    activity.startActivity(intent);
                    Log.i("token", token);

                    processLogin(activity, token, sns_kind);

                }

            }

            @Override
            public void onError(VolleyError error) {
                ProcessDialog.showDialogOk(activity, "", "エラーメッセージ");
            }
        });
    }

    private static void processLogin(Activity activity, String token, String sns_kind) {
        switch (sns_kind) {
            case "1":
                registerWithTwitterLoginInfo(activity, token);
                break;
            case "2":
                regiterWithFacebookLoginInfo(activity, token);
                break;
            case "3":
                registerWithGoogleLoginInfo(activity, token);
                break;
            case "4":
                registerWithLineLoginInfo(activity, token);
                break;

        }

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

    private static void registerWithTwitterLoginInfo(final Activity activity, final String token) {


        // Call the MyTwitterApiClient for user/show session new
        final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        Long userId = session.getUserId();
        new MyTwitterApiClient(session).getCustomService().showProfile(userId)
                .enqueue(new Callback<User>() {
                    @Override
                    public void success(final Result<User> result) {
                        PicassoUtil.getSharedInstance(activity)
                                .load(result.data.profileImageUrl)
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        LoginAPI.editAccount(activity, token, result.data.email, null, result.data.name, bitmap, 0, -1, -1, -1, new ServiceCallback() {
                                            @Override
                                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                                if (((JSONObject) response).has("success")) {
                                                    Log.i(TAG, "onSuccess: login ok " + response.toString());
                                                    Intent intent = new Intent(activity, CourseListActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                                    activity.setResult(Activity.RESULT_OK, intent);
                                                    activity.startActivity(intent);
                                                } else
                                                    Log.e(TAG, "onSuccess: " + response.toString());
                                                ProcessDialog.showloading(activity,false);
                                            }

                                            @Override
                                            public void onError(VolleyError error) {
                                                Log.e(TAG, "onSuccess: " + error.toString());
                                            }
                                        });

                                    }

                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                        LoginAPI.editAccount(activity, token, result.data.email, null, result.data.name, null, 0, -1, -1, -1, new ServiceCallback() {
                                            @Override
                                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                                if (((JSONObject) response).has("success")) {
                                                    Log.i(TAG, "onSuccess: login ok " + response.toString());
                                                    Intent intent = new Intent(activity, CourseListActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                                    activity.setResult(Activity.RESULT_OK, intent);
                                                    activity.startActivity(intent);
                                                } else
                                                    Log.e(TAG, "onSuccess: " + response.toString());
                                                ProcessDialog.showloading(activity,false);
                                            }

                                            @Override
                                            public void onError(VolleyError error) {
                                                Log.e(TAG, "onSuccess: " + error.toString());
                                                ProcessDialog.showloading(activity,false);
                                            }
                                        });
                                    }


                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                });


                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.e("Failed", exception.toString());
                        ProcessDialog.showloading(activity,false);
                    }
                });
    }

    private static void regiterWithFacebookLoginInfo(final Activity activity, final String token) {

        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            Bundle params = new Bundle();
            params.putString("fields", "name,email,gender,picture.type(large)");
            new GraphRequest(accessToken, "me", params, HttpMethod.GET,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            if (response != null) {
                                try {
                                    final JSONObject data = response.getJSONObject();
                                    PicassoUtil.getSharedInstance(activity)
                                            .load(data.getString("picture"))
                                            .into(new Target() {
                                                @Override
                                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                    try {
                                                        LoginAPI.editAccount(activity, token, data.getString("email"), null, data.getString("name"), bitmap, 0, 1, 1, 1, new ServiceCallback() {
                                                            @Override
                                                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                                                if (((JSONObject) response).has("success")) {
                                                                    Log.i(TAG, "onSuccess: login ok " + response.toString());
                                                                    Intent intent = new Intent(activity, CourseListActivity.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                    intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                                                    activity.setResult(Activity.RESULT_OK, intent);
                                                                    activity.startActivity(intent);
                                                                } else
                                                                    Log.e(TAG, "onSuccess: " + response.toString());
                                                                ProcessDialog.showloading(activity,false);
                                                            }

                                                            @Override
                                                            public void onError(VolleyError error) {
                                                                Log.e(TAG, "onSuccess: " + error.toString());
                                                                ProcessDialog.showloading(activity,false);
                                                            }
                                                        });
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }

                                                @Override
                                                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                                    try {
                                                        LoginAPI.editAccount(activity, token, data.getString("email"), null, data.getString("name"), null, 0, 1, 1, 1, new ServiceCallback() {
                                                            @Override
                                                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                                                if (((JSONObject) response).has("success")) {
                                                                    Log.i(TAG, "onSuccess: login ok " + response.toString());
                                                                    Intent intent = new Intent(activity, CourseListActivity.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                    intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                                                    activity.setResult(Activity.RESULT_OK, intent);
                                                                    activity.startActivity(intent);
                                                                } else
                                                                    Log.e(TAG, "onSuccess: " + response.toString());
                                                                ProcessDialog.showloading(activity,false);
                                                            }

                                                            @Override
                                                            public void onError(VolleyError error) {
                                                                Log.e(TAG, "onSuccess: " + error.toString());
                                                                ProcessDialog.showloading(activity,false);
                                                            }
                                                        });
                                                    } catch (JSONException e1) {
                                                        e1.printStackTrace();
                                                        ProcessDialog.showloading(activity,false);
                                                    }
                                                }


                                                @Override
                                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                                }
                                            });


                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                            }
                        }
                    }).executeAsync();
        }
    }

    private static void registerWithGoogleLoginInfo(final Activity activity, final String token) {

        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(activity);

        if (acct != null) {
            PicassoUtil.getSharedInstance(activity)
                    .load(acct.getPhotoUrl())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                            LoginAPI.editAccount(activity, token, acct.getEmail(), null, acct.getDisplayName(), bitmap, 0, 1, 1, 1, new ServiceCallback() {
                                @Override
                                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                    if (((JSONObject) response).has("success")) {
                                        Log.i(TAG, "onSuccess: login ok " + response.toString());
                                        Intent intent = new Intent(activity, CourseListActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                        activity.setResult(Activity.RESULT_OK, intent);
                                        activity.startActivity(intent);
                                    } else
                                        Log.e(TAG, "onSuccess: " + response.toString());
                                    ProcessDialog.showloading(activity,false);
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    Log.e(TAG, "onSuccess: " + error.toString());
                                    ProcessDialog.showloading(activity,false);
                                }
                            });

                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            LoginAPI.editAccount(activity, token, acct.getEmail(), null, acct.getDisplayName(), null, 0, 1, 1, 1, new ServiceCallback() {
                                @Override
                                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                    if (((JSONObject) response).has("success")) {
                                        Log.i(TAG, "onSuccess: login ok " + response.toString());
                                        Intent intent = new Intent(activity, CourseListActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                        activity.setResult(Activity.RESULT_OK, intent);
                                        activity.startActivity(intent);
                                    } else
                                        Log.e(TAG, "onSuccess: " + response.toString());
                                    ProcessDialog.showloading(activity,false);
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    Log.e(TAG, "onSuccess: " + error.toString());
                                    ProcessDialog.showloading(activity,false);
                                }
                            });

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

        }

    }

    private static void registerWithLineLoginInfo(final Activity activity, final String token) {

        final LineProfile lineProfile = lineApiClient.getProfile().getResponseData();

        PicassoUtil.getSharedInstance(activity)
                .load(lineProfile.getPictureUrl())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                        LoginAPI.editAccount(activity, token, lineProfile.getUserId(), null, lineProfile.getDisplayName(), bitmap, 0, 1, 1, 1, new ServiceCallback() {
                            @Override
                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                if (((JSONObject) response).has("success")) {
                                    Log.i(TAG, "onSuccess: login ok " + response.toString());
                                    Intent intent = new Intent(activity, CourseListActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                    activity.setResult(Activity.RESULT_OK, intent);
                                    activity.startActivity(intent);
                                } else
                                    Log.e(TAG, "onSuccess: " + response.toString());
                                ProcessDialog.showloading(activity,false);
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Log.e(TAG, "onSuccess: " + error.toString());
                                ProcessDialog.showloading(activity,false);
                            }
                        });

                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        LoginAPI.editAccount(activity, token, lineProfile.getUserId(), null, lineProfile.getDisplayName(), null, 0, 1, 1, 1, new ServiceCallback() {
                            @Override
                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                if (((JSONObject) response).has("success")) {
                                    Log.i(TAG, "onSuccess: login ok " + response.toString());
                                    Intent intent = new Intent(activity, CourseListActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(Constant.KEY_LOGIN_SUCCESS, true);
                                    activity.setResult(Activity.RESULT_OK, intent);
                                    activity.startActivity(intent);
                                } else
                                    Log.e(TAG, "onSuccess: " + response.toString());
                                ProcessDialog.showloading(activity,false);
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Log.e(TAG, "onSuccess: " + error.toString());
                                ProcessDialog.showloading(activity,false);
                            }
                        });

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

    }



}
