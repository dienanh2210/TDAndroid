package vn.javis.tourde.model;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RunningCourse {

    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("speed_average")
    @Expose
    private String speedAverage;
    @SerializedName("finish_time")
    @Expose
    private String finishTime;
    @SerializedName("insert_datetime")
    @Expose
    private String insertDatetime;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("top_image")
    @Expose
    private String topImage;

    public static RunningCourse getData(String data) {
        return new Gson().fromJson(data, RunningCourse.class);
    }
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getSpeedAverage() {
        return speedAverage;
    }

    public void setSpeedAverage(String speedAverage) {
        this.speedAverage = speedAverage;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getInsertDatetime() {
        return insertDatetime;
    }

    public void setInsertDatetime(String insertDatetime) {
        this.insertDatetime = insertDatetime;
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

}

