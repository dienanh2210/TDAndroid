package vn.javis.tourde.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("account_id")
    @Expose
    private int accountId;
    @SerializedName("course_id")
    @Expose
    private int courseId;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("insert_datetime")
    @Expose
    private String insertDatetime;
    @SerializedName("twitter_id")
    @Expose
    private String twitterId;
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    @SerializedName("google_id")
    @Expose
    private String googleId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("hashed_password")
    @Expose
    private String hashedPassword;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("review_insert_datetime")
    @Expose
    private String reviewInsertDatetime;

    public static Review getData(String data) {
        return new Gson().fromJson(data, Review.class);
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getReviewInsertDatetime() {
        return reviewInsertDatetime;
    }

    public void setReviewInsertDatetime(String reviewInsertDatetime) {
        this.reviewInsertDatetime = reviewInsertDatetime;
    }

}
