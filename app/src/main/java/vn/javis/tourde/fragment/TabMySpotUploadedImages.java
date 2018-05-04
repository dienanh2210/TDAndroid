package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListMySpotsImageAdapter;
import vn.javis.tourde.adapter.ListSpotsImageAdapter;

public class TabMySpotUploadedImages extends BaseFragment{


    @BindView(R.id.grv_spot_img)
    GridView GrvSpotImage;

    ListMySpotsImageAdapter listSpotImageAdapter;
    CourseListActivity mActivity;
    List<String> listSpotImg = new ArrayList<>();
    String avagePace,finishTIme,startAddress;



    public static TabMySpotUploadedImages intansce(List<String> listSpotImg) {
        TabMySpotUploadedImages instance = new TabMySpotUploadedImages();
        instance.listSpotImg = listSpotImg;
        return instance;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        mActivity = (CourseListActivity) getActivity();
        if (listSpotImg.size() > 0) {
            Log.i("listSpot: ", "" + listSpotImg.size());
            listSpotImageAdapter = new ListMySpotsImageAdapter(getActivity(), R.layout.spot_image_single, listSpotImg);
            GrvSpotImage.setAdapter(listSpotImageAdapter);
        }

    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.tab_my_spot_uploaded_image, container, false);
    }
    //Overriden method onCreateView

}
