package vn.javis.tourde.model;

import android.nfc.Tag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseDetail {
    private CourseData mCourseData;
    private List<Review> review;
    private List<Spot> spot;


    private ReviewTotal reviewTotal;
    private List<String> listTag;

    public CourseDetail(JSONObject jsonResponse) {

        try {

            JSONObject data = jsonResponse.getJSONObject("data");
            mCourseData = CourseData.getData(data.toString());
            spot = new ArrayList<Spot>();
            JSONArray listSpot = jsonResponse.getJSONArray("spot");
            for (int i = 0; i < listSpot.length(); i++) {
                JSONObject dtSpot = (JSONObject) listSpot.get(i);
                Spot modelSpot = Spot.getData(dtSpot.getJSONObject("data").toString());

                JSONArray listTagOb = dtSpot.getJSONArray("tag");
                listTag = new ArrayList<String>();
                for (int a = 0; a < listTagOb.length(); a++) {
                    listTagOb.get(a);
                    listTag.add(listTagOb.get(a).toString());
                }
                modelSpot.setListTag(listTag);
                spot.add(modelSpot);
            }
            listTag = new ArrayList<String>();
            JSONArray listTagOb = jsonResponse.getJSONArray("tag");
            for (int i = 0; i < listTagOb.length(); i++) {
               listTagOb.get(i);
                listTag.add(listTagOb.get(i).toString());
            }

            review = new ArrayList<Review>();
            JSONArray listReview = jsonResponse.getJSONArray("review");
            for (int i = 0; i < listReview.length(); i++) {
                JSONObject dtReview = (JSONObject) listReview.get(i);
                Review modelReview = Review.getData(dtReview.toString());
                review.add(modelReview);
            }
            reviewTotal = ReviewTotal.getData(jsonResponse.getJSONObject("review_total").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CourseData getmCourseData() {
        return mCourseData;
    }

    public void setmCourseData(CourseData mCourseData) {
        this.mCourseData = mCourseData;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public List<Spot> getSpot() {
        return spot;
    }

    public void setSpot(List<Spot> spot) {
        this.spot = spot;
    }

    public ReviewTotal getReviewTotal() {
        return reviewTotal;
    }

    public void setReviewTotal(ReviewTotal reviewTotal) {
        this.reviewTotal = reviewTotal;
    }

    public List<String> getListTag() {
        return listTag;
    }

    public void setListTag(List<String> listTag) {
        this.listTag = listTag;
    }
}
