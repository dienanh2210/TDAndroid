package vn.javis.tourde.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import vn.javis.tourde.R;

public class ListMySpotsImageAdapter extends ArrayAdapter<String> {
    Activity context = null;
    List<String> myArray = null;
    int layoutId;


    public ListMySpotsImageAdapter(Activity context, int layoutId, List<String> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = arr;
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        if (convertView != null) {
            if (myArray.size() > 0 && position >= 0) {

                String imgUrl = myArray.get(position);
                ImageView imgCourse = (ImageView) convertView.findViewById(R.id.img_single_spot);
                if (position == 0) {
                    imgCourse.setBackground(convertView.getResources().getDrawable(R.drawable.plus_button));
                } else {

                    //Picasso.with(context).load(imgUrl).into(imgCourse);
                }
            }
        }
        return convertView;
    }

}
