package vn.javis.tourde.apiservice;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import vn.javis.tourde.model.Course;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TourDeService;

public class GetCourseDataAPI {

    public static void getCourseData(int courseId,ServiceCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("course_id",""+courseId);
        TourDeService.getWithAuth(ApiEndpoint.GET_COURSE_DATA, params, callback);
    }
}
