package vn.javis.tourde.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.model.Badge;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.TimeUtil;
import vn.javis.tourde.view.CircleTransform;

public class ListBadgeAdapter extends RecyclerView.Adapter<ListBadgeAdapter.BadgeViewHolder>{

    private List<Badge> listBadge;
    private Context context;
    private View mView;
    public ListBadgeAdapter(List<Badge> listBadge, Context context) {
        this.listBadge = listBadge;
        this.context = context;
    }
    @Override
    @NonNull
    public BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
         mView = inflater.inflate(R.layout.recycler_badge, parent, false);
        return new BadgeViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {

        Badge model = listBadge.get(position);
        holder.txtBadgeName.setText(model.getTitle());
        holder.txtBadgeDateGet.setText(TimeUtil.formatDayFromString(TimeUtil.DATE_FORMAT,model.getInsertDatetime()));
     //   int idImgBadge = mView.getResources().getIdentifier(model.getImageUrl(), "drawable", context.getPackageName());
      //  holder.imgBadge.setImageResource(idImgBadge);
        PicassoUtil.getSharedInstance(context).load(model.getImage()).resize(0, 300).onlyScaleDown().into(holder.imgBadge);

    }

    @Override
    public int getItemCount() {
        return listBadge.size();
    }

    public class BadgeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_badge_name)
        TextView txtBadgeName;
        @BindView(R.id.txt_date_get)
        TextView txtBadgeDateGet;
        @BindView(R.id.img_badge)
        ImageView imgBadge;

        private BadgeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
