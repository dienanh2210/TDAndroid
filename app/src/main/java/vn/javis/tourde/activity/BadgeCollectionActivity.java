package vn.javis.tourde.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.adapter.ListBadgeAdapter;
import vn.javis.tourde.apiservice.BadgeAPI;
import vn.javis.tourde.model.Badge;

public class BadgeCollectionActivity extends BaseActivity {

    @BindView(R.id.recycler_barge)
    RecyclerView badgeRecycler;
    ListBadgeAdapter listBadgeAdapter;
    @BindView(R.id.btn_archivement_badge)
    TextView btnArchivement;
    @BindView(R.id.btn_spot_badge)
    TextView btnSpotBarge;
    @BindView(R.id.redline_archivement)
    ImageView imgRedLineAchivement;
    @BindView(R.id.redline_spot)
    ImageView imgRedLineSpot;
    @BindView(R.id.btn_close_badge_collection)
    TextView btnClose;
    @BindView(R.id.btn_back_badge)
    TextView btnBack;
    @BindView(R.id.btn_home_footer)
    RelativeLayout btnHome;
    @BindView(R.id.img_badge_collect)
    ImageView imgBadgeBtn;
    @BindView(R.id.txt_badge)
    TextView txtBadgeBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.badge_collection);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        badgeRecycler.setItemAnimator(new DefaultItemAnimator());
        badgeRecycler.setLayoutManager(layoutManager);
        setBadgeData();
        btnArchivement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onArchivementClick();
            }
        });
        btnSpotBarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSpotClick();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMenuPage();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMenuPage();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CourseListActivity.class);
                startActivity(intent);
            }
        });
       // imgBadgeBtn.setBackground(getResources().getDrawable(R.drawable.icon_homeclick));
        txtBadgeBtn.setTextColor(getResources().getColor( R.color.SkyBlue));
        onArchivementClick();
    }

    void gotoMenuPage() {
        Intent intent = new Intent(this, MenuPageActivity.class);
        startActivity(intent);
    }

    void setBadgeData() {
        List<Badge> listBadge = BadgeAPI.getInstance().getListBadge();
        listBadgeAdapter = new ListBadgeAdapter(listBadge, this);
        badgeRecycler.setAdapter(listBadgeAdapter);
    }

    void onArchivementClick() {
        imgRedLineAchivement.setVisibility(View.VISIBLE);
        imgRedLineSpot.setVisibility(View.INVISIBLE);
        btnArchivement.setTextColor(Color.BLACK);
        btnSpotBarge.setTextColor(Color.GRAY);
    }
    void onSpotClick() {
        imgRedLineAchivement.setVisibility(View.INVISIBLE);
        imgRedLineSpot.setVisibility(View.VISIBLE);
        btnSpotBarge.setTextColor(Color.BLACK);
        btnArchivement.setTextColor(Color.GRAY);
    }
}
