package vn.javis.tourde.database;

import android.content.Context;
import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vn.javis.tourde.activity.MainActivity;
import vn.javis.tourde.adapter.VolleySingleton;
import vn.javis.tourde.model.Course;

public class ListCourseAPI {

    private static ListCourseAPI instance;
    private String DB_PATH = "data/data/vn.javis.tourde/";
    private static String DB_NAME = "tour_demo_db.s3db";
    Context context;
    private String mUrl = "http://www.app-tour-de-nippon.jp/api/get/getCourseList/?prefecture%5B%5D=0";
    private JSONObject mJsonObject;
    private List<Course> mAllCourses = new ArrayList<Course>();

    public static ListCourseAPI getInstance() {
        return instance;
    }

    public ListCourseAPI(Context context) {

        instance = this;
        this.context = context;
        jsonObjectRequest();
    }

    void jsonObjectRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        mJsonObject = response;
                        setAllCourses(mJsonObject);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        VolleySingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);
    }


    void setAllCourses(JSONObject jsonObject) {
        try {
            if (mJsonObject != null) {

                JSONObject allJsonObject = mJsonObject.getJSONObject("list");
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
                MainActivity.getInstance().loadCourseList();
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
            if(course.getCourseId() == id)
            {
                model = course;
                break;
            }
        }
        return model;
    }


}
