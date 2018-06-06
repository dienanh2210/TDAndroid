package vn.javis.tourde.utils;

import android.content.Context;

import com.squareup.picasso.Picasso;

public class PicassoUtil {

    private static Picasso instance;

    public static Picasso getSharedInstance(Context context) {
        if (instance == null) {
            instance = Picasso.get();
        }
        return instance;
    }

}
