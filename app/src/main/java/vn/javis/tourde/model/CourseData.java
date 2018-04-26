package vn.javis.tourde.model;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class CourseData {

    @SerializedName("course_id")
    @Expose
    private int courseId;
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
    private int status;
    @SerializedName("insert_datetime")
    @Expose
    private String insertDatetime;

    public static CourseData getData(String data) {
        return new Gson().fromJson(data, CourseData.class);
    }
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

}
