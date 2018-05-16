package vn.javis.tourde.apiservice;

import java.util.HashMap;

import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class LogoutAccount {

   public  static void logOut(String token,ServiceCallback callback){
      HashMap<String, String> param = new HashMap<>();
      param.put("token", token);
      TourDeService.postWithAuth(ApiEndpoint.POST_LOGOUT_ACCOUNT, param,callback);
   }


}
