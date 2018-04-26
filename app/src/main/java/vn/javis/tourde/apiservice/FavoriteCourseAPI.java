package vn.javis.tourde.apiservice;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.model.Course;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TourDeService;

public class FavoriteCourseAPI {
    public static void insertFavoriteCourse(String token, int course_id, ServiceCallback callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("course_id", String.valueOf(course_id));
        TourDeService.postWithAuth(ApiEndpoint.POST_INSERT_FAVORITE_COURSE_APP, map, callback);
    }

    public static void deleteFavoriteCourse(String token, int course_id, ServiceCallback callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("course_id", String.valueOf(course_id));
        TourDeService.postWithAuth(ApiEndpoint.POST_DELETE_FAVORITE_COURSE_APP, map, callback);

    }

    public static void getListFavoriteCourse(String token, ServiceCallback callback) {
        List<Course> listCourse = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        TourDeService.getWithAuth(ApiEndpoint.GET_FAVORITE_COURSE_APP, map, callback);
    }
}
