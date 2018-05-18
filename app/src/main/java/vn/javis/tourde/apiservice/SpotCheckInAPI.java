package vn.javis.tourde.apiservice;

import java.util.ArrayList;
import java.util.List;

import vn.javis.tourde.model.SpotCheckIn;

public class SpotCheckInAPI {

    private List<SpotCheckIn> lstSpot = new ArrayList<>();

    public SpotCheckInAPI() {
        setSpotForTest();
    }

    public List<SpotCheckIn> getListSpot(){
        return lstSpot;
    }

   public  void setSpotForTest(){
       SpotCheckIn b1 = new SpotCheckIn("スポット1","瀬戸田町観光案内所","finish");
       lstSpot.add(b1);
       SpotCheckIn b2 = new SpotCheckIn("スポット2","尾道港","icon_tailorshop");
       lstSpot.add(b2);
       SpotCheckIn b3 = new SpotCheckIn("スポット3","瀬戸田町観光案内所","finish");
       lstSpot.add(b3);
       SpotCheckIn b4 = new SpotCheckIn("スポット4","瀬戸田町観光案内所","icon_tailorshop");
       lstSpot.add(b4);
    }


}
