package vn.javis.tourde.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import vn.javis.tourde.R;


public class ContentRegisterAdapter extends RecyclerView.Adapter<ContentRegisterAdapter.ViewHolder> {

    private List<String> contentList;
    private OnClickItem onClickItem;
    public HashMap<Integer, View> mapItemView = new HashMap<>();
    private boolean isShowMark;
    int positionShow;

    public ContentRegisterAdapter(List<String> contentList, OnClickItem onClickItem) {
        this.contentList = contentList;
        this.onClickItem = onClickItem;
    }

    @Override
    public ContentRegisterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContentRegisterAdapter.ViewHolder holder, int position) {

        String content = contentList.get(position);

        holder.tv_content.setText(content);
//        holder.itemView.setOnClickListener(holder);
//        holder.imv_mark.setTag(position);
        mapItemView.put(position, holder.imv_mark);
        if (position == positionShow && isShowMark()) holder.imv_mark.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_content;
        ImageView imv_mark;
        RelativeLayout rlt_mark;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            imv_mark = itemView.findViewById(R.id.imv_mark);
            rlt_mark = itemView.findViewById(R.id.rlt_mark);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickItem != null) onClickItem.onClick(getAdapterPosition(), v);
            mapItemView.put(getAdapterPosition(), imv_mark);
            imv_mark.setVisibility(View.VISIBLE);
        }
    }

    public interface OnClickItem {
        void onClick(int position, View view);
    }

    public boolean isShowMark() {
        return isShowMark;
    }

    public void setShowMark(boolean showMark, int positionShow) {
        isShowMark = showMark;
        this.positionShow = positionShow;
        Log.i("test age",""+positionShow);
    }
}
