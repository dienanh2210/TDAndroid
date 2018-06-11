package vn.javis.tourde.apiservice;

import java.util.HashMap;

import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class CheckInStampAPI {

    public static void postCheckInStamp(String token, int course_id,int spot_id, ServiceCallback callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("course_id", String.valueOf(course_id));
        params.put("spot_id", String.valueOf(spot_id));
        TourDeService.postWithAuth(ApiEndpoint.POST_CHECK_IN_STAMP, params, callback);
    }
}
