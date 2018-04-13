package vn.javis.tourde.activity;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import vn.javis.tourde.service.TourDeService;
import vn.javis.tourde.service.ServiceCallback;
import vn.javis.tourde.service.ServiceResult;

public class LoginPresenterImpl implements LoginPresenter{

    private final LoginView mLoginView;

    public LoginPresenterImpl(@NonNull LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void start() {
        mLoginView.onInit();
        HashMap<String, String> params = new HashMap<>();
        params.put("refecture", "13");

        TourDeService.getWithAuth(ApiEndpoint.GET_COURSE_LIST, params, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) {
                if (resultCode == ServiceResult.RESULT_SUCCESS) {
                    try {
                        Logger.i("GET_COURSE_LIST", ((JSONObject) response).getJSONObject("list").getJSONObject("35").getJSONObject("data").toString());
                        Logger.i("getIntroduction", Data.getData(((JSONObject) response).getJSONObject("list").getJSONObject("35").getJSONObject("data").toString()).getIntroduction());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                Logger.d("Error", ApiEndpoint.GET_COURSE_LIST);

            }
        });
    }

    @Override
    public void onSuccess() {
        Logger.i("Login", "Login success");
    }

    @Override
    public void onCancel() {
        Logger.i("Login", "Cancel");
    }

    @Override
    public void onError(Exception e) {
        Logger.i("Login", "Login error:" + e.getMessage());
    }
}
