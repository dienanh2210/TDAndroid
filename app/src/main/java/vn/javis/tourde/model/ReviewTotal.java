package vn.javis.tourde.model;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewTotal {

    @SerializedName("review_count")
    @Expose
    private String reviewCount;
    @SerializedName("rating_total")
    @Expose
    private String ratingTotal;
    @SerializedName("rating_average")
    @Expose
    private float ratingAverage;

    public static ReviewTotal getData(String data) {
        return new Gson().fromJson(data, ReviewTotal.class);
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getRatingTotal() {
        return ratingTotal;
    }

    public void setRatingTotal(String ratingTotal) {
        this.ratingTotal = ratingTotal;
    }

    public float getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(float ratingAverage) {
        this.ratingAverage = ratingAverage;
    }
}