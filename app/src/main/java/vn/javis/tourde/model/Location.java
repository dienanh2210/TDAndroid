package vn.javis.tourde.model;

import java.io.Serializable;

public class Location implements Serializable{

    private int spotID;
   private double latitude;
   private double longtitude;
   private boolean arrived;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    private int orderNumber;
    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public Location(int spotID, double latitude, double longtitude,int orderNumber) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.spotID =spotID;
        this.orderNumber =orderNumber;
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
