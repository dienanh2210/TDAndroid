package vn.javis.tourde.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("review_update_datetime")
    @Expose
    private String reviewUpdateDatetime;

    public static Review getData(String data) {
        return new Gson().fromJson(data, Review.class);
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getReviewUpdateDatetime() {
        return reviewUpdateDatetime;
    }

    public void setReviewUpdateDatetime(String reviewUpdateDatetime) {
        this.reviewUpdateDatetime = reviewUpdateDatetime;
    }

}





