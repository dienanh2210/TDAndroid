package vn.javis.tourde.model;

import java.util.Date;

/**
 * Created by admin on 3/23/2018.
 */

public class Course {
    private int course_id;
    private int spot_id;
    private String title;
    private String catchPhrase;
    private String  introduction;
    private String area;
    private String distance;
    private String topImage;
    private Date displayDate;
    private String postUserName;
    private String postUserImage;
    private String season;
    private String averageSlope;
    private int elevation;
    private int courseType;
    private String finishTime;
    private int averagePace;
    private String startAddress;
    private String startGoogleMapUrl;
    private String startLatitude;
    private String startLongitude;
    private String routeUrl;
    private String routeImage;
    private int status;
    private String insertDatetime;
    private int prefecture;
    private String tag;
    private String spotDistance;
    private int orderNumber;
    private String zipCode;
    private String address;
    private String siteUrl;
    private String tel;
    private String googleMapUrl;
    private Double latitude;
    private Double longitude;
    private int reviewCount;
    private String ratingAverage;
    private int ratingTotal;
    private int spotCount;

    public Course(int course_id, int spot_id, String title, String catchPhrase, String introduction, String area, String distance, String topImage, Date displayDate, String postUserName, String postUserImage, String season, String averageSlope, int elevation, int courseType, String finishTime, int averagePace, String startAddress, String startGoogleMapUrl, String startLatitude, String startLongitude, String routeUrl, String routeImage, int status, String insertDatetime, int prefecture, String tag, String spotDistance, int orderNumber, String zipCode, String address, String siteUrl, String tel, String googleMapUrl, Double latitude, Double longitude, int reviewCount, String ratingAverage, int ratingTotal, int spotCount) {
        this.course_id = course_id;
        this.spot_id = spot_id;
        this.title = title;
        this.catchPhrase = catchPhrase;
        this.introduction = introduction;
        this.area = area;
        this.distance = distance;
        this.topImage = topImage;
        this.displayDate = displayDate;
        this.postUserName = postUserName;
        this.postUserImage = postUserImage;
        this.season = season;
        this.averageSlope = averageSlope;
        this.elevation = elevation;
        this.courseType = courseType;
        this.finishTime = finishTime;
        this.averagePace = averagePace;
        this.startAddress = startAddress;
        this.startGoogleMapUrl = startGoogleMapUrl;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.routeUrl = routeUrl;
        this.routeImage = routeImage;
        this.status = status;
        this.insertDatetime = insertDatetime;
        this.prefecture = prefecture;
        this.tag = tag;
        this.spotDistance = spotDistance;
        this.orderNumber = orderNumber;
        this.zipCode = zipCode;
        this.address = address;
        this.siteUrl = siteUrl;
        this.tel = tel;
        this.googleMapUrl = googleMapUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reviewCount = reviewCount;
        this.ratingAverage = ratingAverage;
        this.ratingTotal = ratingTotal;
        this.spotCount = spotCount;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getSpot_id() {
        return spot_id;
    }

    public void setSpot_id(int spot_id) {
        this.spot_id = spot_id;
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

    public Date getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(Date displayDate) {
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

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getAveragePace() {
        return averagePace;
    }

    public void setAveragePace(int averagePace) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInsertDatetime() {
        return insertDatetime;
    }

    public void setInsertDatetime(String insertDatetime) {
        this.insertDatetime = insertDatetime;
    }

    public int getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(int prefecture) {
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

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(String ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public int getRatingTotal() {
        return ratingTotal;
    }

    public void setRatingTotal(int ratingTotal) {
        this.ratingTotal = ratingTotal;
    }

    public int getSpotCount() {
        return spotCount;
    }

    public void setSpotCount(int spotCount) {
        this.spotCount = spotCount;
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_id=" + course_id +
                ", spot_id=" + spot_id +
                ", title='" + title + '\'' +
                ", catchPhrase='" + catchPhrase + '\'' +
                ", introduction='" + introduction + '\'' +
                ", area='" + area + '\'' +
                ", distance='" + distance + '\'' +
                ", topImage='" + topImage + '\'' +
                ", displayDate='" + displayDate + '\'' +
                ", postUserName='" + postUserName + '\'' +
                ", postUserImage='" + postUserImage + '\'' +
                ", season='" + season + '\'' +
                ", averageSlope='" + averageSlope + '\'' +
                ", elevation=" + elevation +
                ", courseType=" + courseType +
                ", finishTime='" + finishTime + '\'' +
                ", averagePace=" + averagePace +
                ", startAddress='" + startAddress + '\'' +
                ", startGoogleMapUrl='" + startGoogleMapUrl + '\'' +
                ", startLatitude='" + startLatitude + '\'' +
                ", startLongitude='" + startLongitude + '\'' +
                ", routeUrl='" + routeUrl + '\'' +
                ", routeImage='" + routeImage + '\'' +
                ", status=" + status +
                ", insertDatetime='" + insertDatetime + '\'' +
                ", prefecture=" + prefecture +
                ", tag='" + tag + '\'' +
                ", spotDistance='" + spotDistance + '\'' +
                ", orderNumber=" + orderNumber +
                ", zipCode='" + zipCode + '\'' +
                ", address='" + address + '\'' +
                ", siteUrl='" + siteUrl + '\'' +
                ", tel='" + tel + '\'' +
                ", googleMapUrl='" + googleMapUrl + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", reviewCount=" + reviewCount +
                ", ratingAverage='" + ratingAverage + '\'' +
                ", ratingTotal=" + ratingTotal +
                ", spotCount=" + spotCount +
                '}';
    }
}
