package vn.javis.tourde.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import pl.droidsonroids.gif.GifImageView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.LoginSNSActivity;
import vn.javis.tourde.activity.MenuPageActivity;
import vn.javis.tourde.utils.ProcessDialog;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CheckPointFragment extends BaseFragment {
    GifImageView gitImgView;
    ImageView imgView,img;
    TextView txtView;
    Button btnStartDemo;
    Runnable runnable,runnabletwo;
    Handler handler;
   // LoginSNSActivity mAcitivity;
    private OnFragmentInteractionListener listener;
  //  TextView tv_back_password;


    public static CheckPointFragment newInstance(View.OnClickListener listener) {
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

//        ImageView vie = view.findViewById(R.id.image_im);
//        RotateAnimation anim = new RotateAnimation(0.0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
//        anim.setInterpolator(new LinearInterpolator());
//        anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
//        anim.setDuration(10000); //Put desired duration per anim cycle here, in milliseconds
//        vie.startAnimation(anim);


    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.check_point_fragment, container, false);

    }


    public interface OnFragmentInteractionListener extends View.OnClickListener {
        @Override
        void onClick(View v);
    }
    public static void ImageViewAnimatedChange(Context c, final TextView textView,final String s, final ImageView v, final int new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {   v.setBackgroundResource(new_image);
                v.setTag(new_image);
                textView.setText(s);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {

                    }
                });
                v.startAnimation(anim_in);
                textView.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
        textView.startAnimation(anim_out);

    }

    @Override
    public void onResume() {
        super.onResume();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (imgView.getTag() != null && imgView.getTag().equals(R.drawable.icon_fishing))
                {
                    ImageViewAnimatedChange(getApplicationContext(),txtView,"チェックポイント通過！",imgView,R.drawable.icon_check_star);

                }else
                    ImageViewAnimatedChange(getApplicationContext(),txtView,"Image 2",imgView,R.drawable.icon_fishing);
            }

        };
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
                ImageViewAnimatedChange(getApplicationContext(),txtView,"チェックポイント通過！",imgView,R.drawable.icon_check_star);
                handler = new Handler();
                handler.postDelayed(runnable,1000);

//        ImageViewAnimatedChange(getApplicationContext(),txtView,"チェックポイント通過！",img,R.drawable.backgound_check);
//        handler = new Handler();
//        handler.postDelayed(runnabletwo,1000);


            //}
       // });

    }

}
