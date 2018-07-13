package vn.javis.tourde.model;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Badge implements Serializable{

    @SerializedName("stamp_id")
    @Expose
    private int stampId;
    @SerializedName("course_id")
    @Expose
    private int courseId;
    @SerializedName("spot_id")
    @Expose
    private int spotId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("insert_datetime")
    @Expose
    private String insertDatetime;

    public int getStampId() {
        return stampId;
    }

    public void setStampId(int stampId) {
        this.stampId = stampId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInsertDatetime() {
        return insertDatetime;
    }

    public void setInsertDatetime(String insertDatetime) {
        this.insertDatetime = insertDatetime;
    }

}


