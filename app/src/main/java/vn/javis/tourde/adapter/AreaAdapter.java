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
import vn.javis.tourde.utils.SearchCourseUtils;

public class AreaAdapter extends CheckableChildRecyclerViewAdapter<TitleGroupViewHolder, AreaViewHolder> {

    private List<String> stringChosen;
    private boolean isMultiChoose;
    private int areaValue;
    private SearchCourseUtils mSearchCourseUtils;

    public AreaAdapter(List<MultiCheckGenre> groups, List<String> areaChosen, boolean multiChoose) {
        super(groups);
        stringChosen = areaChosen;
        isMultiChoose = multiChoose;
        mSearchCourseUtils = new SearchCourseUtils();
    }

    @Override
    public AreaViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area_check, parent, false);
       final AreaViewHolder areaViewHolder =  new AreaViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMultiChoose) {
                    CheckedTextView checkable = (CheckedTextView) areaViewHolder.getCheckable();
                    areaViewHolder.setChecked(!checkable.isChecked());
                    if (checkable.isChecked()) {
                        stringChosen.add(checkable.getText().toString());
                    } else {
                        stringChosen.remove(checkable.getText().toString());
                    }
                } else {
                    stringChosen.clear();
                    CheckedTextView checkable = (CheckedTextView) areaViewHolder.getCheckable();
                    areaViewHolder.setChecked(!checkable.isChecked());
                    if (checkable.isChecked()) {
                        String value = checkable.getText().toString();
                        stringChosen.add(value);
                        areaValue = mSearchCourseUtils.getIndexPrefecture(value);
                    } else {
                        areaValue = 1;
                    }
                    notifyDataSetChanged();
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
        if (stringChosen.contains(area)) {
            holder.setChecked(true);
        } else {
            holder.setChecked(false);
        }

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

    public String getAreaContent() {
        if (stringChosen.size() > 0) {
            return stringChosen.get(0);
        } else {
            return "";
        }
    }

    public int getAreaValue() {
        return areaValue;
    }

}