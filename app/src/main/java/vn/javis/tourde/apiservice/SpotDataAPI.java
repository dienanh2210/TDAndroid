package vn.javis.tourde.apiservice;

import java.util.ArrayList;
import java.util.HashMap;

import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class SpotDataAPI {
    public static void getSpotData(int spot_id,ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("spot_id",String.valueOf(spot_id));
        TourDeService.getWithAuth(ApiEndpoint.GET_SPOT_DATA, params, callback);
    }
    public static void getSpotEquipmentReview(int spotId,ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("spot_id",""+spotId);
        TourDeService.getWithAuth(ApiEndpoint.GET_SPOT_EQUIPMENT_REVIEW, params, callback);
    }
    public static void getSpotEquipmentReview(String token, int spotId,ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("token",""+token);
        params.put("spot_id",""+spotId);
        TourDeService.getWithAuth(ApiEndpoint.GET_CHECK_SPOT_EQUIPMENT_REVIEW, params, callback);
    }
    public static void postReviewSpotEquipment (HashMap<String,String> params, ServiceCallback callback){
        TourDeService.postWithAuth(ApiEndpoint.POST_REVIEW_SPOT_EQUIPMENT, params, callback);
    }
    public static void postSpotImage(String token, int spotId, ArrayList<String> user_image_id, ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("token",""+token);
        params.put("spot_id",""+spotId);
      //  params.put("user_image_id",""+user_image_id);
        for (int i = 0; i < user_image_id.size(); i++) {
            params.put("user_image_id[" + i + "]", user_image_id.get(i));
        }
        TourDeService.postWithAuth(ApiEndpoint.POST_SPOT_IMAGE, params, callback);
    }
}
