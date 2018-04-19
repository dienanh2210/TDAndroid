package vn.javis.tourde.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.HashMap;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.MenuPageActivity;
import vn.javis.tourde.activity.RegisterActivity;
import vn.javis.tourde.activity.SearchActivity;
import vn.javis.tourde.activity.SearchCourseActivity;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;

public class SearchCourseFragment extends Fragment implements View.OnClickListener, ServiceCallback, PrefectureSearchFragment.OnFragmentInteractionListener, PrefectureOneFragment.OnFragmentInteractionListener {

    ImageView im_select_area, im_more_searching;
    private SearchCourseActivity activity;
    String prefecture = "エリアを選択";
    String prefecturetext = "こだわり条件を指定";

    TextView tv_prefecture, tv_searchtwo, tv_close;
    LinearLayout ln_prefecture_one;

    Button bt_search_course;

    private ImageView imv_mark, imv_mark_one, imv_mark_two, imv_mark_three, imv_mark_four, imv_mark_fire, imv_mark_six, imv_mark_seven, imv_mark_eight, imv_mark_night;
    private RelativeLayout twenty_km, fifty_km, one_hundred_km, over_one_hundred_km;
    private RelativeLayout over_elevator, less_elevator_layout, course_type_one, course_type_two, course_type_three, course_type_four;

    private String areaSelect, distanceSelect, elevatorSelect, courseTypeSelect, moreCondition, freeWord;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SearchCourseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.search_course_fragment, container, false);
        im_select_area = view.findViewById(R.id.im_select_area);
        im_more_searching = view.findViewById(R.id.im_more_searching);
        tv_prefecture = view.findViewById(R.id.tv_prefecture);
        tv_prefecture.setText(prefecture);
        tv_searchtwo = view.findViewById(R.id.tv_searchtwo);
        tv_searchtwo.setText(prefecturetext);
        ln_prefecture_one = view.findViewById(R.id.ln_prefecture_one);

        bt_search_course = view.findViewById(R.id.bt_search_course);
        bt_search_course.setOnClickListener(this);

        imv_mark = view.findViewById(R.id.imv_mark);
        imv_mark_one = view.findViewById(R.id.imv_mark_one);
        imv_mark_two = view.findViewById(R.id.imv_mark_two);
        imv_mark_three = view.findViewById(R.id.imv_mark_three);
        imv_mark_four = view.findViewById(R.id.imv_mark_four);
        imv_mark_fire = view.findViewById(R.id.imv_mark_fire);
        imv_mark_six = view.findViewById(R.id.imv_mark_six);
        imv_mark_seven = view.findViewById(R.id.imv_mark_seven);
        imv_mark_eight = view.findViewById(R.id.imv_mark_eight);
        imv_mark_night = view.findViewById(R.id.imv_mark_night);

        twenty_km = view.findViewById(R.id.twenty_km);
        fifty_km = view.findViewById(R.id.fifty_km);
        one_hundred_km = view.findViewById(R.id.one_hundred_km);
        over_one_hundred_km = view.findViewById(R.id.over_one_hundred_km);

        over_elevator = view.findViewById(R.id.over_elevator);
        less_elevator_layout = view.findViewById(R.id.less_elevator_layout);
        course_type_one = view.findViewById(R.id.course_type_one);
        course_type_two = view.findViewById(R.id.course_type_two);
        course_type_three = view.findViewById(R.id.course_type_three);
        course_type_four = view.findViewById(R.id.course_type_four);

        tv_close = view.findViewById(R.id.tv_close);

        im_select_area.setOnClickListener(this);
        im_more_searching.setOnClickListener(this);
        twenty_km.setOnClickListener(this);
        fifty_km.setOnClickListener(this);
        one_hundred_km.setOnClickListener(this);
        over_one_hundred_km.setOnClickListener(this);

        over_elevator.setOnClickListener(this);
        less_elevator_layout.setOnClickListener(this);
        course_type_one.setOnClickListener(this);
        course_type_two.setOnClickListener(this);
        course_type_three.setOnClickListener(this);
        course_type_four.setOnClickListener(this);
        tv_close.setOnClickListener(this);
        return view;
    }

    private boolean chooseGender(boolean isRun) {
        if (isRun) {
            imv_mark.setVisibility(View.VISIBLE);
            imv_mark_one.setVisibility(View.GONE);
            imv_mark_two.setVisibility(View.GONE);
            imv_mark_three.setVisibility(View.GONE);
        } else {
            imv_mark.setVisibility(View.GONE);
            imv_mark_one.setVisibility(View.VISIBLE);
            imv_mark_two.setVisibility(View.GONE);
            imv_mark_three.setVisibility(View.GONE);
        }
        return isRun;
    }

    private boolean chooseGendertwo(boolean isRun) {
        if (isRun) {
            imv_mark_two.setVisibility(View.VISIBLE);
            imv_mark_three.setVisibility(View.GONE);
            imv_mark.setVisibility(View.GONE);
            imv_mark_one.setVisibility(View.GONE);

        } else {
            imv_mark_two.setVisibility(View.GONE);
            imv_mark_three.setVisibility(View.VISIBLE);
            imv_mark.setVisibility(View.GONE);
            imv_mark_one.setVisibility(View.GONE);
        }
        return isRun;
    }

    private boolean chooseGenderthree(boolean isRun) {
        if (isRun) {
            imv_mark_four.setVisibility(View.VISIBLE);
            imv_mark_fire.setVisibility(View.GONE);
        } else {
            imv_mark_four.setVisibility(View.GONE);
            imv_mark_fire.setVisibility(View.VISIBLE);
        }
        return isRun;
    }

    private boolean chooseGenderfour(boolean isRun) {
        if (isRun) {
            imv_mark_six.setVisibility(View.VISIBLE);
            imv_mark_seven.setVisibility(View.GONE);
            imv_mark_eight.setVisibility(View.GONE);
            imv_mark_night.setVisibility(View.GONE);
        } else {
            imv_mark_six.setVisibility(View.GONE);
            imv_mark_seven.setVisibility(View.VISIBLE);
            imv_mark_eight.setVisibility(View.GONE);
            imv_mark_night.setVisibility(View.GONE);
        }
        return isRun;
    }

    private boolean chooseGenderfire(boolean isRun) {
        if (isRun) {
            imv_mark_eight.setVisibility(View.VISIBLE);
            imv_mark_night.setVisibility(View.GONE);
            imv_mark_six.setVisibility(View.GONE);
            imv_mark_seven.setVisibility(View.GONE);

        } else {
            imv_mark_eight.setVisibility(View.GONE);
            imv_mark_night.setVisibility(View.VISIBLE);
            imv_mark_six.setVisibility(View.GONE);
            imv_mark_seven.setVisibility(View.GONE);

        }
        return isRun;
    }

    @Override
    public void onClick(View v) {

        boolean gender = false;
        switch (v.getId()) {
            case R.id.im_select_area:
                activity.openPage(PrefectureOneFragment.newInstance(this), true);
                break;
            case R.id.im_more_searching:
                activity.openPage(PrefectureSearchFragment.newInstance(this), true);
                break;
            case R.id.twenty_km:
                gender = chooseGender(true);
                break;
            case R.id.fifty_km:
                gender = chooseGender(false);
                break;
            case R.id.one_hundred_km:
                gender = chooseGendertwo(true);
                break;
            case R.id.over_one_hundred_km:
                gender = chooseGendertwo(false);
                break;
            case R.id.over_elevator:
                gender = chooseGenderthree(true);
                break;
            case R.id.less_elevator_layout:
                gender = chooseGenderthree(false);
                break;
            case R.id.course_type_one:
                gender = chooseGenderfour(true);
                break;
            case R.id.course_type_two:
                gender = chooseGenderfour(false);
                break;
            case R.id.course_type_three:
                gender = chooseGenderfire(true);
                break;
            case R.id.course_type_four:
                gender = chooseGenderfire(false);
                break;
            case R.id.tv_close:
                Intent intent = new Intent(getActivity(), CourseListActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_search_course:
                Log.d("", tv_prefecture.getContext().toString());

                break;
        }
    }

    @Override
    public void onSuccess(ServiceResult resultCode, Object response) {

    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onFragmentInteraction(String content) {
        prefecture = content;
        tv_prefecture.setText(content);

    }

    @Override
    public void onFragment(String content) {
        prefecturetext = content;
        tv_searchtwo.setText(content);
    }

    void onSearchButtonClick() {
        HashMap<String, String> searchInfo = new HashMap<>();
        searchInfo.put("area", tv_prefecture.getText().toString());
        searchInfo.put("distance", distanceSelect);
        searchInfo.put("elevator", elevatorSelect);
        searchInfo.put("course_type", courseTypeSelect);

    }


}
