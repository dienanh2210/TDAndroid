package vn.javis.tourde.utils;

import com.google.gson.Gson;

import vn.javis.tourde.model.SaveCourseRunning;

public class ClassToJson<T> {

    public String getStringClassJson(T saveCourseRunning) {
        Gson gson = new Gson();
        String json = gson.toJson(saveCourseRunning);
        return json;
    }

    public T getClassFromJson(String jsonString, Class<T> clss) {
        Gson gson = new Gson();
        T saveCourseRunning = gson.fromJson(jsonString, clss);
        return saveCourseRunning;
    }

}
