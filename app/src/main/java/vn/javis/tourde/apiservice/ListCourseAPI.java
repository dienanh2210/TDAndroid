package vn.javis.tourde.apiservice;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
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
    int totalSize=0;
    public static ListCourseAPI getInstance() {
        return instance;
    }

    public ListCourseAPI(Context context) {
        if (instance == null) {
            instance = this;
            this.context = context;
            //  jsonObjectRequest();
            //getJsonValues();
        }
    }

    void getJsonValues() {
        HashMap<String, String> params = new HashMap<>();
        params.put("prefecture", "13");
        TourDeService.getWithAuth(ApiEndpoint.GET_COURSE_LIST, params, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) {
                setAllCourses((JSONObject) response);
            }

            @Override
            public void onError(VolleyError error) {
                System.out.println("error" + error);
            }
        });
    }

    public static void getJsonValues(ServiceCallback callback,int page,int limit) {
        HashMap<String, String> params = new HashMap<>();
        params.put("page",String.valueOf(page));
        params.put("limit",String.valueOf(limit));
        TourDeService.getWithAuth(ApiEndpoint.GET_COURSE_LIST, params, callback);
    }

    public static void getJsonValueSearch(HashMap params, ServiceCallback callback,int page,int limit) {
        params.put("page",String.valueOf(page));
        params.put("limit",String.valueOf(limit));
        TourDeService.getWithAuth(ApiEndpoint.GET_COURSE_LIST, params, callback);
    }

    public static void setAllCourses(JSONObject jsonObject) {
        instance.mAllCourses.clear();
        List<Course> list1 =new ArrayList<>();
        try {
            int abc = 0;
            JSONObject allJsonObject = jsonObject.getJSONObject("list");
            Iterator<String> key = allJsonObject.keys();
            while (key.hasNext())
            {
                abc++;
                String id = key.next();

                JSONObject singleJsonObject = allJsonObject.getJSONObject(id).getJSONObject("data");
               JSONArray singleJsonObjectTag = allJsonObject.getJSONObject(id).getJSONArray("tag");
                Gson gson = new GsonBuilder().serializeNulls().create();
                String vl = singleJsonObject.toString();

                ArrayList<String> listTag = new ArrayList<String>();
                Course thisCourse = Course.getData(vl);
            }

            for(int i=list1.size()-1;i>=0;i--)
            {
                instance.mAllCourses.add(list1.get(i));
            }
            Log.i("error_" , abc+"");
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
            if (Integer.valueOf(course.getCourseId()) == id) {
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

    public List<Course> getCourseByPage(int page,int pageSize) {
        int firstValue = (page - 1) * pageSize;

        int secondValue = page * pageSize;

        int maxIndex = mAllCourses.size();

        if (firstValue > maxIndex)
            return null;
        secondValue = secondValue > maxIndex ? maxIndex : secondValue;
        return mAllCourses.subList(firstValue, secondValue);
    }

    public List<Course> getCoursesByKeys(HashMap map) {

        List<Course> list = new ArrayList<>();
        int maxIndex = mAllCourses.size();
        return list;
    }

    public Course getCourseByPosition(int position) {
        return mAllCourses.get(position);
    }

    public int getCourseIdByPosition(int position) {
        return Integer.valueOf(mAllCourses.get(position).getCourseId());
    }

    public List<Course> getSearchCourse(ServiceCallback callback) {
        List<Course> listSearch = new ArrayList<>();
        return mAllCourses;
    }
}
