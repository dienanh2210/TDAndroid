package vn.javis.tourde.model;

import java.io.Serializable;

public class Location implements Serializable{

    private int spotID;
   private double latitude;
   private double longtitude;
   private boolean arrived;

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public Location(int spotID, double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.spotID =spotID;
    }

    public int getSpotID() {
        return spotID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }
}
