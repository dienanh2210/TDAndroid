package vn.javis.tourde.volley.callback;

import com.android.volley.VolleyError;

import vn.javis.tourde.volley.common.ServiceResult;

public interface ServiceCallback {

    void onSuccess(ServiceResult resultCode, Object response);
    void onError(VolleyError error);
}
