package vn.javis.tourde.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import vn.javis.tourde.apiservice.ApiEndpoint;

import static vn.javis.tourde.services.ServiceResult.*;


public abstract class TourDeService {

    protected ServiceCallback mServiceCallback;
    protected Context context;
    protected Integer responseCode = -1;
    protected String message = null;

    public TourDeService(Context context, ServiceCallback serviceCallback) {
        this.context = context;
        this.mServiceCallback = serviceCallback;
    }

    private static String generateURL(String url, HashMap<String, String> params) {
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            url += "?";
            for (String key : keys) {
                url += key + "=" + params.get(key) + "&";
            }
            url = url.substring(0, url.length() - 1);
        }
        return ApiEndpoint.BASE_URL + url;
    }

    public abstract void parseResponse(JSONObject response);

    public static void getWithAuth(String url, HashMap<String, String> params, final ServiceCallback serviceCallback) {
        StringRequest mRequest = new StringRequest(Request.Method.GET, generateURL(url, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String responseStr) {
                JSONObject response = null;
                try {
                    response = new JSONObject(responseStr);
//                    if (response.has("resultCode") && response.getInt("resultCode") == ServiceResult.RESULT_SUCCESS.getValue()) {
//                        serviceCallback.onSuccess(ServiceResult.RESULT_SUCCESS, response.get("data"));
//                    } else {
//                        serviceCallback.onSuccess(ServiceResult.fromInteger(response.getInt("resultCode")), response);
//                    }
                    serviceCallback.onSuccess(RESULT_SUCCESS, response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    serviceCallback.onSuccess(RESULT_ERROR, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serviceCallback.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
//                headers.put("authorization", "Bearer dsfsdgdsgs;");
                return headers;
            }
        };

        TourDeApplication.getInstance().addToRequestQueue(mRequest, url);
    }

    public static void postWithAuth(String api, HashMap<String, String> params, final ServiceCallback serviceCallback) {
        JSONObject jsonObject = null;
        if (params != null) {
            jsonObject = new JSONObject(params);
        }
        JsonObjectRequest mJsonObject = new JsonObjectRequest(
                Request.Method.POST, ApiEndpoint.BASE_URL + api, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                serviceCallback.onSuccess(RESULT_SUCCESS, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serviceCallback.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
//                headers.put("authorization", "Bearer adadad");
                return headers;
            }
        };

        int initialTimeoutMs = 60000;
        int maxNumRetries = 0;
        float backoffMultiplier = 1.0f;
        mJsonObject.setRetryPolicy(new DefaultRetryPolicy(initialTimeoutMs, maxNumRetries, backoffMultiplier));

        TourDeApplication.getInstance().addToRequestQueue(mJsonObject, api);
    }

    public static void postWithAuthObject(String api, HashMap<String, Object> params, final ServiceCallback serviceCallback) {
        JSONObject jsonObject = null;
        if (params != null) {
//            AccessToken accessToken = SMOApplication.getInstance().getAccessToken();
//            if(accessToken != null){
//                params.put("access_token", accessToken.getAccessToken());
//                params.put("aes_key", accessToken.getAesKey());
//                params.put("rsa_public_key", accessToken.getRsaPublicKey());
//            }
            jsonObject = new JSONObject(params);

        }

        JsonObjectRequest mJsonObject = new JsonObjectRequest(
                Request.Method.POST, ApiEndpoint.BASE_URL + api, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("resultCode") && response.getInt("resultCode") == RESULT_SUCCESS.getValue()) {
                        serviceCallback.onSuccess(RESULT_SUCCESS, response.get("data"));
                    } else {
                        serviceCallback.onSuccess(fromInteger(response.getInt("resultCode")), response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    serviceCallback.onSuccess(RESULT_ERROR, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serviceCallback.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
                headers.put("authorization", "Bearer adadad");
//                headers.put("authorization", "Bearer dsfsdgdsgs;");
                return headers;
            }
        };

        int initialTimeoutMs = 60000;
        int maxNumRetries = 0;
        float backoffMultiplier = 1.0f;
        mJsonObject.setRetryPolicy(new DefaultRetryPolicy(initialTimeoutMs, maxNumRetries, backoffMultiplier));

        TourDeApplication.getInstance().addToRequestQueue(mJsonObject, api);
    }

}
