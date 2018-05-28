package vn.javis.tourde.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.javis.tourde.R;
import vn.javis.tourde.model.Data;

public class ListSearchAdapter extends RecyclerView.Adapter<ListSearchAdapter.ViewHolder> {

    private List<Data> dataList;
    private Context context;
    private HashMap<Integer, RecyclerView> mapRecyclerView = new HashMap<>();
    private OnClickItem onClickItem;
    private HashMap<String, Boolean> mapContent = new HashMap<>();

    public List<String> getListSeason() {
        return listSeason;
    }

    public List<String> getListTag() {
        return listTag;
    }
    private List<String> listSeason = new ArrayList<>();
    private List<String> listTag = new ArrayList<>();

    public ListSearchAdapter(Context context, List<Data> dataList, OnClickItem onClickItem) {
        this.context = context;
        this.dataList = dataList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ListSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSearchAdapter.ViewHolder holder, int position) {
        final Data data = dataList.get(position);
        holder.tv_header.setText(data.getTitle());

        holder.rcv_content.setAdapter(new ContentSearchAdapter(data.getContent(), new ContentSearchAdapter.OnClickItem() {
            @Override
            public void onClick(int position, boolean isPick, View view) {
                for (Map.Entry<Integer, RecyclerView> e1 : mapRecyclerView.entrySet()) {
                    //to get key
                    e1.getKey();
                    //and to get value
                    for (Map.Entry<Integer, View> eItem : ((ContentSearchAdapter) e1.getValue().getAdapter()).mapItemView.entrySet()) {
                        //to get key
                        eItem.getKey();
                        //and to get value, 1
                        //  eItem.getValue().setVisibility( View.GONE );
                    }
                }
                if (onClickItem != null) {
                    String s = data.getContent().get(position);
                    mapContent.put(s, isPick);
                    Log.i("Content", data.getContent().get(position));

                    if ("おすすめの月".equals(data.getTitle())) //area
                    {
                        if (!listSeason.contains(s)) {
                            listSeason.add(s);
                        } else listSeason.remove(s);
                    } else {
                        if (!listTag.contains(s)) {
                            listTag.add(s);
                        } else listTag.remove(s);
                    }
                    Log.i("listSeason", listSeason.toString());
                    Log.i("listTag", listTag.toString());
//                    onClickItem.onClick( data.getContent().get( position ));

                }
            }
        }));
        mapRecyclerView.put(position, holder.rcv_content);
        if (position == 0) {
            ((ContentSearchAdapter) holder.rcv_content.getAdapter()).setShowMark(true);
            holder.rcv_content.setLayoutManager(new GridLayoutManager(context, 2));
        } else {

            holder.rcv_content.setLayoutManager(new LinearLayoutManager(context));
        }
        //0ne ...
        //  if(position == 1) {
        //   holder.rcv_content.setLayoutManager(new LinearLayoutManager(context));
        // }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_header;
        RecyclerView rcv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_header = itemView.findViewById(R.id.tv_header);
            rcv_content = itemView.findViewById(R.id.rcv_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnClickItem {
        void onClick(Map content);
    }

    public HashMap<String, Boolean> getMapContent() {
        return mapContent;
    }

}

