package vn.javis.tourde.adapter;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import vn.javis.tourde.R;
import vn.javis.tourde.model.MultiCheckGenre;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class TitleGroupViewHolder extends GroupViewHolder {

    private TextView groupName;


    public TitleGroupViewHolder(View itemView) {
        super(itemView);
        groupName = itemView.findViewById(R.id.group_name);

    }

    public void setGenreTitle(ExpandableGroup groupName) {
       this.groupName.setText(groupName.getTitle());
    }

    @Override
    public void expand() {

    }

    @Override
    public void collapse() {

    }


}
