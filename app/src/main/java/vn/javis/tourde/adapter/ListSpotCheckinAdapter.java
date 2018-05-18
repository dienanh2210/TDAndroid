package vn.javis.tourde.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.model.SpotCheckIn;

public class ListSpotCheckinAdapter extends RecyclerView.Adapter<ListSpotCheckinAdapter.SpotViewHolder>{

    private List<SpotCheckIn> listSpot;
    private Context context;
    private View mView;
    public ListSpotCheckinAdapter(List<SpotCheckIn> listSpot, Context context) {
        this.listSpot = listSpot;
        this.context = context;
    }

    @Override
    @NonNull
    public SpotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
         mView = inflater.inflate(R.layout.child_checkin_spot, parent, false);
        return new SpotViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpotViewHolder holder, final int position) {

        SpotCheckIn model = listSpot.get(position);
        holder.tv_spotNumber.setText(model.getSportNumber());
        holder.tv_spotName.setText(model.getSpotName());
        Picasso.with(context).load(model.getImageUrl()).into(holder.imgSpot);

        //load local for test
        int idImgSpot = mView.getResources().getIdentifier(model.getImageUrl(), "drawable", context.getPackageName());
        holder.imgSpot.setImageResource(idImgSpot);
        holder.btnSpot.setOnClickListener(new View.OnClickListener() {
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
        return listSpot.size();
    }

    public class SpotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_spotNumber)
        TextView tv_spotNumber;
        @BindView(R.id.tv_spotName)
        TextView tv_spotName;
        @BindView(R.id.image_checkin_spot)
        ImageView imgSpot;
        @BindView(R.id.ln_select_checkin_spot)
        LinearLayout btnSpot;
        private SpotViewHolder(View itemView) {
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
