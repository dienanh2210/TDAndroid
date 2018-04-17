package vn.javis.tourde.utils;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable{

    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("spot_id")
    @Expose
    private String spotId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("catch_phrase")
    @Expose
    private String catchPhrase;
    @SerializedName("introduction")
    @Expose
    private String introduction;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("top_image")
    @Expose
    private String topImage;
    @SerializedName("display_date")
    @Expose
    private String displayDate;
    @SerializedName("post_user_name")
    @Expose
    private String postUserName;
    @SerializedName("post_user_image")
    @Expose
    private String postUserImage;
    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("average_slope")
    @Expose
    private String averageSlope;
    @SerializedName("elevation")
    @Expose
    private String elevation;
    @SerializedName("course_type")
    @Expose
    private String courseType;
    @SerializedName("finish_time")
    @Expose
    private String finishTime;
    @SerializedName("average_pace")
    @Expose
    private String averagePace;
    @SerializedName("start_address")
    @Expose
    private String startAddress;
    @SerializedName("start_google_map_url")
    @Expose
    private String startGoogleMapUrl;
    @SerializedName("start_latitude")
    @Expose
    private String startLatitude;
    @SerializedName("start_longitude")
    @Expose
    private String startLongitude;
    @SerializedName("route_url")
    @Expose
    private String routeUrl;
    @SerializedName("route_image")
    @Expose
    private String routeImage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("insert_datetime")
    @Expose
    private String insertDatetime;
    @SerializedName("prefecture")
    @Expose
    private String prefecture;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("spot_distance")
    @Expose
    private String spotDistance;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
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
    @SerializedName("review_count")
    @Expose
    private String reviewCount;
    @SerializedName("rating_average")
    @Expose
    private String ratingAverage;
    @SerializedName("rating_total")
    @Expose
    private String ratingTotal;
    @SerializedName("spot_count")
    @Expose
    private List<String> content;
    private String spotCount;

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public static Data getData(String data) {
        return new Gson().fromJson(data, Data.class);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTopImage() {
        return topImage;
    }

    public void setTopImage(String topImage) {
        this.topImage = topImage;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public String getPostUserImage() {
        return postUserImage;
    }

    public void setPostUserImage(String postUserImage) {
        this.postUserImage = postUserImage;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getAverageSlope() {
        return averageSlope;
    }

    public void setAverageSlope(String averageSlope) {
        this.averageSlope = averageSlope;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getAveragePace() {
        return averagePace;
    }

    public void setAveragePace(String averagePace) {
        this.averagePace = averagePace;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getStartGoogleMapUrl() {
        return startGoogleMapUrl;
    }

    public void setStartGoogleMapUrl(String startGoogleMapUrl) {
        this.startGoogleMapUrl = startGoogleMapUrl;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public String getRouteImage() {
        return routeImage;
    }

    public void setRouteImage(String routeImage) {
        this.routeImage = routeImage;
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

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSpotDistance() {
        return spotDistance;
    }

    public void setSpotDistance(String spotDistance) {
        this.spotDistance = spotDistance;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(String ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public String getRatingTotal() {
        return ratingTotal;
    }

    public void setRatingTotal(String ratingTotal) {
        this.ratingTotal = ratingTotal;
    }

    public String getSpotCount() {
        return spotCount;
    }

    public void setSpotCount(String spotCount) {
        this.spotCount = spotCount;
    }

}
