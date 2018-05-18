package vn.javis.tourde.model;

public class SpotCheckIn {
    private String spotNumber;
    private String spotName;
    private String imageUrl;

    public SpotCheckIn(String spotNumber, String spotName, String imageUrl) {
        this.spotNumber = spotNumber;
        this.spotName = spotName;
        this.imageUrl = imageUrl;
    }

    public String getSportNumber() {
        return spotNumber;
    }

    public String getSpotName() {
        return spotName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
