package vn.javis.tourde.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.thoughtbot.expandablecheckrecyclerview.ChildCheckController;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildCheckChangedListener;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildrenCheckStateChangedListener;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.javis.tourde.R;
import vn.javis.tourde.model.MultiCheckGenre;

import static android.view.LayoutInflater.from;

public class MoreConditionAdapter extends MultiTypeExpandableRecyclerViewAdapter<TitleGroupViewHolder, ChildViewHolder>
        implements OnChildCheckChangedListener, OnChildrenCheckStateChangedListener {

    private final int TYPE_TWO_COLUMN = 1;
    private final int TYPE_ONE_COLUMN = 3;
    private List<String> stringChosen = new ArrayList<>();
    private ChildCheckController childCheckController;
    private static final String CHECKED_STATE_MAP = "child_check_controller_checked_state_map";
    private Context mContext;

    public MoreConditionAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        childCheckController = new ChildCheckController(expandableList, this);
        mContext = context;
    }


    @Override
    public List<? extends ExpandableGroup> getGroups() {
        return super.getGroups();
    }


    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ONE_COLUMN:
                View view = from(parent.getContext()).inflate(R.layout.item_area_check, parent, false);
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
            case TYPE_TWO_COLUMN:
                View monthView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_two_column, parent, false);
                return new TowColumnHolder(monthView, mContext);
            default:
                throw new IllegalArgumentException(viewType + " is an Invalid viewType");
        }
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        int viewType = getItemViewType(flatPosition);
        String item = (String) group.getItems().get(childIndex);
        switch (viewType) {
            case TYPE_ONE_COLUMN:
                AreaViewHolder areaViewHolder = (AreaViewHolder) holder;
                areaViewHolder.setAreaName(item);
                areaViewHolder.setChecked(false);
                break;
            case TYPE_TWO_COLUMN:
                final String[] data = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
                TowColumnHolder towColumnHolder = (TowColumnHolder) holder;
                towColumnHolder.recyclerMonth.setAdapter(new ContentSearchCourseAdapter(flatPosition, Arrays.asList(data), new ContentSearchCourseAdapter.OnClickItem() {
                    @Override
                    public void onClick(int position, boolean isPick, View view) {
                        for(String data: data) {
                            for (String text : stringChosen) {
                                if (data.equals(text)) {
                                    stringChosen.remove(text);
                                }
                            }
                        }
                        if (isPick) {
                            stringChosen.add(data[position]);
                        } else {
                            stringChosen.remove(data[position]);
                        }
                    }
                }));
                break;
        }
    }

    @Override
    public void onChildCheckChanged(View view, boolean checked, int flatPos) {
        ExpandableListPosition listPos = expandableList.getUnflattenedPosition(flatPos);
        childCheckController.onChildCheckChanged(checked, listPos);
    }

    @Override
    public void updateChildrenCheckState(int firstChildFlattenedIndex, int numChildren) {
        notifyItemRangeChanged(firstChildFlattenedIndex, numChildren);
    }



    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(CHECKED_STATE_MAP)) {
            return;
        }
        expandableList.groups = savedInstanceState.getParcelableArrayList(CHECKED_STATE_MAP);
        super.onRestoreInstanceState(savedInstanceState);
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

    @Override
    public boolean isChild(int viewType) {
        return viewType == TYPE_TWO_COLUMN || viewType == TYPE_ONE_COLUMN;
    }


    @Override
    public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
        if (((MultiCheckGenre) (group)).isMonth()) {
            return TYPE_TWO_COLUMN;
        } else {
            return TYPE_ONE_COLUMN;
        }
    }

    public List<String> getListText() {
        return stringChosen;
    }

}