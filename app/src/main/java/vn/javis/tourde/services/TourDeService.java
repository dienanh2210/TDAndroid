package vn.javis.tourde.services;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import vn.javis.tourde.apiservice.ApiEndpoint;
import vn.javis.tourde.volley.VolleyCustomRequest;
import vn.javis.tourde.volley.VolleyMultipartRequest;
import vn.javis.tourde.volley.VolleySingleton;

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

    public static void getWithAuth(String url, HashMap<String, String> params, final ServiceCallback serviceCallback) {
        StringRequest mRequest = new StringRequest(Request.Method.GET, generateURL(url, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String responseStr) {
                JSONObject response = null;
                try {
                    response = new JSONObject(responseStr);
                    serviceCallback.onSuccess(RESULT_SUCCESS, response);
                } catch (JSONException e) {
                    e.printStackTrace();
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
                return headers;
            }
        };

        TourDeApplication.getInstance().addToRequestQueue(mRequest, url);
    }

    public static void postWithAuth(String api, final HashMap<String, String> params, final ServiceCallback serviceCallback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiEndpoint.BASE_URL + api, new Response.Listener<String>() {
            @Override
            public void onResponse(String responseStr) {
                JSONObject response = null;
                try {
                    response = new JSONObject(responseStr);
                    serviceCallback.onSuccess(RESULT_SUCCESS, response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serviceCallback.onError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        TourDeApplication.getInstance().addToRequestQueue(stringRequest, api);
    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void uploadImageBitmap(final Activity activity, String api, final Bitmap bitmap, final HashMap<String, String> params, final ServiceCallback serviceCallback) {

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ApiEndpoint.BASE_URL + api,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(new String(response.data));
                            serviceCallback.onSuccess(RESULT_SUCCESS, jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceCallback.onError(error);
                    }
                }) {


            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Map<String, String> params = new HashMap<>();
                //params.put("tags", tags);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the request to volley
//        Volley.newRequestQueue(this).add(volleyMultipartRequest);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            HttpStack stack = null;
            try {
                stack = new HurlStack(null, new TLSSocketFactory());
            } catch (KeyManagementException e) {
                e.printStackTrace();
                Log.d("Your Wrapper Class", "Could not create new stack for TLS v1.2");
                stack = new HurlStack();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                Log.d("Your Wrapper Class", "Could not create new stack for TLS v1.2");
                stack = new HurlStack();
            }
            Volley.newRequestQueue(activity, stack).add(volleyMultipartRequest);
        } else {
            Volley.newRequestQueue(activity).add(volleyMultipartRequest);
        }
    }

}
