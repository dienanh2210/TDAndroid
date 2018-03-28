package vn.javis.tourde.Courses.models;

/**
 * Created by admin on 3/23/2018.
 */

public class Course {
    private int id;
    private String title;
    private String area_name;
    private float course_length;
    private int star;
    private int spot_num;
    private String tags;
    private String user_create;
    private String img_link;



    private String explaination;


    public Course(int id, String title, String area_name, float course_length, int star, int spot_num, String tags, String user_create,String explaination, String img_link)
    {
        this.id = id;
        this.title = title;
        this.area_name = area_name;
        this.course_length = course_length;
        this.star = star;
        this.spot_num = spot_num;
        this.tags = tags;
        this.user_create = user_create;
        this.img_link = img_link;
        this.explaination =explaination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public float getCourse_length() {
        return course_length;
    }

    public void setCourse_length(float course_length) {
        this.course_length = course_length;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getSpot_num() {
        return spot_num;
    }

    public void setSpot_num(int spot_num) {
        this.spot_num = spot_num;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUser_create() {
        return user_create;
    }

    public void setUser_create(String user_create) {
        this.user_create = user_create;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }
    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }
}
