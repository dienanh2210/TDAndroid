package vn.javis.tourde.apiservice;

import android.content.Context;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import vn.javis.tourde.services.TourDeService;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class ListCourseAPI {

    private static ListCourseAPI instance;
    Context context;
    private List<Course> mAllCourses = new ArrayList<Course>();
    public static ListCourseAPI getInstance() {
        return instance;
    }

    public ListCourseAPI(Context context) {
        instance = this;
        this.context = context;
      //  jsonObjectRequest();
        getJsonValues();
    }
    void getJsonValues(){
        HashMap<String, String> params = new HashMap<>();
        params.put("prefecture", "0");
        TourDeService.getWithAuth(ApiEndpoint.GET_COURSE_LIST, params, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) {
                setAllCourses((JSONObject)response);
            }

            @Override
            public void onError(VolleyError error) {
                System.out.println("error" +error);
            }
        });
    }

    void setAllCourses(JSONObject jsonObject) {
        try {
            System.out.println("sdfsdf"+jsonObject);
                JSONObject allJsonObject = jsonObject.getJSONObject("list");
                Iterator<String> key = allJsonObject.keys();
                while (key.hasNext()) {
                    String id = key.next();
                    JSONObject singleJsonObject = allJsonObject.getJSONObject(id).getJSONObject("data");
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    String vl = singleJsonObject.toString();
                    JsonParser jsonParser = new JsonParser();
                    Course thisCourse = Course.getData(vl);
                    System.out.println(thisCourse.toString());
                    if (thisCourse != null) {
                        mAllCourses.add(thisCourse);
                    }
                }
        } catch (JSONException e) {
            System.out.println("error_" + e.getMessage());
        }
    }

    public int getCourseSize() {
        return mAllCourses.size();
    }

    public List<Course> getAllCourses() {
        return mAllCourses;
    }

    public Course getCouseById(int id) {
        Course model = null;

        for (Course course : mAllCourses) {
            if (course.getCourseId() == id) {
                model = course;
                break;
            }
        }
        return model;
    }
    public Course getCouseByIndex(int index) {
        Course model = mAllCourses.get(index);
        return model;
    }
    public List<Course> getCourseByPage(int page) {
        int firstValue = (page - 1) * 5;

        int secondValue = page * 5;

        int maxIndex = mAllCourses.size();

        if (firstValue > maxIndex)
            return null;
        secondValue = secondValue > maxIndex ? maxIndex : secondValue;
        return mAllCourses.subList(firstValue, secondValue);
    }


}
