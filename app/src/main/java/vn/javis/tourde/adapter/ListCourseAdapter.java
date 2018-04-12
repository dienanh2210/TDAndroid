package vn.javis.tourde.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.model.Course;

public class ListCourseAdapter extends RecyclerView.Adapter<ListCourseAdapter.CourseViewHolder> {

    List<Course> listCourse = new ArrayList<Course>();
    Context context;
    View mView;

    public ListCourseAdapter(List<Course> listCourse, Context context) {

        this.listCourse = listCourse;
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.course_view_row, parent, false);
        return new CourseViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, final int position) {
        Course model = listCourse.get(position);
        holder.txtTitle.setText(model.getTitle());
        holder.txtArea.setText(model.getArea());
        holder.txtTag.setText("# "+model.getTag());
        holder.txtDistance.setText(model.getDistance() + "km");
        holder.txtCatchPhrase.setText(model.getCatchPhrase());
        holder.txtReviewCount.setText(model.getReviewCount());
        holder.txtSpotCount.setText(model.getSpotCount());
        holder.txtPostUser.setText(model.getPostUserName());

     //   ImageLoader imageLoader = ImageLoader.getInstance();
     //   imageLoader.init(ImageLoaderConfiguration.createDefault(context));
     //   imageLoader.getMemoryCache();
     //   imageLoader.getInstance().displayImage(model.getTopImage(), holder.imgCourse);
     //   imageLoader.getInstance().displayImage(model.getPostUserImage(), holder.imgPostUser);

        Picasso.with(context).load(model.getTopImage()).into(holder.imgCourse);
        Picasso.with(context).load(model.getPostUserImage()).into(holder.imgPostUser);

        int rate = Math.round(model.getRatingAverage());

        if (rate == 1) {
            holder.imgStar1.setImageResource(R.drawable.star_yellow);
        } else if (rate == 2) {
            holder.imgStar1.setImageResource(R.drawable.star_yellow);
            holder.imgStar2.setImageResource(R.drawable.star_yellow);
        } else if (rate == 3) {
            holder.imgStar1.setImageResource(R.drawable.star_yellow);
            holder.imgStar3.setImageResource(R.drawable.star_yellow);
            holder.imgStar2.setImageResource(R.drawable.star_yellow);
        } else if (rate == 4) {
            holder.imgStar1.setImageResource(R.drawable.star_yellow);
            holder.imgStar2.setImageResource(R.drawable.star_yellow);
            holder.imgStar3.setImageResource(R.drawable.star_yellow);
            holder.imgStar4.setImageResource(R.drawable.star_yellow);
        } else if (rate == 5) {
            holder.imgStar1.setImageResource(R.drawable.star_yellow);
            holder.imgStar2.setImageResource(R.drawable.star_yellow);
            holder.imgStar3.setImageResource(R.drawable.star_yellow);
            holder.imgStar4.setImageResource(R.drawable.star_yellow);
            holder.imgStar5.setImageResource(R.drawable.star_yellow);
        }
        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickedListener !=null){
                    onItemClickedListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCourse.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_course_title)
        TextView txtTitle;
        @BindView(R.id.txt_catch_phrase)
        TextView txtCatchPhrase;
        @BindView(R.id.txt_course_area)
        TextView txtArea;
        @BindView(R.id.txt_distance)
        TextView txtDistance;
        @BindView(R.id.txt_review_count)
        TextView txtReviewCount;
        @BindView(R.id.txt_spot_count)
        TextView txtSpotCount;
        @BindView(R.id.txt_post_user)
        TextView txtPostUser;
        @BindView(R.id.img_course)
        ImageView imgCourse;
        @BindView(R.id.star_1)
        ImageView imgStar1;
        @BindView(R.id.star_2)
        ImageView imgStar2;
        @BindView(R.id.star_3)
        ImageView imgStar3;
        @BindView(R.id.star_4)
        ImageView imgStar4;
        @BindView(R.id.star_5)
        ImageView imgStar5;
        @BindView(R.id.txt_tags)
        TextView txtTag;
        @BindView(R.id.img_post_user)
        ImageView imgPostUser;

        public CourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickedListener{
        void onItemClick(int position);
    }
    private OnItemClickedListener  onItemClickedListener;
    public void setOnItemClickListener(OnItemClickedListener onItemClickedListener){
        this.onItemClickedListener = onItemClickedListener;
    }
}
