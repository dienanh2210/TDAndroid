package vn.javis.tourde.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ViewPagerAdapter;

public class FragmentTabLayoutMyCourse  extends BaseFragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View mView;

    @BindView(R.id.btn_badge_collection)
    RelativeLayout btnBadge;
    CourseListActivity mActivity;
    @BindView(R.id.img_home)
    ImageView imgHomeBtn;
    @BindView(R.id.txt_home)
    TextView txtHomeBtn;
    @BindView(R.id.btn_home_footer)
    RelativeLayout btnHome;
    @BindView(R.id.img_mycourse)
    ImageView imgMyCourse;
    @BindView(R.id.txt_mycourse)
    TextView txtMyCourse;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mActivity.showCourseListPage();
            }
        });


        btnBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mActivity.showBadgeCollection();
            }
        });

        imgMyCourse.setBackground(getResources().getDrawable(R.drawable.icon_courseclick));
        txtMyCourse.setTextColor(getResources().getColor(R.color.SkyBlue));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mView = inflater.inflate(R.layout.my_course,container,false);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        tabLayout = (TabLayout) mView.findViewById(R.id.tabs);
        viewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        initTabControl();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void initTabControl() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 1:
                        //Todo tab selected
                        break;
                    case 2:
                        break;

                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new FragmentFavorites(),"お気に入りコース");
        adapter.addFragment(new FragmentRunning(),"走行済コース");

        viewPager.setAdapter(adapter);
    }
}
