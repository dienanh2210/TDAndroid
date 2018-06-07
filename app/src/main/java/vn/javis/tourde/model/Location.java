package vn.javis.tourde.model;

import java.io.Serializable;

public class Location implements Serializable{

   private double latitude;
   private double longtitude;

    public Location(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }
}
