package vn.javis.tourde.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {


    SharedPreferences prefs = null;
    SharedPreferences.Editor editor = null;

    Context context;
    static SharedPreferencesUtils mInstance;

    public static SharedPreferencesUtils getInstance(Context context) {
        if (mInstance == null) mInstance = new SharedPreferencesUtils(context);
        return mInstance;
    }

    public SharedPreferencesUtils(Context context) {
        this.context = context;
        prefs = this.context.getSharedPreferences("tourde.Data",
                Context.MODE_PRIVATE);
    }

    public void delete() {
        prefs.edit().clear().apply();
    }

    public String getStringValue(String key) {
        return prefs.getString(key, "");
    }

    public void setStringValue(String key, String value) {
        if (editor == null) {
            editor = prefs.edit();
        }
        editor.putString(key, value);
        editor.commit();
    }

    public int getIntValue(String key) {
        return prefs.getInt(key, 0);
    }

    public void setIntValue(String key, int value) {
        if (editor == null) {
            editor = prefs.edit();
        }
        editor.putInt(key, value);
        editor.commit();
    }

    public void removeKey(String key) {
        if (editor == null) {
            editor = prefs.edit();
        }
        editor.remove(key);
        editor.commit();
    }

    public void setBooleanValue(String key, boolean value) {
        if (editor == null) {
            editor = prefs.edit();
        }
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBooleanValue(String key) {
        return prefs.getBoolean(key, false);
    }

}
