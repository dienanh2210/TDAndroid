package vn.javis.tourde.activity;

import android.app.Application;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import okhttp3.OkHttpClient;

public class MyApplication extends Application{

    private final String CONSUMER_KEY = "E9SgLRW6z3YDe1tkZjA8gWNdu";
    private final String CONSUMER_SECRET = "Esa5wkgYc4jjSKCGFb7QJlnLkOBlJkWI41Yixc318OP6p8ClJc";

    private static MyApplication sInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        FacebookSdk.sdkInitialize(getApplicationContext());

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);
        final OkHttpClient customClient = new OkHttpClient.Builder().build();

        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();

        final TwitterApiClient customApiClient;
        if (activeSession != null) {
            customApiClient = new TwitterApiClient(activeSession, customClient);
            TwitterCore.getInstance().addApiClient(activeSession, customApiClient);
        } else {
            customApiClient = new TwitterApiClient(customClient);
            TwitterCore.getInstance().addGuestApiClient(customApiClient);
        }
    }

    public static synchronized MyApplication getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String api) {
        req.setTag(api);
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }

}
