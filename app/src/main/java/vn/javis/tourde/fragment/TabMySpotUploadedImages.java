package vn.javis.tourde.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListMySpotUploadedImageAdapter;
import vn.javis.tourde.utils.ProcessDialog;

import static vn.javis.tourde.fragment.RegisterFragment.FILE_SIZE_8MB;


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
            listSpotImageAdapter.setOnItemClickListener(new ListMySpotUploadedImageAdapter.OnItemClickedListener() {
                @Override
                public void onItemClick(int position) {
                    if(position==0)
                    {
                        //uploadMyImage(GET_FROM_GALLERY,Activity.RESULT_OK,);
                    }
                }
            });
            rcvMySpotImage.setAdapter(listSpotImageAdapter);
        }

    }
    Bitmap bitmapIcon;
    private static final int GET_FROM_GALLERY = 1;
    private void uploadMyImage(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmapIcon = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                File file = new File("android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI");

                if(file.length() > FILE_SIZE_8MB)
                    Log.i("","");
                    //listSpotImg.setImageBitmap(bitmapIcon);
                else
                    ProcessDialog.showDialogOk(getContext(),"","容量が大きすぎるため投稿できません。");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.tab_my_spot_uploaded_image, container, false);
    }
    //Overriden method onCreateView

}
