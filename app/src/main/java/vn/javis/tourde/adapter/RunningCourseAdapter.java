package vn.javis.tourde.adapter;

import android.content.Context;
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
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.view.CircleTransform;

public class RunningCourseAdapter extends RecyclerView.Adapter<RunningCourseAdapter.FavoriteCourseViewHolder> {

    List<FavoriteCourse> listCourse = new ArrayList<FavoriteCourse>();
    Context context;
    View mView;

    public RunningCourseAdapter(List<FavoriteCourse> listCourse, Context context) {

        this.listCourse = listCourse;
        this.context = context;
    }

    @Override
    public FavoriteCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.child_running_course, parent, false);
        return new FavoriteCourseViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteCourseViewHolder holder, final int position) {
        FavoriteCourse model = listCourse.get(position);

        holder.txtTitle.setText(model.getTitle());
        PicassoUtil.getSharedInstance(context).load(model.getTopImage()).into(holder.imgShow);
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
        @BindView(R.id.txt_travel_time)
        TextView txtTravelTime;

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
