package vn.javis.tourde.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.List;

public class GsonUtil {
//    public static <T> List<T> toList(String json) {
//        if (null == json) {
//            return null;
//        }
//        Gson gson = new Gson();
//        return gson.fromJson(json, new TypeToken<T>(){}.getType());
//    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
}
