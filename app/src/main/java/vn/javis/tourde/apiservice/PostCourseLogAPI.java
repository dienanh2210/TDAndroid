package vn.javis.tourde.apiservice;

import java.util.HashMap;

import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class PostCourseLogAPI {

    public static void postCourseLog(String token, int course_id,float speed_average,String finish_time, ServiceCallback callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("course_id", String.valueOf(course_id));
        params.put("speed_average", String.valueOf(speed_average));
        params.put("finish_time", finish_time);
        String url = ApiEndpoint.BASE_URL + ApiEndpoint.POST_COURSE_LOG;
        TourDeService.postWithAuth(ApiEndpoint.POST_REVIEW_COURSE, params, callback);
    }
}
