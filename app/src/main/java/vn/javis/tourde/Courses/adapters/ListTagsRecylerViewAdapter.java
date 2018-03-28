package vn.javis.tourde.Courses.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.javis.tourde.R;

/**
 * Created by admin on 3/26/2018.
 */

public class ListTagsRecylerViewAdapter extends RecyclerView.Adapter<ListTagsRecylerViewAdapter.ViewHolder>
{
    private List<String> listData = new ArrayList<String>();
    Activity context;
    public ListTagsRecylerViewAdapter(Activity context, List<String> listData){
        this.context =context;
        this.listData =listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.sources_list_tags, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTag.setText(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtTag;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTag =(TextView)itemView.findViewById(R.id.btn_tag_in_sources);
        }
    }
}
