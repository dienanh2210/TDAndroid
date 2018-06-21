package vn.javis.tourde.adapter;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.utils.PicassoUtil;


public class ListMySpotUploadedImageAdapter extends RecyclerView.Adapter<ListMySpotUploadedImageAdapter.SpotViewHolder> {

    List<String> listImgage = new ArrayList<>();
    Context context;
    View mView;
    List<FavoriteCourse> listCourse = new ArrayList<FavoriteCourse>();

    public ListMySpotUploadedImageAdapter(List<String> listImgage, Context context) {
        Log.i("asd", "" + listImgage.size());
        this.listImgage = listImgage;
        this.context = context;
    }

    @Override
    public SpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //   mActivity = (CourseListActivity) getActivity();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.spot_image_single, parent, false);
        return new SpotViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SpotViewHolder holder, final int position) {

        String model = listImgage.get(position);
        if (position == 0) {
            holder.imgSpot.setBackground(mView.getResources().getDrawable(R.drawable.plus_button));
            holder.imgSpot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickedListener.onItemClick(0);
                }
            });
        } else {
            //  holder.imgSpot.setBackground(mView.getResources().getDrawable(R.drawable.icon_classic));
            if (!TextUtils.isEmpty(model))
                PicassoUtil.getSharedInstance(context).load(model).resize(0, 100).onlyScaleDown().into(holder.imgSpot);

        }
    }

    @Override
    public int getItemCount() {
        Log.i("asd1", "" + listImgage.size());
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
