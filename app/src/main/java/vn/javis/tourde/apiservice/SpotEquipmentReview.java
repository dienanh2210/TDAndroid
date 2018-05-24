package vn.javis.tourde.apiservice;

import java.util.HashMap;

import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class SpotEquipmentReview {

    public static void getSpotEquipmentReview(int spotId,ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("spot_id",""+spotId);
        TourDeService.getWithAuth(ApiEndpoint.GET_SPOT_EQUIPMENT_REVIEW, params, callback);
    }
}
