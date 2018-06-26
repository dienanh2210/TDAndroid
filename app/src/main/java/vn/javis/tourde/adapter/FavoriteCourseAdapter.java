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
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.view.CircleTransform;

public class FavoriteCourseAdapter extends RecyclerView.Adapter<FavoriteCourseAdapter.FavoriteCourseViewHolder> {

    List<FavoriteCourse> listCourse = new ArrayList<FavoriteCourse>();
    CourseListActivity activityContext;
    View mView;

    public FavoriteCourseAdapter(List<FavoriteCourse> listCourse, CourseListActivity activityContext) {

        this.listCourse = listCourse;
        this.activityContext = activityContext;
    }

    @Override
    public FavoriteCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.child_favorites_course, parent, false);
        return new FavoriteCourseViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteCourseViewHolder holder, final int position) {
        final FavoriteCourse model = listCourse.get(position);

        holder.txtTitle.setText(model.getTitle());
        holder.txtPostUsername.setText(model.getPostUserName());
        PicassoUtil.getSharedInstance(activityContext).load(model.getTopImage()).resize(0, 500).onlyScaleDown().into(holder.imgShow);
        PicassoUtil.getSharedInstance(activityContext).load(model.getPostUserImage()).resize(0, 100).onlyScaleDown().transform(new CircleTransform()).into(holder.imgPostUser);
        if (holder.isRunning) {
            //holder.txtRunning.setBackground(mView.getResources().getDrawable(R.drawable.icon_bicycle_blue));
        }
        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(position);
                }
            }
        });
        holder.imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(position);
                }
            }
        });
        holder.txtRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityContext.setmCourseID(model.getCourseId());
               activityContext.showCourseDrive();
            }
        });
       /* holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
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
        });*/

    }

    @Override
    public int getItemCount() {
        return listCourse.size();
    }

    public class FavoriteCourseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_show)
        ImageView imgShow;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_post_user)
        ImageView imgPostUser;
        @BindView(R.id.txt_post_username)
        TextView txtPostUsername;
        @BindView(R.id.running)
        TextView txtRunning;

       /* @BindView(R.id.txt_spot_count)
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
        ImageButton btnFavorite;*/

        boolean isRunning;

        public FavoriteCourseViewHolder(View itemView) {
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
