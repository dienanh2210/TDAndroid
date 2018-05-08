package vn.javis.tourde.apiservice;

import java.util.HashMap;

import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class PostImageAPI {
    public static void postImage(String imageBinary, ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("image", imageBinary);
        String url = ApiEndpoint.BASE_URL + ApiEndpoint.POST_IMAGE;
        TourDeService.postWithAuth(ApiEndpoint.POST_REVIEW_COURSE, params, callback);
    }
    public static void postCourseImage(String token, int course_id, String user_image_id, ServiceCallback callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("course_id", String.valueOf(course_id));
        params.put("user_image_id", user_image_id);
        String url = ApiEndpoint.BASE_URL + ApiEndpoint.POST_COURSE_IMAGE;
        TourDeService.postWithAuth(ApiEndpoint.POST_REVIEW_COURSE, params, callback);
    }
}
