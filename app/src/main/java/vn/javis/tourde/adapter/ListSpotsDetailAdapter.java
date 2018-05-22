package vn.javis.tourde.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.model.Spot;

public class ListSpotsDetailAdapter extends ArrayAdapter<Spot> {
    Activity context = null;
    List<Spot> myArray = null;
    int layoutId;


    public ListSpotsDetailAdapter(Activity context, int layoutId, List<Spot> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = arr;
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        LayoutInflater inflater =
                context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        if (convertView != null) {
            if (myArray.size() > 0 && position >= 0) {
                final Spot spot = myArray.get(position);

                TextView txtTitle = (TextView) convertView.findViewById(R.id.title_story_1);
                TextView txtIndex = (TextView) convertView.findViewById(R.id.ic_story_1);
                TextView txtCatchPhrase = (TextView) convertView.findViewById(R.id.story_1_image_des);
                TextView txtIntro = (TextView) convertView.findViewById(R.id.content_story_1);
                TextView txtTag = (TextView) convertView.findViewById(R.id.tag_story_1);
                ImageView imgCourse = (ImageView) convertView.findViewById(R.id.img_story_1);
                LinearLayout lnDistance = (LinearLayout) convertView.findViewById(R.id.ln_distance_between_spot);
                ImageView btnSpotImages = (ImageView) convertView.findViewById(R.id.btn_spot_images);
                int order = spot.getOrderNumber() + 1;
                txtTitle.setText(spot.getTitle());
                txtIndex.setText(String.valueOf(order));
                txtCatchPhrase.setText(spot.getCatchPhrase());
                txtIntro.setText(spot.getIntroduction());
                Log.i("myArray.size",position+""+ myArray.get(1).getTitle());
                // txtTag.setText(spot.getTag().toString());
                  Picasso.with(context).load(spot.getTopImage()).into(imgCourse);
                if (order == myArray.size())
                    lnDistance.setVisibility(View.GONE);

                btnSpotImages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onSpotImageClick != null) {
                            onSpotImageClick.onItemClick(spot.getSpotId());
                        }
                    }
                });
            }
        }
        return convertView;
    }
    public interface OnSpotImageClick {
        void onItemClick(int position);
    }

    private OnSpotImageClick onSpotImageClick;

    public void setOnSpotImageClick(OnSpotImageClick onSpotImageClick) {
        this.onSpotImageClick = onSpotImageClick;
    }

}
