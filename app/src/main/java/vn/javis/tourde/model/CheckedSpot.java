package vn.javis.tourde.model;

public class CheckedSpot {
    private int spotID;
    private String title;
    private int orderNumber;
    private String topImage;
    private String time;
    private boolean checked;
    private double avarageSpeed;


    public CheckedSpot(int spotID, String title, int order, String imgUrl,boolean isChecked) {
        this.spotID = spotID;
        this.title = title;
        this.orderNumber = order;
        this.topImage = imgUrl;
        checked =isChecked;
    }

    public int getSpotID() {
        return spotID;
    }

    public void setSpotID(int spotID) {
        this.spotID = spotID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return orderNumber;
    }

    public void setOrder(int order) {
        this.orderNumber = order;
    }

    public String getImgUrl() {
        return topImage;
    }

    public void setImgUrl(String imgUrl) {
        this.topImage = imgUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public double getAvarageSpeed() {
        return avarageSpeed;
    }

    public void setAvarageSpeed(double avarageSpeed) {
        this.avarageSpeed = avarageSpeed;
    }
}
