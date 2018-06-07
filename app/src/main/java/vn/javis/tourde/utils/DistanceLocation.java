package vn.javis.tourde.utils;

import android.location.Location;

public class DistanceLocation {
    public static double getDistance(double startLatitue, double startLongtitue, double endLatitue, double endLongtitue) {
        Location startPoint = new Location("startLocation");
        startPoint.setLatitude(startLatitue);
        startPoint.setLongitude(startLongtitue);

        Location endPoint = new Location("endLocation");
        endPoint.setLatitude(endLatitue);
        endPoint.setLongitude(endLongtitue);

        return startPoint.distanceTo(endPoint);
    }
}
