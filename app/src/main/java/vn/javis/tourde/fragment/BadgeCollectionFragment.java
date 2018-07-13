package vn.javis.tourde.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.MenuPageActivity;
import vn.javis.tourde.adapter.ListBadgeAdapter;
import vn.javis.tourde.apiservice.BadgeAPI;
import vn.javis.tourde.model.Badge;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.GsonUtil;
import vn.javis.tourde.utils.LoginUtils;
import vn.javis.tourde.utils.SharedPreferencesUtils;

public class BadgeCollectionFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    CourseListActivity mActivity;
    @BindView(R.id.recycler_barge)
    RecyclerView badgeRecycler;
    ListBadgeAdapter listBadgeAdapter;
    private TabLayout tabLayout;

    @BindView(R.id.btn_home_footer)
    RelativeLayout btnHome;
    @BindView(R.id.img_badge_collect)
    ImageView imgBadgeBtn;
    @BindView(R.id.txt_badge)
    TextView txtBadgeBtn;
    @BindView(R.id.btn_my_course_footer)
    RelativeLayout btnMyCourse;
    String token = SharedPreferencesUtils.getInstance(getContext()).getStringValue(LoginUtils.TOKEN);

    List<Badge> listBadgeGoal = new ArrayList<>();
    List<Badge> listBadgeSpot = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        // testAPI();
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        badgeRecycler.setItemAnimator(new DefaultItemAnimator());
        badgeRecycler.setLayoutManager(layoutManager);
        setBadgeData();
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("達成バッジ"));
        tabLayout.addTab(tabLayout.newTab().setText("ご当地バッジ"));
        tabLayout.setOnTabSelectedListener(this);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showCourseListPage();
            }
        });
        btnMyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showMyCourse();
            }
        });
        imgBadgeBtn.setBackground(getResources().getDrawable(R.drawable.icon_badge_blue));
        txtBadgeBtn.setTextColor(getResources().getColor(R.color.SkyBlue));
        //  onArchivementClick();
    }

    void gotoMenuPage() {
        Intent intent = new Intent(mActivity, MenuPageActivity.class);
        startActivity(intent);
    }

    void setBadgeData() {
//        List<Badge> listBadge = new ArrayList<>();
        BadgeAPI.getBadgeData(token, new ServiceCallback() {
            @Override
            public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                List<Badge> listBadge = GsonUtil.stringToArray(response.toString(), Badge[].class);
                for (Badge b : listBadge) {
                    if (b.getCourseId() != 0) listBadgeGoal.add(b);
                    else listBadgeSpot.add(b);
                }
                listBadgeAdapter = new ListBadgeAdapter(listBadgeGoal, mActivity);
                badgeRecycler.setAdapter(listBadgeAdapter);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.badge_collection, container, false);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            listBadgeAdapter = new ListBadgeAdapter(listBadgeGoal, mActivity);
            badgeRecycler.setAdapter(listBadgeAdapter);
        } else {
            listBadgeAdapter = new ListBadgeAdapter(listBadgeSpot, mActivity);
            badgeRecycler.setAdapter(listBadgeAdapter);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
