package vn.javis.tourde.apiservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.model.Badge;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class BadgeAPI {
    public static void getBadgeData(String token,ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("token",token);
        TourDeService.getWithAuthAray(ApiEndpoint.GET_CHECKED_STAMP, params, callback);
    }
}
