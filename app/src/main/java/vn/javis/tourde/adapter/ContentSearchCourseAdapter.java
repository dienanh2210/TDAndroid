package vn.javis.tourde.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.javis.tourde.R;


public class ContentSearchCourseAdapter extends RecyclerView.Adapter<ContentSearchCourseAdapter.ViewHolder> {

    private List<String> contentList;
    private OnClickItem onClickItem;
    private HashMap<Integer, View> mapItemView = new HashMap<>();
    private List<String> mStringChosen;
    int mCheckedPosition = -1;
    private boolean singleClicked;
    View view;

    ContentSearchCourseAdapter(List<String> contentList, List<String> stringChosen, OnClickItem onClickItem) {
        this.contentList = contentList;
        this.onClickItem = onClickItem;
        mStringChosen = stringChosen;
    }

    ContentSearchCourseAdapter(List<String> contentList, List<String> stringChosen, boolean singleClicked, OnClickItem onClickItem) {
        this.contentList = contentList;
        this.onClickItem = onClickItem;
        mStringChosen = stringChosen;
        this.singleClicked = singleClicked;
    }

    @NonNull
    @Override
    public ContentSearchCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_content, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ContentSearchCourseAdapter.ViewHolder holder, int position) {
        String content = contentList.get(position);

        holder.tv_content.setText(content);
        mapItemView.put(position, holder.imv_mark);
        Log.i("SearchCourseAdapter58", mStringChosen.toString() + "-" + content);
        if (!TextUtils.isEmpty(content)) {

            if (singleClicked)
            {
                if (mStringChosen.contains(content) || position == mCheckedPosition) {
                    holder.imv_mark.setVisibility(View.VISIBLE);
                    if(!holder.isClicked) {
                        holder.isClicked = true;
                        if (onClickItem != null) onClickItem.onClick(position, true, view);
                    }
                } else {
                    // holder.onClick(view);
                    holder.isClicked = false;
                    holder.imv_mark.setVisibility(View.INVISIBLE);

                }
            } else {
                if (mStringChosen.contains(content)) {
                    holder.imv_mark.setVisibility(View.VISIBLE);
                    holder.isClicked = true;
                    if (onClickItem != null) onClickItem.onClick(position, true, view);
                } else {
                    // holder.onClick(view);
                    holder.imv_mark.setVisibility(View.INVISIBLE);

                }
            }
        }
        else holder.nullContent=true;
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_content;
        ImageView imv_mark;
        RelativeLayout rlt_mark;
        boolean isClicked;
        boolean nullContent;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            imv_mark = itemView.findViewById(R.id.imv_mark);
            rlt_mark = itemView.findViewById(R.id.rlt_mark);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(nullContent) return;
            isClicked = !isClicked;
            if (!isClicked) {
                imv_mark.setVisibility(View.INVISIBLE);
                mCheckedPosition=-1;
                if (onClickItem != null) onClickItem.onClick(getAdapterPosition(), false, v);

            }else  {
                if (onClickItem != null) onClickItem.onClick(getAdapterPosition(), true, v);
                imv_mark.setVisibility(View.VISIBLE);
                mCheckedPosition = getAdapterPosition();

            }
            if(singleClicked) notifyDataSetChanged();
        }
    }

    public interface OnClickItem {
        void onClick(int position, boolean isPick, View view);
    }
}
