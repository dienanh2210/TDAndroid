package vn.javis.tourde.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


    public ListSearchCourseAdapter(Context context, List<Data> dataList, OnClickItem onClickItem) {
        this.context = context;
        this.dataList = dataList;
        this.onClickItem = onClickItem;
    }

    @Override
    public ListSearchCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_list, parent, false );
        ViewHolder viewHolder = new ViewHolder( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListSearchCourseAdapter.ViewHolder holder, int position) {
        final Data data = dataList.get( position );
        holder.tv_header.setText( data.getTitle() );
        holder.rcv_content.setLayoutManager(new GridLayoutManager(context,2));
        // holder.rcv_content.setLayoutManager( new LinearLayoutManager( context ) );
        holder.rcv_content.setAdapter( new ContentSearchCourseAdapter( data.getContent(), new  ContentSearchCourseAdapter.OnClickItem() {
            @Override
            public void onClick(int position, View view) {
                for (Map.Entry<Integer, RecyclerView> e1 : mapRecyclerView.entrySet()) {
                    //to get key
                    e1.getKey();
                    //and to get value
                    for (Map.Entry<Integer, View> eItem : ((ContentSearchCourseAdapter) e1.getValue().getAdapter()).mapItemView.entrySet()) {
                        //to get key
                        eItem.getKey();
                        //and to get value, 1
                        eItem.getValue().setVisibility( View.GONE );
                    }
                }
                if (onClickItem != null) {
                    onClickItem.onClick( data.getContent().get( position ) );
                    Log.i( "Content", data.getContent().get( position ) );
                }
            }
        } ) );
        mapRecyclerView.put( position, holder.rcv_content );
        if (position == 0) {
            //  ((ContentSearchAdapter) holder.rcv_content.getAdapter()).setShowMark( true );
            //  holder.rcv_content.setLayoutManager(new GridLayoutManager(context,2));
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
            super( itemView );
            tv_header = itemView.findViewById( R.id.tv_header );
            rcv_content = itemView.findViewById( R.id.rcv_content );
            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface OnClickItem {
        void onClick(String content);
    }

}

