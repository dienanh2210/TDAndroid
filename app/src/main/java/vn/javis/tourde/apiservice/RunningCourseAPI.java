package vn.javis.tourde.apiservice;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.model.RunningCourse;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.TourDeService;

public class RunningCourseAPI {

    public static void getListRunningCourse(String token, int page, int limit, ServiceCallback callback) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);
        hashMap.put("page", String.valueOf(page));
        hashMap.put("limit", String.valueOf(limit));
        Log.i("Error course drive73",ApiEndpoint.GET_RUNNING_COURSE_APP);
        TourDeService.getWithAuthAray(ApiEndpoint.GET_RUNNING_COURSE_APP, hashMap, callback);

    }
    public static List<RunningCourse> getRunning(Object response) {
        List<RunningCourse> listCourse = new ArrayList<>();
        JSONObject jsonObject = (JSONObject) response;
        try {
            JSONArray jsonArray = (JSONArray) jsonObject.get("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                RunningCourse course = RunningCourse.getData(jsonArray.get(i).toString());
                listCourse.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listCourse;
    }
}
