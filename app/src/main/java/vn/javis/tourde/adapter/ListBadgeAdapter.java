package vn.javis.tourde.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.model.Badge;

public class ListBadgeAdapter extends RecyclerView.Adapter<ListBadgeAdapter.BadgeViewHolder>{

    List<Badge> listBadge = new ArrayList<>();
    Context context;
    View mView;
    public ListBadgeAdapter(List<Badge> listBadge, Context context) {
        this.listBadge = listBadge;
        this.context = context;
    }

    @Override
    public BadgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
         mView = inflater.inflate(R.layout.recycler_badge, parent, false);
        return new BadgeViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {

        Badge model = listBadge.get(position);
        holder.txtBadgeName.setText(model.getBadgeName());
        holder.txtBadgeDateGet.setText(model.getDateGet());

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.getMemoryCache();
        //set image from url
       // imageLoader.getInstance().displayImage(model.getImageUrl(), holder.imgBadge);

        //load local for test
        int idImgBadge = mView.getResources().getIdentifier(model.getImageUrl(), "drawable", context.getPackageName());
        holder.imgBadge.setImageResource(idImgBadge);
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

        public BadgeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
