package vn.javis.tourde.apiservice;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.services.TourDeService;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.volley.VolleyCustomRequest;

public class LoginAPI {

    public static void loginEmail(final String email, final String password,ServiceCallback callback) {
        HashMap<String, String> param = new HashMap<>();
        param.put("email", email);
        param.put("password", password);
        TourDeService.postWithAuth(ApiEndpoint.POST_LOGIN_ACCOUNT, param,callback);
    }
    public static void register(final String email, final String password, boolean gender, int age, String address,ServiceCallback callback) {
        HashMap<String, String> param = new HashMap<>();
        param.put("email", email);
        param.put("password", password);
        TourDeService.postWithAuth(ApiEndpoint.POST_CREATE_ACCOUNT, param, callback);
    }

    public static void registerAccount(String email, String password, Response.Listener<JSONObject> successListener,Response.ErrorListener errorListener ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        String url = ApiEndpoint.BASE_URL + ApiEndpoint.POST_CREATE_ACCOUNT;
        VolleyCustomRequest jsObjRequest = new VolleyCustomRequest(Request.Method.POST, url, params, successListener,errorListener);
        TourDeApplication.getInstance().addToRequestQueue(jsObjRequest, ApiEndpoint.POST_CREATE_ACCOUNT);
    }
    public static void loginSNS(final String sns_id, final String sns_kind,ServiceCallback callback) {
        HashMap<String, String> param = new HashMap<>();
        param.put(ApiEndpoint.SNS_ID, sns_id);
        param.put(ApiEndpoint.SNS_KIND, sns_kind);
        TourDeService.postWithAuth(ApiEndpoint.POST_LOGIN_SNS, param,callback);
    }
    public static void pushToken(final String token,ServiceCallback callback) {
        HashMap<String, String> param = new HashMap<>();
        param.put("token", token);
        TourDeService.postWithAuth(ApiEndpoint.POST_GET_ACCOUNT, param,callback);
    }
}
