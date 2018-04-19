package vn.javis.tourde.model;

import java.util.Date;

public class Comment {

    private String token;
    private int course_id;
    private int rating;
    private String comment;

    public Comment(String token, int course_id, int rating, String comment) {
        this.token = token;
        this.course_id = course_id;
        this.rating = rating;
        this.comment = comment;
    }

    public String getToken() {
        return token;
    }

    public int getCourse_id() {
        return course_id;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
