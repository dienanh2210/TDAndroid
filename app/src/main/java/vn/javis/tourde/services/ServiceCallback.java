package vn.javis.tourde.services;

import com.android.volley.VolleyError;

public interface ServiceCallback {

    void onSuccess(ServiceResult resultCode, Object response);
    void onError(VolleyError error);
}
