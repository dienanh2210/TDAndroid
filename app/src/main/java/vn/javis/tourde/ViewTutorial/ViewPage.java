package vn.javis.tourde.ViewTutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import vn.javis.tourde.R;
import vn.javis.tourde.ScreenMain.ScreenMain;
import android.app.Activity;
import android.view.Window;

public class ViewPage extends AppCompatActivity {

    ViewPager viewPager;
    private int dotscount;
    private ImageView[] dots;
    LinearLayout sliderDotspanel;
    Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.activity_view_page );
        //Button skip
        btnSkip=findViewById( R.id.skip);
        //button skip on click
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(viewPager.getCurrentItem() != 3){
                    viewPager.setCurrentItem( 3, true );
                } else {
                    Intent intent = new Intent(ViewPage.this,ScreenMain.class);
                     startActivity(intent);
                    Log.d( "aaaaa", "alooooooo" );
            }
            }
        });
        viewPager = (ViewPager) findViewById( R.id.viewpager );
        setupViewPager( viewPager );
        sliderDotspanel = (LinearLayout) findViewById( vn.javis.tourde.R.id.SliderDots);
        MainActivity.ViewPagerAdapter adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "One");
        adapter.addFragment(new TwoFragment(), "Two");
        adapter.addFragment(new ThreeFragment(), "Three");
        adapter.addFragment( new LoginActivity(),"Four" );
        viewPager.setAdapter(adapter);
        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];
        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), vn.javis.tourde.R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), vn.javis.tourde.R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), vn.javis.tourde.R.drawable.active_dot));
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {

        MainActivity.ViewPagerAdapter adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "One");
        adapter.addFragment(new TwoFragment(), "Two");
        adapter.addFragment(new ThreeFragment(), "Three");
        adapter.addFragment( new LoginActivity(),"Four" );
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
    }
}
