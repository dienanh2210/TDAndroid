package vn.javis.tourde.service;

import com.android.volley.VolleyError;

import vn.javis.tourde.service.ServiceResult;

public interface ServiceCallback {
    void onSuccess(ServiceResult resultCode, Object response);

    void onError(VolleyError error);
}
