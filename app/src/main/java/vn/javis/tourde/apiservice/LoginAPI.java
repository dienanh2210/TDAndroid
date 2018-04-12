package vn.javis.tourde.apiservice;

import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import vn.javis.tourde.services.TourDeService;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class LoginAPI {

    public static void login(final String email, final String password) {
        HashMap<String, String> param = new HashMap<>();
        param.put("email",email);
        param.put("password",password);
        TourDeService.postWithAuth( ApiEndpoint.POST_LOGIN_ACCOUNT, param, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) {
                Log.i( "Login ACCOUNT: ", ((JSONObject) response).toString() +email +"-"+password );
            }

            @Override
            public void onError(VolleyError error) {

            }
        } );
    }
    public static void register(final String email, final String password, boolean gender, int age, String address){
        HashMap<String, String> param = new HashMap<>();
        param.put("email",email);
        param.put("password",password);
        TourDeService.postWithAuth( ApiEndpoint.POST_CREATE_ACCOUNT, param, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) {
                Log.i( "CREATE ACCOUNT: ", ((JSONObject) response).toString() +email +"-"+password );
            }

            @Override
            public void onError(VolleyError error) {

            }
        } );
    }


}
