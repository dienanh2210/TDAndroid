package vn.javis.tourde.model;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListCourses {

    @SerializedName("total_count")
    @Expose
    private int totalCount;
    @SerializedName("list")
    @Expose
    private List<CourseArray> list = null;
    public static ListCourses getData(String data) {
        return new Gson().fromJson(data, ListCourses.class);
    }
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<CourseArray> getList() {
        return list;
    }

    public void setList(List<CourseArray> list) {
        this.list = list;
    }
    public class CourseArray {

        @SerializedName("data")
        @Expose
        private Course data;
        @SerializedName("tag")
        @Expose
        private java.util.List<Tag> tag = null;

        public Course getData() {
            return data;
        }

        public void setData(Course data) {
            this.data = data;
        }

        public java.util.List<Tag> getTag() {
            return tag;
        }

        public void setTag(java.util.List<Tag> tag) {
            this.tag = tag;
        }

    }
    public class Tag {

        @SerializedName("course_id")
        @Expose
        private int courseId;
        @SerializedName("tag")
        @Expose
        private String tag;

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

    }

}