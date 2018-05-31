package vn.javis.tourde.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagClass {

    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("tag")
    @Expose
    private String tag;
    public static TagClass getData(String data) {
        return new Gson().fromJson(data, TagClass.class);
    }
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}