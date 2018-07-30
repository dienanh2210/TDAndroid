package vn.javis.tourde.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.model.FavoriteCourse;
import vn.javis.tourde.utils.PicassoUtil;


public class ListMySpotUploadedImageAdapter extends RecyclerView.Adapter<ListMySpotUploadedImageAdapter.SpotViewHolder> {

    List<String> listImgage = new ArrayList<>();
    Context context;
    View mView;
    List<FavoriteCourse> listCourse = new ArrayList<FavoriteCourse>();
    @BindView(R.id.img_single_spot)
    ImageView imgSpot;
    boolean isImageFitToScreen;
    private String fullScreenInd;
    private Uri mImageUri;

    public ListMySpotUploadedImageAdapter(List<String> listImgage, Context context) {
        Log.i( "asd", "" + listImgage.size() );
        this.listImgage = listImgage;
        this.context = context;
    }

    @Override
    public SpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //   mActivity = (CourseListActivity) getActivity();
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        mView = inflater.inflate( R.layout.spot_image_single, parent, false );

        return new SpotViewHolder( mView );
    }

    @Override
    public void onBindViewHolder(@NonNull final SpotViewHolder holder, final int position) {

        String model = listImgage.get( position );
        if (position == 0) {
            holder.imgSpot.setBackground( mView.getResources().getDrawable( R.drawable.select_image ) );
            holder.imgSpot.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickedListener.onItemClick( 0 );

                }
            } );
        } else {
            //  holder.imgSpot.setBackground(mView.getResources().getDrawable(R.drawable.icon_classic));
            if (!TextUtils.isEmpty( model ))
                PicassoUtil.getSharedInstance( context ).load( model ).resize( 0, 200 ).onlyScaleDown().into( holder.imgSpot );

        }
    }

    @Override
    public int getItemCount() {
        Log.i( "asd1", "" + listImgage.size() );
        return listImgage.size();
    }


    public class SpotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_single_spot)
        ImageView imgSpot;


        public SpotViewHolder(View itemView) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            imgSpot = itemView.findViewById( R.id.img_single_spot );

            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            Log.i( "imageUrl:", listImgage.get( getAdapterPosition() ) );
            showImage(listImgage.get(getAdapterPosition()));
        }
    }

    private void showImage(String urlImage) {
        final Dialog dialog = new Dialog( context );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.BLACK ) );
        dialog.getWindow().requestFeature( Window.FEATURE_NO_TITLE );
        dialog.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        dialog.setContentView( R.layout.dialog_custom_layout );
        dialog.getWindow().setLayout( RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        ImageView imageView =  dialog.findViewById( R.id.imageView );
        RequestOptions options = new RequestOptions();
        options.fitCenter();
        Glide.with(context).load(urlImage).apply( options ).into(imageView);
        dialog.show();
    }

    public interface OnItemClickedListener {
        void onItemClick(int position);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
