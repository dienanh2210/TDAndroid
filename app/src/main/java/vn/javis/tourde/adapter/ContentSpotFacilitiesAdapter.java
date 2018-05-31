package vn.javis.tourde.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.R;


public class ContentSpotFacilitiesAdapter extends RecyclerView.Adapter<ContentSpotFacilitiesAdapter.ViewHolder> {

    private List<String> contentList;
    private OnClickItem onClickItem;
    public HashMap<Integer, View> mapItemView = new HashMap<>();
    private boolean isShowMark;

    public ContentSpotFacilitiesAdapter(List<String> contentList, OnClickItem onClickItem) {
        this.contentList = contentList;
        this.onClickItem = onClickItem;
    }

    @Override
    public ContentSpotFacilitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_spotfacilities, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ContentSpotFacilitiesAdapter.ViewHolder holder, final int position) {

        String content = contentList.get(position);
        holder.tv_content.setText(content);
        mapItemView.put(position, holder.imv_mark);
        mapItemView.put(position, holder.imv_mark1);
        holder.imv_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imv_mark1.setBackground(holder.imv_mark.getResources().getDrawable(R.drawable.icon_check_circle));
                holder.imv_mark.setBackground(holder.imv_mark.getResources().getDrawable(R.drawable.icon_check_circle_blue));
                if (onClickItem != null) onClickItem.onClick(position,2);
            }
        });

        holder.imv_mark1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imv_mark.setBackground(holder.imv_mark.getResources().getDrawable(R.drawable.icon_check_circle));
                holder.imv_mark1.setBackground(holder.imv_mark.getResources().getDrawable(R.drawable.icon_check_circle_blue));
                if (onClickItem != null) onClickItem.onClick(position,1);
            }
        });
        if (position == 0 && isShowMark()) holder.imv_mark.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_content;
        ImageButton imv_mark, imv_mark1;
        RelativeLayout rlt_mark;

        public ViewHolder(final View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            imv_mark = itemView.findViewById(R.id.imv_mark);
            imv_mark1 = itemView.findViewById(R.id.imv_mark1);
//            imv_mark2 = itemView.findViewById(R.id.imv_mark2);
//            imv_mark3 = itemView.findViewById(R.id.imv_mark3);
            rlt_mark = itemView.findViewById(R.id.rlt_mark);




        }

        @Override
        public void onClick(View v) {

            mapItemView.put(getAdapterPosition(), imv_mark);
        }
    }

    public interface OnClickItem {
        void onClick(int pos,int value);
    }

    public boolean isShowMark() {
        return isShowMark;
    }

    public void setShowMark(boolean showMark) {
        isShowMark = showMark;
    }
}
