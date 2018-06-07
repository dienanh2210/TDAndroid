package vn.javis.tourde.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Checkable;

import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import vn.javis.tourde.R;

public class TowColumnHolder extends CheckableChildViewHolder {

    public RecyclerView recyclerMonth;

    public TowColumnHolder(View itemView, Context context) {
        super(itemView);
        recyclerMonth =  itemView.findViewById(R.id.recycler_month);
        recyclerMonth.setLayoutManager(new GridLayoutManager(context, 2));
    }

    @Override
    public Checkable getCheckable() {
        return null;
    }
}