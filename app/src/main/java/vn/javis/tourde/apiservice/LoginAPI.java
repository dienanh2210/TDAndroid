package vn.javis.tourde.apiservice;

import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import vn.javis.tourde.volley.TourDeService;
import vn.javis.tourde.volley.callback.ServiceCallback;
import vn.javis.tourde.volley.common.ServiceResult;
import vn.javis.tourde.volley.endpoint.ApiEndpoint;

public class LoginAPI {
    public LoginAPI(){

    }
    public void login(String email, String password) {

    }
    public void register(String email, String password, boolean gender, int age, String address){
        HashMap<String, String> param = new HashMap<>();
        param.put("email",email);
        param.put("password",password);
        TourDeService.postWithAuth( ApiEndpoint.POST_CREATE_ACCOUNT, param, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) {
                Log.i( "CREATE ACCOUNT: ", ((JSONObject) response).toString() );
            }

            @Override
            public void onError(VolleyError error) {

            }
        } );
    }


}
