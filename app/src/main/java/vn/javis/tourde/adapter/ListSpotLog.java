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
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.view.CircleTransform;

public class ListSpotLog extends RecyclerView.Adapter<ListSpotLog.SpotViewHolder> {
    List<Spot> listSpot = new ArrayList<Spot>();
    CourseListActivity activityContext;
    View mView;

    public ListSpotLog(List<Spot> listSpot, CourseListActivity activityContext) {

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
        final Spot model = listSpot.get(position);

        holder.txt_spotName.setText(model.getTitle());
        holder.spot_id.setText("" + model.getSpotId());
        /*if (model.getTopImage() != null && model.getTopImage() !="")
            Picasso.with(activityContext).load(model.getTopImage()).into(holder.imgShowSuccess);*/

        holder.txt_spotName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(position);
                }
            }
        });
       if(position==listSpot.size()-1)
       {
           holder.img_link_spot.setVisibility(View.GONE);
       }


    }

    @Override
    public int getItemCount() {
        return listSpot.size();
    }

    public class SpotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.success)
        ImageView imgShowSuccess;
        @BindView(R.id.txt_spotName)
        TextView txt_spotName;
        @BindView(R.id.number)
        TextView spot_id;
        @BindView(R.id.img_link_spot)
        ImageView img_link_spot;

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
