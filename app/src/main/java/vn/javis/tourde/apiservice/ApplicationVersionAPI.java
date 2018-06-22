package vn.javis.tourde.apiservice;

import java.util.HashMap;

import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class ApplicationVersionAPI {
    public static void checkAppVersion(String version,String device,ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("version", version);
        params.put("device", device);
        TourDeService.getWithAuth(ApiEndpoint.CHECK_APPLICATION_VERSION, params, callback);
    }
}
