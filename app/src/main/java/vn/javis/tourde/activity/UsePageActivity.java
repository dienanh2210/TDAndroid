package vn.javis.tourde.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vn.javis.tourde.R;
import vn.javis.tourde.fragment.usepage.UsePageFireFragment;
import vn.javis.tourde.fragment.usepage.UsePageFourFragment;
import vn.javis.tourde.fragment.usepage.UsePageOneFragment;
import vn.javis.tourde.fragment.usepage.UsePageSevenFragment;
import vn.javis.tourde.fragment.usepage.UsePageSixFragment;
import vn.javis.tourde.fragment.usepage.UsePageThreeFragment;
import vn.javis.tourde.fragment.usepage.UsePageTwoFragment;

import android.view.Window;

public class UsePageActivity extends AppCompatActivity {

    ViewPager viewPager;
    private int dotscount;
    private ImageView[] dots;
    LinearLayout sliderDotspanel;
    MainActivity.ViewPagerAdapter adapter;
    Button bt_usePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        this.supportRequestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_use_page );
        //Button skip

        viewPager = (ViewPager) findViewById( R.id.viewpager );
       bt_usePage=findViewById( R.id.bt_usePage );
        bt_usePage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsePageActivity.this, MenuPageActivity.class);
                startActivity(intent);
            }
        } );
        setupViewPager( viewPager );
        sliderDotspanel = (LinearLayout) findViewById( R.id.SliderDots );
        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];
        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView( this );
            dots[i].setImageDrawable( ContextCompat.getDrawable( getApplicationContext(), vn.javis.tourde.R.drawable.nonactive_dot ) );
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
            params.setMargins( 8, 0, 8, 0 );
            sliderDotspanel.addView( dots[i], params );
        }
        dots[0].setImageDrawable( ContextCompat.getDrawable( getApplicationContext(), R.drawable.active_dot ) );

        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable( ContextCompat.getDrawable( getApplicationContext(), vn.javis.tourde.R.drawable.nonactive_dot ) );
                }
                dots[position].setImageDrawable( ContextCompat.getDrawable( getApplicationContext(), vn.javis.tourde.R.drawable.active_dot ) );

                if (position != 6) {
                    // viewPager.setCurrentItem(7, true);
                        sliderDotspanel.setVisibility( View.VISIBLE );
                        bt_usePage.setVisibility( View.GONE );
                }else {
                    sliderDotspanel.setVisibility( View.GONE );
                    bt_usePage.setVisibility( View.VISIBLE );
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        } );

    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UsePageOneFragment(), "One");
        adapter.addFragment(new UsePageTwoFragment(), "Two");
        adapter.addFragment(new UsePageThreeFragment(), "Three");
        adapter.addFragment(new UsePageFourFragment(), "four");
        adapter.addFragment(new UsePageFireFragment(), "fire");
        adapter.addFragment(new UsePageSixFragment(), "six");
        adapter.addFragment(new UsePageSevenFragment(), "seven");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(7);

    }
}
