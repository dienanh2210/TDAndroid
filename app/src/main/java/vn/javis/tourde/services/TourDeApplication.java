package vn.javis.tourde.services;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import vn.javis.tourde.R;

public class TourDeApplication extends Application {

    private final String CONSUMER_KEY = "o2Ixt07yGvkBoi1pfND1okRs3";
    private final String CONSUMER_SECRET = "dpS4plVCOqkHgxClbtzS1rZhk9IcnY0KTtZj6SUqojRdwzAIb6";

    private  static final String PROPERTY_ID="UA-76452397-13";

    private static TourDeApplication sInstance;
    private RequestQueue mRequestQueue;

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
//        FacebookSdk.sdkInitialize(getApplicationContext());
//
//        TwitterConfig config = new TwitterConfig.Builder(this)
//                .logger(new DefaultLogger( Log.DEBUG))
//                .twitterAuthConfig(new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET))
//                .debug(true)
//                .build();
//        Twitter.initialize(config);
//        final OkHttpClient customClient = new OkHttpClient.Builder().build();
//
//        final TwitterSession activeSession = TwitterCore.getInstance()
//                .getSessionManager().getActiveSession();
//
//        final TwitterApiClient customApiClient;
//        if (activeSession != null) {
//            customApiClient = new TwitterApiClient(activeSession, customClient);
//            TwitterCore.getInstance().addApiClient(activeSession, customApiClient);
//        } else {
//            customApiClient = new TwitterApiClient(customClient);
//            TwitterCore.getInstance().addGuestApiClient(customApiClient);
//        }
        sAnalytics = GoogleAnalytics.getInstance(this);
        getDefaultTracker();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized TourDeApplication getInstance() {
        return sInstance;
    }

    synchronized public Tracker getDefaultTracker() {
        if(sTracker ==null)
        {
            sTracker =sAnalytics.newTracker(R.xml.global_tracker);
        }
        return sTracker;
    }

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
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
    public void trackScreenView(String screenName) {

        // Set screen name.
        sTracker.setScreenName(screenName);
        // Send a screen view.
        sTracker.send(new HitBuilders.ScreenViewBuilder().build());
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }
    public void trackEvent(String category, String action, String label) {
        sTracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }

}
