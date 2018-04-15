package vn.javis.tourde.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ViewPagerAdapter;
import vn.javis.tourde.apiservice.ListCourseAPI;
import vn.javis.tourde.model.Course;

public class CourseDetailFragment extends BaseFragment {

    private  int mPosition;

    @BindView(R.id.btn_back_to_list)
    ImageButton btnBackToList;
    @BindView(R.id.btn_share)
    ImageButton btnShare;

    @BindView(R.id.title_detail)
    TextView txtTitle;
    @BindView(R.id.txt_catch_phrase_detail)
    TextView txtCatchPhrase;
    @BindView(R.id.txt_area_detail)
    TextView txtArea;
    @BindView(R.id.txt_distance_detail)
    TextView txtDistance;

    @BindView(R.id.txt_season)
    TextView txtSeason;
    @BindView(R.id.txt_average_slope)
    TextView txtAverageSlope;
    @BindView(R.id.txt_elevation)
    TextView txtElevation;
    @BindView(R.id.txt_course_type)
    TextView txtCourseType;


    @BindView(R.id.txt_review_count_detail)
    TextView txtReviewCount;
    @BindView(R.id.txt_spot_count_detail)
    TextView txtSpotCount;
    @BindView(R.id.txt_post_user_detail)
    TextView txtPostUser;
    @BindView(R.id.img_course_detail)
    ImageView imgCourse;
    @BindView(R.id.star_1_detail)
    ImageView imgStar1;
    @BindView(R.id.star_2_detail)
    ImageView imgStar2;
    @BindView(R.id.star_3_detail)
    ImageView imgStar3;
    @BindView(R.id.star_4_detail)
    ImageView imgStar4;
    @BindView(R.id.star_5_detail)
    ImageView imgStar5;
    @BindView(R.id.txt_tag_detail)
    TextView txtTag;
    @BindView(R.id.img_post_user_detail)
    ImageView imgPostUser;

    @BindView(R.id.course_tabs)
    TabLayout tabLayout;
    @BindView(R.id.course_view_pager)
    ViewPager courseViewpager;
    CourseListActivity activity;

    @Override
    public void onStart() {
        super.onStart();
        showCourseDetail();
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.course_detail, container, false);
    }

    private void setupViewPager(ViewPager viewPager) {


    }
    void showCourseDetail(){
        Course model = ListCourseAPI.getInstance().getCouseByIndex(mPosition);
        txtTitle.setText(model.getTitle());
        txtPostUser.setText(model.getPostUserName());
        txtCatchPhrase.setText(model.getCatchPhrase());
        txtReviewCount.setText(model.getReviewCount());
        txtSpotCount.setText(model.getSpotCount());
        txtArea.setText(model.getArea());
        txtDistance.setText(model.getDistance());
        txtSeason.setText(model.getSeason());
        txtAverageSlope.setText(model.getAverageSlope());
        txtElevation.setText(model.getElevation() +"m");
        txtCourseType.setText(model.getCourseType());
        txtTag.setText("#"+model.getTag());

        Picasso.with(activity).load(model.getTopImage()).into(imgCourse);
        Picasso.with(activity).load(model.getPostUserImage()).into(imgPostUser);

        int rate = Math.round(model.getRatingAverage());

        if (rate == 1) {
            imgStar1.setImageResource(R.drawable.star_yellow);
        } else if (rate == 2) {
            imgStar1.setImageResource(R.drawable.star_yellow);
            imgStar2.setImageResource(R.drawable.star_yellow);
        } else if (rate == 3) {
            imgStar1.setImageResource(R.drawable.star_yellow);
            imgStar3.setImageResource(R.drawable.star_yellow);
            imgStar2.setImageResource(R.drawable.star_yellow);
        } else if (rate == 4) {
            imgStar1.setImageResource(R.drawable.star_yellow);
            imgStar2.setImageResource(R.drawable.star_yellow);
            imgStar3.setImageResource(R.drawable.star_yellow);
            imgStar4.setImageResource(R.drawable.star_yellow);
        } else if (rate == 5) {
            imgStar1.setImageResource(R.drawable.star_yellow);
            imgStar2.setImageResource(R.drawable.star_yellow);
            imgStar3.setImageResource(R.drawable.star_yellow);
            imgStar4.setImageResource(R.drawable.star_yellow);
            imgStar5.setImageResource(R.drawable.star_yellow);
        }
    }

}
