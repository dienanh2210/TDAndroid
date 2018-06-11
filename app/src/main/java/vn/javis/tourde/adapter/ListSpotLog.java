package vn.javis.tourde.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;
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
        int spotID = model.getOrderNumber() + 1;
        holder.txt_spotName.setText(model.getTitle());
        holder.spot_id.setText("" + spotID);
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
        if (position == listSpot.size() - 1) {
            holder.img_link_spot.setVisibility(View.GONE);
        }
        if (position == 0) {
            holder.BG_spot.setImageResource(R.drawable.boder_white);
            holder.spot_id.setBackground(activityContext.getResources().getDrawable(R.drawable.blue_circle));
            holder.showInfoSpot.setVisibility(View.VISIBLE);
            //holder.img_link_spot.setVisibility(View.VISIBLE);
            holder.imgShowChecked.setVisibility(View.VISIBLE);
            holder.txt_spotName.setTextColor(activityContext.getResources().getColor(R.color.Black));
            holder.spot_id.setTextColor(activityContext.getResources().getColor(R.color.White));
            holder.img_link_vitual.setVisibility(View.GONE);
        }
        else if(position==1)
        {
            holder.BG_spot.setImageResource(R.drawable.boder_white);
            holder.spot_id.setBackground(activityContext.getResources().getDrawable(R.drawable.blue_circle));
            //holder.showInfoSpot.setVisibility(View.VISIBLE);
            holder.img_link_spot.setVisibility(View.VISIBLE);
            holder.imgShowChecked.setVisibility(View.VISIBLE);
            holder.txt_spotName.setTextColor(activityContext.getResources().getColor(R.color.Black));
            holder.spot_id.setTextColor(activityContext.getResources().getColor(R.color.White));
            holder.img_link_vitual.setVisibility(View.GONE);
        }
        else if(position==2)
        {}
        else {
            holder.spot.setAlpha((float) 0.3);
        }

    }

    @Override
    public int getItemCount() {
        return listSpot.size();
    }

    public class SpotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.spot)
        RelativeLayout spot;
        @BindView(R.id.BG_spot)
        ImageView BG_spot;
        @BindView(R.id.checked)
        ImageView imgShowChecked;
        @BindView(R.id.txt_spotName)
        TextView txt_spotName;
        @BindView(R.id.number)
        TextView spot_id;
        @BindView(R.id.img_link)
        GifImageView img_link_spot;
        @BindView(R.id.img_link_vitual)
        GifImageView img_link_vitual;
        @BindView(R.id.showInfoSpot)
        RelativeLayout showInfoSpot;

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