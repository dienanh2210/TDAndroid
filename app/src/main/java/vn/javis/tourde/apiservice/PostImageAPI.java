package vn.javis.tourde.apiservice;

import android.app.Activity;
import android.graphics.Bitmap;

import java.util.HashMap;

import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class PostImageAPI {
    public static void postImage(Activity activity, Bitmap bitmap, ServiceCallback callback) {
        HashMap<String, String> params = new HashMap<>();
        TourDeService.uploadImageBitmap(activity, ApiEndpoint.POST_IMAGE, bitmap, params, callback);
    }

    public static void postCourseImage(String token, int course_id, String user_image_id, ServiceCallback callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("course_id", String.valueOf(course_id));
        params.put("user_image_id", user_image_id);
        TourDeService.postWithAuth(ApiEndpoint.POST_COURSE_IMAGE, params, callback);
    }
}
