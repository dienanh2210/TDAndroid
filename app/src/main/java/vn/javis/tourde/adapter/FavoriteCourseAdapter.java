package vn.javis.tourde.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.fragment.CourseDetailFragment;
import vn.javis.tourde.fragment.CourseDriveFragment;
import vn.javis.tourde.fragment.FragmentTabLayoutRunning;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.model.SaveCourseRunning;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.ClassToJson;
import vn.javis.tourde.utils.Constant;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.SharedPreferencesUtils;
import vn.javis.tourde.view.CircleTransform;

public class FavoriteCourseAdapter extends RecyclerView.Adapter<FavoriteCourseAdapter.FavoriteCourseViewHolder> {

    List<FavoriteCourse> listCourse = new ArrayList<FavoriteCourse>();
    private CourseListActivity activityContext;
    View mView;

    public FavoriteCourseAdapter(List<FavoriteCourse> listCourse, CourseListActivity activityContext) {

        this.listCourse = listCourse;
        this.activityContext = activityContext;
    }

    @Override
    public FavoriteCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.child_favorites_course, parent, false);
        return new FavoriteCourseViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteCourseViewHolder holder, final int position) {
        final FavoriteCourse model = listCourse.get(position);

        holder.txtTitle.setText(model.getTitle());
        holder.txtPostUsername.setText(model.getPostUserName());
        PicassoUtil.getSharedInstance(activityContext).load(model.getTopImage()).resize(0, 500).onlyScaleDown().into(holder.imgShow);
        PicassoUtil.getSharedInstance(activityContext).load(model.getPostUserImage()).resize(0, 100).onlyScaleDown().transform(new CircleTransform()).into(holder.imgPostUser);
        if (holder.isRunning) {
            //holder.txtRunning.setBackground(mView.getResources().getDrawable(R.drawable.icon_bicycle_blue));
        }
        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(position);
                }
            }
        });
        holder.imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(position);
                }
            }
        });

        if (SharedPreferencesUtils.getInstance(activityContext).getLongValue(FragmentTabLayoutRunning.KEY_SHARED_BASETIME) == 0) {
            holder.txtRunning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activityContext.setmCourseID(model.getCourseId());
                    if (SharedPreferencesUtils.getInstance(activityContext).getStringValue("Checkbox") == "") {
                        String content = "運転中の画面操作・注視は、道路交通法又は、道路交通規正法に違反する可能性があります。画面の注視/操作を行う場合は安全な場所に停車し、画面の注視や操作を行ってください。 \n" +
                                "\n" +
                                "道路標識などの交通規制情報が実際の道路状況と異なる場合は、すべて現地の通行規制や標識の指示に従って走行してください";

                        ProcessDialog.showDialogcheckbox(activityContext, "ご利用にあたって", content, new ProcessDialog.OnActionDialogClickOk() {
                            @Override
                            public void onOkClick() {
//                                activityContext.showCourseDrive();
                                activityContext.openPage(CourseDetailFragment.newInstance(true), true, false);

                            }
                        });
                    } else {
//                        activityContext.showCourseDrive();
                        activityContext.openPage(CourseDetailFragment.newInstance(true), true, false);
                    }

                }
            });
        } else {
            holder.txtRunning.setBackground(activityContext.getResources().getDrawable(R.drawable.boder_gray_favorite));
            String savedString = SharedPreferencesUtils.getInstance(activityContext).getStringValue(Constant.SAVED_COURSE_RUNNING);
            final SaveCourseRunning saveCourseRunning = new ClassToJson<SaveCourseRunning>().getClassFromJson(savedString, SaveCourseRunning.class);
            holder.txtRunning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (model.getCourseId() == saveCourseRunning.getCourseID()) {
//                        activityContext.showFragmentTabLayoutRunning();
//                        activityContext.openPage(new FragmentTabLayoutRunning(), CourseDetailFragment.class.getSimpleName(), true, false);
                        activityContext.openPage(CourseDetailFragment.newInstance(true), true, false);
                    }
                    else
                    {
                        ProcessDialog.showDialogConfirm(activityContext, "", "走行中のコースがあります。\n 終了しますか？", new ProcessDialog.OnActionDialogClickOk() {
                            @Override
                            public void onOkClick() {

                                SharedPreferencesUtils.getInstance(activityContext).removeKey(FragmentTabLayoutRunning.KEY_SHARED_BASETIME);
                                SharedPreferencesUtils.getInstance(activityContext).removeKey(Constant.SAVED_COURSE_RUNNING);
                                SharedPreferencesUtils.getInstance(activityContext).removeKey(Constant.KEY_GOAL_PAGE);
                                activityContext.setmCourseID(model.getCourseId());
                                activityContext.showCourseDrive();
                            }
                        });
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listCourse.size();
    }

    public class FavoriteCourseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_show)
        ImageView imgShow;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_post_user)
        ImageView imgPostUser;
        @BindView(R.id.txt_post_username)
        TextView txtPostUsername;
        @BindView(R.id.running)
        TextView txtRunning;

        boolean isRunning;

        public FavoriteCourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int position);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
