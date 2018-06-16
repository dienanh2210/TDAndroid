package vn.javis.tourde.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.model.Data;
import vn.javis.tourde.model.MultiCheckGenre;

public class AreaAdapter extends CheckableChildRecyclerViewAdapter<TitleGroupViewHolder, AreaViewHolder> {

    private List<String> stringChosen = new ArrayList<>();
    public AreaAdapter(List<MultiCheckGenre> groups) {
        super(groups);
    }

    @Override
    public AreaViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area_check, parent, false);
       final AreaViewHolder areaViewHolder =  new AreaViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckedTextView checkable = (CheckedTextView) areaViewHolder.getCheckable();
                areaViewHolder.setChecked(!checkable.isChecked());
                if (checkable.isChecked()) {
                    stringChosen.add(checkable.getText().toString());


                } else {
                    stringChosen.remove(checkable.getText().toString());
                }
            }
        });
        return areaViewHolder;
    }

    @Override
    public void onBindCheckChildViewHolder(AreaViewHolder holder, int position,
                                           CheckedExpandableGroup group, int childIndex) {
        final String area = (String ) group.getItems().get(childIndex);
        holder.setAreaName(area);
        holder.setChecked(false);
    }

    @Override
    public TitleGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);
        return new TitleGroupViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(TitleGroupViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setGenreTitle(group);
    }

    public List<String> getListText() {
        return stringChosen;
    }

}