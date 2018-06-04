package vn.javis.tourde.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.view.CircleTransform;

public class ListSpotLog extends RecyclerView.Adapter<ListSpotLog.SpotViewHolder>{
    List<FavoriteCourse> listSpot = new ArrayList<FavoriteCourse>();
    CourseListActivity activityContext;
    View mView;

    public ListSpotLog(List<FavoriteCourse> listSpot, CourseListActivity activityContext) {

        this.listSpot = listSpot;
        this.activityContext = activityContext;
    }

    @Override
    public ListSpotLog.SpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.log_fragment_finish, parent, false);
        return new ListSpotLog.SpotViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSpotLog.SpotViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final FavoriteCourse model = listSpot.get(position);

        holder.txtTitle.setText(model.getTitle());
        holder.txtPostUsername.setText(model.getPostUserName());
        Picasso.with(activityContext).load(model.getTopImage()).into(holder.imgShow);
        Picasso.with(activityContext).load(model.getPostUserImage()).transform(new CircleTransform()).into(holder.imgPostUser);
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


    }

    @Override
    public int getItemCount() {
        return listSpot.size();
    }

    public class SpotViewHolder extends RecyclerView.ViewHolder {

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

        boolean isRunning;

        public SpotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int position);
    }

    private FavoriteCourseAdapter.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickListener(FavoriteCourseAdapter.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
