package vn.javis.tourde.model;

public class Badge {
    private String badgeName;
    private String dateGet;
    private String imageUrl;

    public Badge(String badgeName, String dateGet, String imageUrl) {
        this.badgeName = badgeName;
        this.dateGet = dateGet;
        this.imageUrl = imageUrl;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String getDateGet() {
        return dateGet;
    }

    public void setDateGet(String dateGet) {
        this.dateGet = dateGet;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
