package vn.javis.tourde.apiservice;

import java.util.HashMap;

import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class SpotDataAPI {
    public static void getCourseData(int spot_id,ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("spot_id",String.valueOf(spot_id));
        TourDeService.getWithAuth(ApiEndpoint.GET_SPOT_DATA, params, callback);
    }
}
