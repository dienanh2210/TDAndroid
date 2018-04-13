package vn.javis.tourde.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import vn.javis.tourde.R;

import vn.javis.tourde.activity.login.MenuPage;
import vn.javis.tourde.database.ListCourseAPI;
import vn.javis.tourde.activity.LoginFragment;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_Next;

    public static MainActivity getInstance() {

        return instance;
    }

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//        openPage(LoginFragment.newInstance());
        instance = this;
        ListCourseAPI api = new ListCourseAPI(this);
        Intent intent = new Intent(this, MenuPage.class);
        startActivity(intent);
        //openPage(new LoginFragment());

    }

    public void loadCourseList() {

      //  Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
       // startActivity(intent);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

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
    public void openPage(Fragment fragment) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tx.addToBackStack(null);
        tx.commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // for twitter
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        loginFragment.onActivityResult(requestCode, resultCode, data);

    }

}





