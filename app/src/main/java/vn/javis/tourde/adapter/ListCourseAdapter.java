package vn.javis.tourde.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.view.CircleTransform;

public class
ListCourseAdapter extends RecyclerView.Adapter<ListCourseAdapter.CourseViewHolder> {

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
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, final int position) {
        Course model = listCourse.get(position);

        holder.txtTitle.setText(model.getTitle());
        holder.txtArea.setText(model.getArea());
        holder.txtTag.setText("# " + model.getTag());
        holder.txtDistance.setText(model.getDistance() + "km");
        holder.txtCatchPhrase.setText(model.getCatchPhrase());
        holder.txtReviewCount.setText(model.getReviewCount());
        holder.txtSpotCount.setText(model.getSpotCount());
        holder.txtPostUser.setText(model.getPostUserName());
        Picasso.with(context).load(model.getTopImage()).into(holder.imgCourse);
        Picasso.with(context).load(model.getPostUserImage()).transform(new CircleTransform()).into(holder.imgPostUser);
        holder.isFavorite = model.getStatus() == 1 ? true : false;
        holder.isFavorite = false;
        if (holder.isFavorite) {
            holder.btnFavorite.setBackground(mView.getResources().getDrawable(R.drawable.icon_bicycle_blue));
        }
        int rate = Math.round(model.getRatingAverage());

        if (rate == 1) {
            holder.imgStarRate.setImageResource(R.drawable.icon_star1);
        } else if (rate == 2) {
            holder.imgStarRate.setImageResource(R.drawable.icon_star2);
        } else if (rate == 3) {
            holder.imgStarRate.setImageResource(R.drawable.icon_star3);
        } else if (rate == 4) {
            holder.imgStarRate.setImageResource(R.drawable.icon_star4);
        } else if (rate == 5) {
            holder.imgStarRate.setImageResource(R.drawable.icon_star5);
        }
        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(position);
                }
            }
        });
        holder.txtCatchPhrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(position);
                }
            }
        });
        holder.imgCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(position);
                }
            }
        });
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                holder.isFavorite = !holder.isFavorite;
                String token = "";
                int course_id = 1;
                if (holder.isFavorite) {
                    FavoriteCourseAPI.insertFavoriteCourse(token, course_id, new ServiceCallback() {
                        @Override
                        public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                            holder.btnFavorite.setBackground(view.getResources().getDrawable(R.drawable.icon_bicycle_blue));
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
                } else {
                    FavoriteCourseAPI.deleteFavoriteCourse(token, course_id, new ServiceCallback() {
                        @Override
                        public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                            holder.btnFavorite.setBackground(view.getResources().getDrawable(R.drawable.icon_bicycle_gray));
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
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
        @BindView(R.id.star_rate)
        ImageView imgStarRate;

        @BindView(R.id.txt_tags)
        TextView txtTag;
        @BindView(R.id.img_post_user)
        ImageView imgPostUser;

        @BindView(R.id.btn_favorite_list)
        ImageButton btnFavorite;

        boolean isFavorite;

        public CourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int position);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
