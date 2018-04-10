package vn.javis.tourde.activity.UserRegistration.RegisterFragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.javis.tourde.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Data> dataList;
    private Context context;
    private HashMap<Integer, RecyclerView> mapRecyclerView = new HashMap<>();

    public ListAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Data data = dataList.get(position);
        holder.tv_header.setText(data.getTitle());
        holder.rcv_content.setLayoutManager(new LinearLayoutManager(context));
        holder.rcv_content.setAdapter(new ContentAdapter(data.getContent(), new ContentAdapter.OnClickItem() {
            @Override
            public void onClick(int position, View view) {
                for (Map.Entry<Integer, RecyclerView> e1 : mapRecyclerView.entrySet()) {
                    //to get key
                    e1.getKey();
                    //and to get value
                    for (Map.Entry<Integer, View> eItem : ((ContentAdapter) e1.getValue().getAdapter()).mapItemView.entrySet()) {
                        //to get key
                        eItem.getKey();
                        //and to get value
                        eItem.getValue().setVisibility(View.GONE);
                    }
                }
            }
        }));
        mapRecyclerView.put(position, holder.rcv_content);
        if (position == 0) {
            ((ContentAdapter) holder.rcv_content.getAdapter()).setShowMark(true);
        }
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
}

