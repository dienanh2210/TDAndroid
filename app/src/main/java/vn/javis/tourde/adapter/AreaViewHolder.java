package vn.javis.tourde.adapter;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import vn.javis.tourde.R;

public class AreaViewHolder extends CheckableChildViewHolder {

    private CheckedTextView childCheckedTextView;

    public AreaViewHolder(View itemView) {
        super(itemView);
        childCheckedTextView = itemView.findViewById(R.id.list_item_area_check);
    }

    @Override
    public Checkable getCheckable() {
        return childCheckedTextView;
    }

    public void setAreaName(String areaName) {
        childCheckedTextView.setText(areaName);
    }

    public void setChecked(boolean checked) {
        childCheckedTextView.setChecked(checked);
        if (checked) {
            childCheckedTextView.setCheckMarkDrawable(R.drawable.checked);
        } else {
            childCheckedTextView.setCheckMarkDrawable(null);
        }

    }

}
