package vn.javis.tourde.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListCheckInSpot;
import vn.javis.tourde.apiservice.CheckInStampAPI;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.model.SpotData;
import vn.javis.tourde.model.Stamp;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.PicassoUtil;
import vn.javis.tourde.utils.TimeUtil;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CheckPointFragment extends BaseFragment implements ListCheckInSpot.OnItemClickedListener {
    GifImageView gifImgView;
    ImageView imgView;
    @BindView(R.id.txtDesc)
    TextView txtView;

    @BindView(R.id.tv_back_password)
    ImageView tv_back_password;
    @BindView(R.id.txtDesctwo)
    TextView txtDesctwo;
    @BindView(R.id.txtDescthree)
    TextView txtDescthree;

    @BindView(R.id.take_photo)
    Button takePhoto;
    Runnable runnable, runnabletwo;
    Handler handler;
    CourseListActivity mActivity;
    private GifDrawable gifDrawable;
    @BindView(R.id.bt_checkpointleft)
    Button bt_checkpointleft;
    @BindView(R.id.bt_checkpointright)
    Button bt_checkpointright;
    int spotID;
    int courseID;
    String imgUrl;
    String title;
    String time;
    String distance;

    boolean showSecondAnim;
    private OnFragmentInteractionListener listener;
    private String filePath;
    //  TextView tv_back_password;
    private Animation rotation;

    public static CheckPointFragment newInstance(ListCheckInSpot.OnItemClickedListener listener) {
        CheckPointFragment fragment = new CheckPointFragment();
//        fragment.listener = (CheckPointFragment.OnFragmentInteractionListener) listener;
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imgView = view.findViewById(R.id.imgMain);
        spotID = getArguments().getInt(CourseListActivity.SPOT_ID);
        courseID = getArguments().getInt(CourseListActivity.COURSE_DETAIL_ID);

        imgUrl = getArguments().getString(CourseListActivity.STAMP_IMAGE);
        title = getArguments().getString(CourseListActivity.STAMP_TITLE);
        time = getArguments().getString(CourseListActivity.TIME_FINISH);
        distance = getArguments().getString(CourseListActivity.STAMP_DISTANCE);
        showSecondAnim =getArguments().getBoolean(CourseListActivity.STAMP_GAIN,false);

        mActivity = (CourseListActivity) getActivity();

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mActivity.showTakePhoto(spotID,time,distance);
            }
        });

        bt_checkpointleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        //  bt_checkpointleft.setEnabled(true);
                            mActivity.showSpotFacilities();
                    }
                }, 1000);

            }
        });
        bt_checkpointright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms

                        //    bt_checkpointright.setEnabled(true);
                            mActivity.showSpotImages(spotID);
                    }
                }, 1000);

            }
        });
        SpotDataAPI.getSpotData(spotID, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                JSONObject jsonObject = (JSONObject) response;
                Log.i("getSpotData", jsonObject.toString());
                if (jsonObject.has("error"))
                    return;
                SpotData spotData = SpotData.getSpotData(response.toString());
                if (spotData == null)
                    return;
               // final String spotTitle = spotData.getData().getTitle();
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {

if(showSecondAnim){
    if (imgView.getTag() == null) {
        ImageViewAnimatedChange(getApplicationContext(), txtView, "チェックポイント通過！", imgView, R.drawable.icon_check_star);
        //    ImageViewAnimatedChange(mActivity, txtDesctwo, "バッジを獲得！", imgView, imgUrl);
        handler.postDelayed(runnable, 1000);
    } else {
        // ImageViewAnimatedChange(getApplicationContext(),txtView,"バッジを獲得！\n" +
        //   "『 琵 琶 湖 1 周 』",imgView,R.drawable.icon_fishing);
        //  .setVisibility( View.GONE );
        //  ImageViewAnimatedChange( getApplicationContext(), txtDesctwo, "バッジを獲得！", imgView, R.drawable.icon_fishing );
        //  ImageViewAnimatedChange( getApplicationContext(), txtView, "『琵琶湖1周』", imgView, R.drawable.icon_fishing );

        ImageViewAnimatedChange(mActivity, txtDesctwo, "バッジを獲得！", imgView, imgUrl);

        ImageViewAnimatedChange(mActivity, txtView, "『" + title + "』", imgView, imgUrl);
    }
                        }else {

    ImageViewAnimatedChange(getApplicationContext(), txtView, "チェックポイント通過！", imgView, R.drawable.icon_check_star);
}
                    }


                };
                handler.postDelayed(runnable, 1000);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });


    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.check_point_fragment, container, false);

    }

    @Override
    public void onItemClick(int position) {

    }


    public interface OnFragmentInteractionListener extends View.OnClickListener {
        @Override
        void onClick(View v);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static void ImageViewAnimatedChange(Context c, final TextView textView, final String s, final ImageView v, final int new_image) {

        final Animation anim_img = AnimationUtils.loadAnimation(c, R.anim.rotate_up);
        final Animation anim_text = AnimationUtils.loadAnimation(c, R.anim.rotate_up_in);

        v.setImageResource(new_image);
        v.setTag(new_image);
        textView.setText(s);
        v.startAnimation(anim_img);
        textView.startAnimation(anim_text);
    }

    public static void ImageViewAnimatedChange(Context c, final TextView textView, final String s, final ImageView v, String url) {

        if (textView != null && !s.isEmpty() && v != null && url != null) {
            final Animation anim_img = AnimationUtils.loadAnimation(c, R.anim.rotate_up);
            final Animation anim_text = AnimationUtils.loadAnimation(c, R.anim.rotate_up_in);
            if (!url.isEmpty())
                PicassoUtil.getSharedInstance(c).load(url).into(v);
            textView.setText(s);
            v.setTag(url);
            v.startAnimation(anim_img);
            textView.startAnimation(anim_text);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @OnClick({R.id.tv_back_password})
    public void onClickView(View view) {

        switch (view.getId()) {

            case R.id.tv_back_password:

                //mActivity.openPage(new FragmentTabLayoutRunning(), true);
                mActivity.onBackPressed();
                break;
        }
    }

}
