package vn.javis.tourde.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;

public class ListMySpotUploadedImageAdapter extends RecyclerView.Adapter<ListMySpotUploadedImageAdapter.SpotViewHolder> {

    List<String> listImgage = new ArrayList<>();
    Context context;
    View mView;

    public ListMySpotUploadedImageAdapter(List<String> listImgage, Context context) {
        Log.i("asd",""+listImgage.size());
        this.listImgage = listImgage;
        this.context = context;
    }

    @Override
    public SpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.spot_image_single, parent, false);
        return new SpotViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SpotViewHolder holder, final int position) {
        String model = listImgage.get(position);
        if(position ==0) {
            holder.imgSpot.setBackground(mView.getResources().getDrawable(R.drawable.plus_button));
        }
        else {
        //Picasso.with(context).load(imgUrl).into( holder.imgSpot);
            holder.imgSpot.setBackground(mView.getResources().getDrawable(R.drawable.icon_classic));
            }
    }

    @Override
    public int getItemCount() {
        Log.i("asd1",""+listImgage.size());
        return listImgage.size();
    }

    public class SpotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_single_spot)
        ImageView imgSpot;

        public SpotViewHolder(View itemView) {
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
