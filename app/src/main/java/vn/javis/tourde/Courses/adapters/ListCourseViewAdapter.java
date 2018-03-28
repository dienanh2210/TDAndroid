package vn.javis.tourde.Courses.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import vn.javis.tourde.Courses.models.Course;
import vn.javis.tourde.R;

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
        DatabaseAdapter db = new DatabaseAdapter(context);

        TextView txt_title = (TextView) convertView.findViewById(R.id.txt_course_title);
        TextView txt_area = (TextView) convertView.findViewById(R.id.txt_course_area);
        TextView course_length = (TextView) convertView.findViewById(R.id.txt_course_length);
        TextView txt_num_comment = (TextView) convertView.findViewById(R.id.txt_num_comment);
        TextView txt_num_spot = (TextView) convertView.findViewById(R.id.txt_num_spot);
        TextView txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
        TextView txt_creater = (TextView) convertView.findViewById(R.id.txt_creater);
        ImageView img_course = (ImageView) convertView.findViewById(R.id.img_course);
        ImageView img_star = (ImageView) convertView.findViewById(R.id.img_stars);

        txt_title.setText(listCourses.get(position).getTitle());
        txt_area.setText(listCourses.get(position).getArea_name());
        course_length.setText("Length: " + listCourses.get(position).getCourse_length());
        txt_num_comment.setText("" + db.getCommentByIdCourse(listCourses.get(position).getId()));
        txt_num_spot.setText("" + listCourses.get(position).getSpot_num());
        txt_comment.setText("" + listCourses.get(position).getExplaination());
        txt_creater.setText(listCourses.get(position).getUser_create());

        RecyclerView tags_view = (RecyclerView) convertView.findViewById(R.id.recycleview_tags);
        setListTagsView(tags_view, listCourses.get(position).getTags());

        try {
            int id_img_course = convertView.getResources().getIdentifier(listCourses.get(position).getImg_link(), "drawable", context.getPackageName());
            int id_img_star_rate = convertView.getResources().getIdentifier("star_rate_" + listCourses.get(position).getStar(), "drawable", context.getPackageName());

            img_star.setImageResource(id_img_star_rate);
            img_course.setImageResource(id_img_course);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return convertView;
    }

    void setListTagsView(RecyclerView rcvTag, String tags) {

        String[] arr_tags = tags.split(",");
        final ArrayList<String> list_tags = new ArrayList<String>(Arrays.asList(arr_tags));
        ListTagsRecylerViewAdapter adapter = new ListTagsRecylerViewAdapter(context, list_tags);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        // layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvTag.setLayoutManager(layoutManager);
        rcvTag.setAdapter(adapter);
    }

}


