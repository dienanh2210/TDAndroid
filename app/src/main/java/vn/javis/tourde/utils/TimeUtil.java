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
    public static String formatDayFromString(String inputFormatString, String inputDate) {
        return formatDateFromString(inputFormatString, "yyyy.MM.dd", inputDate);

    }

  public static String getTimeFormat(long time) {
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        final String finishTime = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
        return finishTime;
    }

}
