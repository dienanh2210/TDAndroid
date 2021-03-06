package vn.javis.tourde.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.FavoriteCourseAPI;
import vn.javis.tourde.fragment.LoginFragment;
import vn.javis.tourde.model.Course;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.model.Spot;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.view.CircleTransform;

public class ListSpotDetailCircleAdapter extends RecyclerView.Adapter<ListSpotDetailCircleAdapter.CourseViewHolder> {

    List<Spot> listSpot = new ArrayList<>();
    Context context;
    View mView;
    String htmlText = "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/HiraKakuPro-W3.otf\")}body,* {font-family: MyFont;text-align: justify;line-height: 1.5; margin: 0; padding: 0}</style>";
    int fontSize;

    public ListSpotDetailCircleAdapter(List<Spot> listSpot, Context context) {

        this.listSpot = listSpot;
        this.context = context;
        Resources res = context.getResources();
        fontSize = res.getInteger(R.integer.txtSizeWebView);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mView = inflater.inflate(R.layout.list_spots_detail, parent, false);
        return new CourseViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, final int position) {


        final Spot spot = listSpot.get(position);

        int order = spot.getOrderNumber() + 1;
        holder.txtTitle.setText(spot.getTitle());
        holder.txtIndex.setText(String.valueOf(order));
        holder.txtCatchPhrase.setText(spot.getCatchPhrase());
//        holder.txtIntro.setText(spot.getIntroduction());
        holder.webViewIntro.loadDataWithBaseURL("", htmlText + String.format("<div style=\"font-size:%spx;color: black\">", fontSize) + spot.getIntroduction() + "</div>", "text/html", "utf-8", null);

        holder.txtSpotDistance.setText(spot.getSpotDistance() + "km");
//        if(position<listSpot.size()-1)
//        {
//            final Spot spot1 = listSpot.get(position+1);
//            holder.txtSpotDistance.setText(spot1.getSpotDistance() + "km");
//        }
        String tag = "";
//        if (spot.getTag() != null)
//            tag += "#" + spot.getTag();

        for (int i = 0; i < spot.getListTag().size(); i++) {
            tag += "#" + spot.getListTag().get(i) + "     ";
        }
        Log.i("tagDebugCircleSPot85", spot.getListTag().size() + "-" + tag);
        holder.txtTag.setText(tag);
//
        if (spot.getTopImage() != null && spot.getTopImage() != "")
            PicassoUtil.getSharedInstance(context)
                    .load(spot.getTopImage())
                    .resize(0, 400).onlyScaleDown()
                    .into(holder.imgCourse);
        if (order == listSpot.size())
            holder.lnDistance.setVisibility(View.GONE);

        holder.btnSpotImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickedListener.onItemClick(spot.getSpotId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSpot.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_story_1)
        TextView txtTitle;
        @BindView(R.id.ic_story_1)
        TextView txtIndex;
        @BindView(R.id.story_1_image_des)
        TextView txtCatchPhrase;
        @BindView(R.id.content_story_1)
        TextView txtIntro;
        @BindView(R.id.tag_story_1)
        TextView txtTag;
        @BindView(R.id.txt_spot_distance)
        TextView txtSpotDistance;
        @BindView(R.id.img_story_1)
        ImageView imgCourse;


        @BindView(R.id.ln_distance_between_spot)
        LinearLayout lnDistance;

        @BindView(R.id.btn_spot_images)
        ImageView btnSpotImages;

        @BindView(R.id.webView_content_story)
        WebView webViewIntro;


        boolean isFavorite;

        public CourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int spotId);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
