package vn.javis.tourde.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import vn.javis.tourde.database.ListCourseAPI;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.R;

import java.net.URL;

/**
 * Created by admin on 3/23/2018.
 */

public class ListCourseViewAdapter extends ArrayAdapter<Course> {

    Activity context;
    ArrayList<Course> listCourses;
    int layoutId;

    public ListCourseViewAdapter(Activity context, int layoutId, ArrayList<Course> listCourses) {
        super(context, layoutId, listCourses);
        this.context = context;
        this.layoutId = layoutId;
        this.listCourses = listCourses;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        TextView txt_title = (TextView) convertView.findViewById(R.id.txt_course_title);
        TextView txt_catch_phrase = (TextView) convertView.findViewById(R.id.txt_catch_phrase);
        TextView txt_area = (TextView) convertView.findViewById(R.id.txt_course_area);
        TextView course_length = (TextView) convertView.findViewById(R.id.txt_course_length);
        TextView txt_num_comment = (TextView) convertView.findViewById(R.id.txt_num_comment);
        TextView txt_num_spot = (TextView) convertView.findViewById(R.id.txt_spot_count);
        TextView txt_creater = (TextView) convertView.findViewById(R.id.txt_creater);
        ImageView img_course = (ImageView) convertView.findViewById(R.id.img_course);
        ImageView img_star = (ImageView) convertView.findViewById(R.id.img_stars);
        TextView txt_tag = (TextView) convertView.findViewById(R.id.txt_tags);

        txt_title.setText(listCourses.get(position).getTitle());
        txt_area.setText(listCourses.get(position).getArea());
        txt_tag.setText(listCourses.get(position).getTag());
        course_length.setText(listCourses.get(position).getDistance() + "km");
        txt_num_comment.setText("" + listCourses.get(position).getReviewCount());
        txt_num_spot.setText("" + listCourses.get(position).getSpotCount());
        txt_catch_phrase.setText("" + listCourses.get(position).getCatchPhrase());
        System.out.println(listCourses.get(position).getTopImage());
        String imgUrl = listCourses.get(position).getTopImage();
        if (imgUrl != null) {
            Uri url = Uri.parse(imgUrl);

            img_course.setImageURI(url);
        }
        txt_creater.setText(listCourses.get(position).getPostUserName());

        return convertView;
    }

}


