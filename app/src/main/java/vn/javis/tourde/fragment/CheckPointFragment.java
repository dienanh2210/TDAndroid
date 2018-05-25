package vn.javis.tourde.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListSpotCheckinAdapter;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CheckPointFragment extends BaseFragment implements ListSpotCheckinAdapter.OnItemClickedListener {
    GifImageView gitImgView;
    ImageView imgView, img;
    TextView txtView;

    @BindView(R.id.tv_back_password)
    ImageView tv_back_password;
    @BindView(R.id.txtDesctwo)
    TextView txtDesctwo;
    @BindView(R.id.txtDescthree)
    TextView txtDescthree;
    Button btnStartDemo;
    @BindView(R.id.take_photo)
    Button takePhoto;
    Runnable runnable, runnabletwo;
    Handler handler;
    CourseListActivity mActivity;

    private OnFragmentInteractionListener listener;
    private String filePath;
    //  TextView tv_back_password;
    private Animation rotation;

    public static CheckPointFragment newInstance(ListSpotCheckinAdapter.OnItemClickedListener listener) {
        CheckPointFragment fragment = new CheckPointFragment();
//        fragment.listener = (CheckPointFragment.OnFragmentInteractionListener) listener;
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imgView = view.findViewById(R.id.imgMain);
        img = view.findViewById(R.id.image_im);
        txtView = view.findViewById(R.id.txtDesc);
        gitImgView = view.findViewById(R.id.gifImageView);
        //  btnStartDemo = (Button)view.findViewById(R.id.btnStartDemo);
        gitImgView.setBackgroundResource(R.drawable.backgound_check);
        mActivity = (CourseListActivity) getActivity();
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showTakePhoto();
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

    public static void ImageViewAnimatedChange(Context c, final TextView textView, final String s, final ImageView v, final int new_image) {
       /* final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setBackgroundResource(new_image);
                v.setTag(new_image);
                textView.setText(s);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }
                });
                v.startAnimation(anim_in);
                textView.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
        textView.startAnimation(anim_out);*/

        final Animation anim_img = AnimationUtils.loadAnimation(c,R.anim.rotate_up);
        final Animation anim_text = AnimationUtils.loadAnimation(c,R.anim.rotate_up_in);

        v.setBackgroundResource(new_image);
        v.setTag(new_image);
        textView.setText(s);
        v.startAnimation(anim_img);
        textView.startAnimation(anim_text);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );


    }

    @Override
    public void onResume() {
        super.onResume();

        runnable = new Runnable() {
            @Override
            public void run() {

                if (imgView.getTag() == null ) {
                    ImageViewAnimatedChange(getApplicationContext(), txtView, "チェックポイント通過！", imgView, R.drawable.icon_check_star);
                    handler.postDelayed( runnable,1000 );

//                    rotation = AnimationUtils.loadAnimation(getApplicationContext(),
//                            R.anim.animscale);
//                    imgView.startAnimation(rotation);

                } else {
                    // ImageViewAnimatedChange(getApplicationContext(),txtView,"バッジを獲得！\n" +
                    //   "『 琵 琶 湖 1 周 』",imgView,R.drawable.icon_fishing);
                    txtView.setVisibility( View.GONE );
                    ImageViewAnimatedChange( getApplicationContext(), txtDesctwo, "バッジを獲得！", imgView, R.drawable.icon_fishing );
                    ImageViewAnimatedChange( getApplicationContext(), txtDescthree, "『琵琶湖1周』", imgView, R.drawable.icon_fishing );

                }
            }


        };
        handler = new Handler();
        handler.postDelayed(runnable, 500);

//        runnabletwo = new Runnable() {
//            @Override
//            public void run() {
//                if (img.getTag() != null && img.getTag().equals(R.drawable.backgound_check)) {
//                    ImageViewAnimatedChange( getApplicationContext(), txtView, "", img, R.drawable.backgound_check );
//
//        RotateAnimation anim = new RotateAnimation(0.0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
//        anim.setInterpolator(new LinearInterpolator());
//        anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
//        anim.setDuration(1000); //Put desired duration per anim cycle here, in milliseconds
//        img.startAnimation(anim);
//                }
//            }
//
//        };
        //  btnStartDemo.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //  public void onClick(View view) {



//        ImageViewAnimatedChange(getApplicationContext(),txtView,"チェックポイント通過！",img,R.drawable.backgound_check);
//        handler = new Handler();
//        handler.postDelayed(runnabletwo,1000);


        //}
        // });

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
