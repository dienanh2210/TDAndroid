package vn.javis.tourde.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.fragment.FragmentLog;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.view.CircleTransform;

public class
ListCourseAdapter extends RecyclerView.Adapter<ListCourseAdapter.CourseViewHolder> {

    List<Course> listCourse = new ArrayList<Course>();
    Context context;
    View mView;

    public ListCourseAdapter(List<Course> listCourse, Context context) {

        this.listCourse = listCourse;
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.course_view_row, parent, false);
        return new CourseViewHolder(mView);
    }

    String token = LoginFragment.getmUserToken();

    @Override
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, final int position) {
        final Course model = listCourse.get(position);
        Log.i("response", position + "");
        holder.txtTitle.setText(model.getTitle());
        holder.txtArea.setText(model.getArea());

        holder.txtTag.setText("# " + model.getTag());
        holder.txtDistance.setText(model.getDistance() + "km");
        holder.txtCatchPhrase.setText(model.getCatchPhrase());
        holder.txtReviewCount.setText(model.getReviewCount());
        holder.txtSpotCount.setText(model.getSpotCount());
        holder.txtPostUser.setText(model.getPostUserName());
        PicassoUtil.getSharedInstance(context).load(model.getTopImage()).resize(0, 400).onlyScaleDown().into(holder.imgCourse);
        PicassoUtil.getSharedInstance(context).load(model.getPostUserImage()).resize(0, 50).onlyScaleDown().transform(new CircleTransform()).into(holder.imgPostUser);
        List<String> listTag = model.getListTag();

        if (listTag !=null && listTag.size() > 0) {
            String s="";
            if (model.getTag() !=null && model.getTag() !="")
                s = "#" + model.getTag() + " ";
            for (int i = 0; i < listTag.size(); i++) {
                s += "#" + listTag.get(i) + " ";
            }
            holder.txtTag.setText(s);
        }
        holder.isFavorite = false;
        FavoriteCourseAPI.getListFavoriteCourse(LoginFragment.getmUserToken(), new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                Log.i("response", response.toString());
                List<FavoriteCourse> listFavorCourse = FavoriteCourseAPI.getFavorites(response);
                for (int i = 0; i < listFavorCourse.size(); i++) {
                    Log.i("response", listFavorCourse.get(i).getAccountId() + "" + model.getCourseId());
                    if (listFavorCourse.get(i).getCourseId() == Integer.valueOf(model.getCourseId())) {
                        holder.isFavorite = true;
                        break;
                    }
                }
                if (holder.isFavorite) {
                    holder.btnFavorite.setBackground(mView.getResources().getDrawable(R.drawable.icon_bicycle_red));
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
        String s = model.getRatingAverage();
        int rate = Math.round(Float.valueOf(s));

        if (rate == 1) {
            holder.imgStarRate.setImageResource(R.drawable.icon_star1);
        } else if (rate == 2) {
            holder.imgStarRate.setImageResource(R.drawable.icon_star2);
        } else if (rate == 3) {
            holder.imgStarRate.setImageResource(R.drawable.icon_star3);
        } else if (rate == 4) {
            holder.imgStarRate.setImageResource(R.drawable.icon_star4);
        } else if (rate == 5) {
            holder.imgStarRate.setImageResource(R.drawable.icon_star5);
        }

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(model.getCourseId());
                }
            }
        });
        holder.txtCatchPhrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(model.getCourseId());
                }
            }
        });
        holder.imgCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(model.getCourseId());
                }
            }
        });
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (token != "") {
                    holder.isFavorite = !holder.isFavorite;
                    String token = LoginFragment.getmUserToken();
                    int course_id = Integer.valueOf(model.getCourseId());
                    if (holder.isFavorite) {
                        FavoriteCourseAPI.insertFavoriteCourse(token, course_id, new ServiceCallback() {
                            @Override
                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                Log.i("insert favorite", response.toString());

                                JSONObject jsonObject = (JSONObject) response;
                                if (jsonObject.has("success")) {
                                    holder.btnFavorite.setBackground(view.getResources().getDrawable(R.drawable.icon_bicycle_red));
                                } else {
                                    holder.isFavorite = !holder.isFavorite;
                                    Log.i("is: ", "false");
                                }
                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        });
                    } else {
                        FavoriteCourseAPI.deleteFavoriteCourse(token, course_id, new ServiceCallback() {
                            @Override
                            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                Log.i("delete favorite", response.toString());
                                JSONObject jsonObject = (JSONObject) response;
                                if (jsonObject.has("success")) {
                                    holder.btnFavorite.setBackground(view.getResources().getDrawable(R.drawable.icon_bicycle_gray));

                                } else {
                                    holder.isFavorite = !holder.isFavorite;
                                }

                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        });
                    }
                } else {
                    ProcessDialog.showDialogLogin(context, "", "この機能を利用するにはログインをお願いいたします", new ProcessDialog.OnActionDialogClickOk() {
                        @Override
                        public void onOkClick() {
                            if (onItemClickedListener != null) {
                                onItemClickedListener.openPage(new LoginFragment());
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listCourse != null)
            return listCourse.size();
        else return 0;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_course_title)
        TextView txtTitle;
        @BindView(R.id.txt_catch_phrase)
        TextView txtCatchPhrase;
        @BindView(R.id.txt_course_area)
        TextView txtArea;
        @BindView(R.id.txt_distance)
        TextView txtDistance;
        @BindView(R.id.txt_review_count)
        TextView txtReviewCount;
        @BindView(R.id.txt_spot_count)
        TextView txtSpotCount;
        @BindView(R.id.txt_post_user)
        TextView txtPostUser;
        @BindView(R.id.img_course)
        ImageView imgCourse;
        @BindView(R.id.star_rate)
        ImageView imgStarRate;

        @BindView(R.id.txt_tags)
        TextView txtTag;
        @BindView(R.id.img_post_user)
        ImageView imgPostUser;

        @BindView(R.id.btn_favorite_list)
        ImageButton btnFavorite;

        boolean isFavorite;

        public CourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int position);

        void openPage(android.support.v4.app.Fragment fragment);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
