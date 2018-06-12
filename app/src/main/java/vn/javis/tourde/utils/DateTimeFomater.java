package vn.javis.tourde.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFomater {
    public static String GetDateFomat(String dateInput){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = dateFormat.parse(dateInput);

            String out = dateFormat2.format(date).toString();
           return out;
        } catch (ParseException e) {
        }
        return dateInput;
    }
}
