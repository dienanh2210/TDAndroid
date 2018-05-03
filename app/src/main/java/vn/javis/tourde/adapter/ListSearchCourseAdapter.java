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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.javis.tourde.R;
import vn.javis.tourde.model.Data;

public class ListSearchCourseAdapter extends RecyclerView.Adapter<ListSearchCourseAdapter.ViewHolder> {

    private List<Data> dataList;
    private Context context;
    private HashMap<Integer, RecyclerView> mapRecyclerView = new HashMap<>();

    private OnClickItem onClickItem;
    private HashMap<String, Boolean> mapContent = new HashMap<>();
    private String txtDistance = "";


    private String txtElevation = "";
    private List<String> listCourseType = new ArrayList<>();

    public ListSearchCourseAdapter(Context context, List<Data> dataList, OnClickItem onClickItem) {
        this.context = context;
        this.dataList = dataList;
        this.onClickItem = onClickItem;
    }

    public String getTxtDistance() {
        return txtDistance;
    }

    public String getTxtElevation() {
        return txtElevation;
    }

    public List<String> getListCourseType() {
        return listCourseType;
    }

    @Override
    @NonNull
    public ListSearchCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListSearchCourseAdapter.ViewHolder holder, int position) {
        final Data data = dataList.get(position);
        holder.tv_header.setText(data.getTitle());
        holder.rcv_content.setLayoutManager(new GridLayoutManager(context, 2));
        // holder.rcv_content.setLayoutManager( new LinearLayoutManager( context ) );

        holder.rcv_content.setAdapter(new ContentSearchCourseAdapter(position, data.getContent(), new ContentSearchCourseAdapter.OnClickItem() {
            @Override
            public void onClick(int position, boolean isPick, View view) {


//                for (Map.Entry<Integer, RecyclerView> e1 : mapRecyclerView.entrySet()) {
//                    //to get key
//                    e1.getKey();
//                    //and to get value
//                    for (Map.Entry<Integer, View> eItem : ((ContentSearchCourseAdapter) e1.getValue().getAdapter()).mapItemView.entrySet()) {
//                        //to get key
//                        eItem.getKey();
//                        //and to get value, 1
//                      //  eItem.getValue().setVisibility( View.GONE );
//                    }
//                }
                if (onClickItem != null) {
                    String s = data.getContent().get(position);
                    mapContent.put(s, isPick);
                    if ("距離".equals(data.getTitle())) //area
                    {
                        if (!txtDistance.equals(s)) {
                            txtDistance = s;
                        } else txtDistance = "";
                    }
                    if ("獲得標高".equals(data.getTitle())) //area
                    {
                        if (!txtElevation.equals(s)) {
                            txtElevation = s;
                        } else txtElevation = "";
                    }
                    if ("コース形態".equals(data.getTitle())) //area
                    {
                        if (!listCourseType.contains(s)) {
                            listCourseType.add(s);
                        } else listCourseType.remove(s);
                    }
                }
            }
        }));

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

        public void showTick(int i) {


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
