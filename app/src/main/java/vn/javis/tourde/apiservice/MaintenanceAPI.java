package vn.javis.tourde.apiservice;

import java.util.HashMap;

import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class MaintenanceAPI {
    public static void getMaintenanceData(ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        TourDeService.getWithAuth(ApiEndpoint.GET_MAINTENANCE_STATUS, params, callback);
    }
}
