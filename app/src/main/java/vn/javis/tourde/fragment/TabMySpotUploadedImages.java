package vn.javis.tourde.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListMySpotUploadedImageAdapter;


public class TabMySpotUploadedImages extends BaseFragment {


    @BindView(R.id.rcv_my_spot_img)
    RecyclerView rcvMySpotImage;

    ListMySpotUploadedImageAdapter listSpotImageAdapter;
    CourseListActivity mActivity;
    List<String> listSpotImg = new ArrayList<>();
    String avagePace, finishTIme, startAddress;


    public static TabMySpotUploadedImages intansce(List<String> listSpotImg) {
        TabMySpotUploadedImages instance = new TabMySpotUploadedImages();
        instance.listSpotImg = listSpotImg;
        return instance;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        if (listSpotImg.size() > 0) {
            Log.i("listSpot: ", "" + listSpotImg.size());
            RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, 1);;
            rcvMySpotImage.setLayoutManager(layoutManager);
            listSpotImageAdapter = new ListMySpotUploadedImageAdapter( listSpotImg,mActivity);
            rcvMySpotImage.setAdapter(listSpotImageAdapter);
        }

    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.tab_my_spot_uploaded_image, container, false);
    }
    //Overriden method onCreateView

}
