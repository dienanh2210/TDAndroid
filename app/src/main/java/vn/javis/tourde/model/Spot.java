package vn.javis.tourde.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Spot {
    @SerializedName("spot_id")
    @Expose
    private int spotId;
    @SerializedName("course_id")
    @Expose
    private int courseId;
    @SerializedName("spot_distance")
    @Expose
    private String spotDistance;
    @SerializedName("spot_introduction")
    @Expose
    private String spotIntroduction;
    @SerializedName("order_number")
    @Expose
    private int orderNumber;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("top_image")
    @Expose
    private String topImage;
    @SerializedName("catch_phrase")
    @Expose
    private String catchPhrase;
    @SerializedName("introduction")
    @Expose
    private String introduction;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("site_url")
    @Expose
    private String siteUrl;
    @SerializedName("tel")
    @Expose
    private String tel;
    @SerializedName("google_map_url")
    @Expose
    private String googleMapUrl;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("insert_datetime")
    @Expose
    private String insertDatetime;
    @SerializedName("tag")
    @Expose
    private Object tag;

    public static Spot getData(String data) {
        return new Gson().fromJson(data, Spot.class);
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSpotDistance() {
        return spotDistance;
    }

    public void setSpotDistance(String spotDistance) {
        this.spotDistance = spotDistance;
    }

    public String getSpotIntroduction() {
        return spotIntroduction;
    }

    public void setSpotIntroduction(String spotIntroduction) {
        this.spotIntroduction = spotIntroduction;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopImage() {
        return topImage;
    }

    public void setTopImage(String topImage) {
        this.topImage = topImage;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGoogleMapUrl() {
        return googleMapUrl;
    }

    public void setGoogleMapUrl(String googleMapUrl) {
        this.googleMapUrl = googleMapUrl;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInsertDatetime() {
        return insertDatetime;
    }

    public void setInsertDatetime(String insertDatetime) {
        this.insertDatetime = insertDatetime;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

}
