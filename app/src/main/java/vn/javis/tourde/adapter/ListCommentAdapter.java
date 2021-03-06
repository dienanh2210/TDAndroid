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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.ApiEndpoint;
import vn.javis.tourde.model.Comment;
import vn.javis.tourde.model.Review;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.view.CircleTransform;

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.CommentViewHolder> {

    List<Review> listReview = new ArrayList<Review>();
    Context context;
    View mView;

    public ListCommentAdapter(List<Review> listReview, Context context) {

        this.listReview = listReview;
        this.context = context;
    }

    @Override
    public ListCommentAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.comment_row, parent, false);
        return new ListCommentAdapter.CommentViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCommentAdapter.CommentViewHolder holder, final int position) {
        Review model = listReview.get(position);
        if (model.getImage() != "" && model.getImage() != null)
            holder.txtUserName.setText(model.getNickname());
        holder.txtCommentContent.setText(model.getComment());
        String dateGet = model.getReviewUpdateDatetime();
        if (dateGet != null && dateGet != "") {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy.MM.dd");
            try {
                Date date = dateFormat.parse(dateGet);
                String out = dateFormat2.format(date).toString();
                holder.txtPostDate.setText(out);
            } catch (ParseException e) {
            }
        }
        float rate =model.getRating();
        if (rate >= 1 && rate<1.5)
            holder.imgStarRate.setImageResource(R.drawable.icon_star1);
        else if (rate >= 2 && rate<2.5)
            holder.imgStarRate.setImageResource(R.drawable.icon_star2);
        else if (rate >= 3 && rate<3.5)
            holder.imgStarRate.setImageResource(R.drawable.icon_star3);
        else if (rate >= 4 && rate<4.5)
            holder.imgStarRate.setImageResource(R.drawable.icon_star4);
        else if (rate >= 5)
            holder.imgStarRate.setImageResource(R.drawable.icon_star5);
        else  if (rate >= 1.5 && rate<2)
            holder.imgStarRate.setImageResource(R.drawable.icon_star1_5);
        else  if (rate >= 2.5 && rate<3)
            holder.imgStarRate.setImageResource(R.drawable.icon_star2_5);
        else  if (rate >= 3.5 && rate<4)
            holder.imgStarRate.setImageResource(R.drawable.icon_star3_5);
        else  if (rate >= 4.5 && rate<5)
            holder.imgStarRate.setImageResource(R.drawable.icon_star4_5);
        else  if (rate >= 0.5 && rate<1)
            holder.imgStarRate.setImageResource(R.drawable.icon_star0_5);
        else
            holder.imgStarRate.setImageResource(R.drawable.icon_star0);
        if (model.getImage() != null && model.getImage() != "") {
            String imgUrl = model.getImage();
            PicassoUtil.getSharedInstance(context).load(imgUrl).resize(0, 100).onlyScaleDown().transform(new CircleTransform()).into(holder.imgUserAvata);
        }
    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_user_name)
        TextView txtUserName;
        @BindView(R.id.txt_comment_content)
        TextView txtCommentContent;
        @BindView(R.id.txt_post_date)
        TextView txtPostDate;
        @BindView(R.id.img_avata)
        ImageView imgUserAvata;
        @BindView(R.id.img_star_rating)
        ImageView imgStarRate;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int position);
    }

    private ListCommentAdapter.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickListener(ListCommentAdapter.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}