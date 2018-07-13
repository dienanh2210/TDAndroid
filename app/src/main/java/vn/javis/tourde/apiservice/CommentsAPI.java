package vn.javis.tourde.apiservice;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.model.Comment;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.services.TourDeService;
import vn.javis.tourde.volley.VolleyCustomRequest;

public class CommentsAPI {
    private static CommentsAPI instance;

    public static CommentsAPI getInstance() {
        if(instance==null)
            instance = new CommentsAPI();
        return instance;
    }
    public static void postReviewCourse(String token, int course_id, int rating, String comment, ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("course_id", String.valueOf(course_id));
        params.put("rating", String.valueOf(rating));
        params.put("comment", comment);
        TourDeService.postWithAuth(ApiEndpoint.POST_REVIEW_COURSE, params, callback);
    }
    public static void postCheckCourseReview(String token, int course_id, ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("course_id", String.valueOf(course_id));
        TourDeService.postWithAuth(ApiEndpoint.POST_CHECK_COURSE_REVIEW, params, callback);
    }
}
