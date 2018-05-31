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
    public void onBindViewHolder(ContentSpotFacilitiesAdapter.ViewHolder holder, int position) {

        String content = contentList.get(position);
        holder.tv_content.setText(content);
        mapItemView.put(position, holder.imv_mark);
        mapItemView.put(position, holder.imv_mark1);
        mapItemView.put(position, holder.imv_mark2);
        mapItemView.put(position, holder.imv_mark3);
        if (position == 0 && isShowMark()) holder.imv_mark.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_content;
        ImageButton imv_mark,imv_mark1,imv_mark2,imv_mark3;
        RelativeLayout rlt_mark;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            imv_mark = itemView.findViewById(R.id.imv_mark);
            imv_mark1 = itemView.findViewById(R.id.imv_mark1);
//            imv_mark2 = itemView.findViewById(R.id.imv_mark2);
//            imv_mark3 = itemView.findViewById(R.id.imv_mark3);
            rlt_mark = itemView.findViewById(R.id.rlt_mark);

            itemView.setOnClickListener(this);

            imv_mark.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imv_mark1.setBackground( imv_mark.getResources().getDrawable(R.drawable.icon_check_circle));
                    imv_mark.setBackground( imv_mark.getResources().getDrawable(R.drawable.icon_check_circle_blue));
                }
            } );

            imv_mark1.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imv_mark.setBackground( imv_mark.getResources().getDrawable(R.drawable.icon_check_circle));
                    imv_mark1.setBackground( imv_mark.getResources().getDrawable(R.drawable.icon_check_circle_blue));
                }
            } );
        }

        @Override
        public void onClick(View v) {
            if (onClickItem != null) onClickItem.onClick(getAdapterPosition(), v);
            mapItemView.put(getAdapterPosition(), imv_mark);
        }
    }

    public interface OnClickItem {
        void onClick(int position, View view);
    }

    public boolean isShowMark() {
        return isShowMark;
    }

    public void setShowMark(boolean showMark) {
        isShowMark = showMark;
    }
}
