package vn.javis.tourde.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.model.SpotCheckIn;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.view.CircleTransform;

public class ListCheckInSpot extends RecyclerView.Adapter<ListCheckInSpot.SpotViewHolder> {
    List<Spot> listSpot = new ArrayList<Spot>();
    CourseListActivity activityContext;
    View mView;

    public ListCheckInSpot(List<Spot> listSpot, CourseListActivity activityContext) {
        this.listSpot.clear();
        this.listSpot = listSpot;
        this.activityContext = activityContext;
    }

    @Override
    public ListCheckInSpot.SpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.child_checkin_spot, parent, false);
        return new ListCheckInSpot.SpotViewHolder(mView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ListCheckInSpot.SpotViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Spot model = listSpot.get(position);
        if (model.getTopImage() != null && !model.getTopImage().equals("")) {
            PicassoUtil.getSharedInstance(activityContext).load(model.getTopImage()).resize(0, 200).transform(new CircleTransform()).into(holder.image_checkin_spot);
        }
        holder.tv_spot_nunber.setText("スポット" + (model.getOrderNumber() + 1));
        holder.tv_spot_name.setText(model.getTitle());
        /*if (model.getTopImage() != null && model.getTopImage() !="")
            Picasso.with(activityContext).load(model.getTopImage()).into(holder.imgShowSuccess);*/

        holder.tv_spot_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(model.getSpotId(),model.getOrderNumber());
                }
            }
        });
        holder.imageCheckinSportCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(model.getSpotId(),model.getOrderNumber());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSpot.size();
    }

    public class SpotViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_checkin_spot)
        ImageView image_checkin_spot;
        @BindView(R.id.image_checkin_spot_cover)
        ImageView imageCheckinSportCover;
        @BindView(R.id.tv_spotNumber)
        TextView tv_spot_nunber;
        @BindView(R.id.tv_spotName)
        TextView tv_spot_name;

        public SpotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface OnItemClickedListener {
        void onItemClick(int id,int order);
    }
    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
