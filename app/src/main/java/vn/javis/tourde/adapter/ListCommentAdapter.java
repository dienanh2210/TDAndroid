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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.model.Comment;
import vn.javis.tourde.view.CircleTransform;

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.CommentViewHolder> {

    List<Comment> listComments = new ArrayList<Comment>();
    Context context;
    View mView;

    public ListCommentAdapter(List<Comment> listComment, Context context) {

        this.listComments = listComment;
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
        Comment model = listComments.get(position);
        holder.txtUserName.setText(model.getToken());
        holder.txtCommentContent.setText(model.getComment());
      //  holder.txtPostDate.setText(model.getPostDate().toString());
        holder.txtPostDate.setText("2018.01.02");
        int rate = model.getRating();
        if (rate == 1)
            holder.imgStarRate.setImageResource(R.drawable.icon_star1);
        else if (rate == 2)
            holder.imgStarRate.setImageResource(R.drawable.icon_star2);
        else if (rate == 3)
            holder.imgStarRate.setImageResource(R.drawable.icon_star3);
        else if (rate == 4)
            holder.imgStarRate.setImageResource(R.drawable.icon_star4);
        else if (rate == 5)
            holder.imgStarRate.setImageResource(R.drawable.icon_star5);
        //Picasso.with(context).load(model.getUserAvatar()).into(holder.imgUserAvata);
    }

    @Override
    public int getItemCount() {
        return listComments.size();
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