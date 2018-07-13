package vn.javis.tourde.services;

import com.android.volley.VolleyError;

import org.json.JSONException;

public interface ServiceCallback {

    void onSuccess(ServiceResult resultCode, Object response) throws JSONException;
    void onError(VolleyError error);
}
