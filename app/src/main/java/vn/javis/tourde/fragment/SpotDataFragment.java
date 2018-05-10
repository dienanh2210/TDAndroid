package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import org.json.JSONException;

import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.model.SpotData;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class SpotDataFragment extends BaseFragment implements ServiceCallback {
    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int spotId = 1;
        SpotDataAPI.getCourseData(spotId, this);
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return null;
    }

    @Override
    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
        SpotData spotData = SpotData.getSpotData(response.toString());
    }

    @Override
    public void onError(VolleyError error) {

    }
}
