package vn.javis.tourde.apiservice;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.model.Course;
import vn.javis.tourde.model.FavoriteCourse;
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
    public static List<FavoriteCourse> getFavorites(Object response)
    {

        List<FavoriteCourse> listCourse = new ArrayList<>();
        JSONObject jsonObject = (JSONObject)response;
        try {
            JSONArray jsonArray = (JSONArray) jsonObject.get("list");
            for(int i=0;i<jsonArray.length();i++)
            {
                FavoriteCourse course = FavoriteCourse.getData(jsonArray.get(i).toString());
                listCourse.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  listCourse;
    }

}
