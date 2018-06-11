package vn.javis.tourde.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by QuanPham on 6/9/18.
 */

public class TimeUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT1 = "HH:mm:ss";

    public static String formatDateFromString(String inputFormat, String outputFormat, String inputDate) {
        Date parsed;
        String outputDate = "";
        if (inputDate == null) {
            return "";
        }
        try {
            SimpleDateFormat df_input = new SimpleDateFormat(inputFormat);
            SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.JAPANESE);
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (Exception e) {

        }
        return outputDate;

    }

}
