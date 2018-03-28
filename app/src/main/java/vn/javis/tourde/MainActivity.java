package vn.javis.tourde;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;

import android.view.Window;

import vn.javis.tourde.Courses.view.CourseList;
import vn.javis.tourde.ViewTutorial.ViewPage;

public class MainActivity extends Activity {

    ImageButton btn_Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.activity_main);
        btn_Next=findViewById(R.id.next );
       btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ViewPage.class);
                startActivity(intent);
            }
        });
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Intent intent = new Intent(MainActivity.this, CourseList.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}




