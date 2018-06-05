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

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.adapter.ListMySpotUploadedImageAdapter;
import vn.javis.tourde.apiservice.PostImageAPI;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.ProcessDialog;

import static vn.javis.tourde.fragment.RegisterFragment.FILE_SIZE_8MB;


public class TabMySpotUploadedImages extends BaseFragment {


    @BindView(R.id.rcv_my_spot_img)
    RecyclerView rcvMySpotImage;

    ListMySpotUploadedImageAdapter listSpotImageAdapter;
    CourseListActivity mActivity;
    List<String> listSpotImg = new ArrayList<>();
    String avagePace, finishTIme, startAddress;
    int spotId;

    public static TabMySpotUploadedImages intansce(List<String> listSpotImg,int spotID) {
        TabMySpotUploadedImages instance = new TabMySpotUploadedImages();
        instance.listSpotImg = listSpotImg;
        instance.spotId = spotID;
        return instance;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        if (listSpotImg.size() > 0) {
            Log.i("listSpot: ", "" + listSpotImg.size());
            RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, 1) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            rcvMySpotImage.setLayoutManager(layoutManager);
            listSpotImageAdapter = new ListMySpotUploadedImageAdapter(listSpotImg, mActivity);
            listSpotImageAdapter.setOnItemClickListener(new ListMySpotUploadedImageAdapter.OnItemClickedListener() {
                @Override
                public void onItemClick(int position) {
                    if (position == 0) {

                        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                        //     uploadMyImage(GET_FROM_GALLERY,Activity.RESULT_OK,);
                    }
                }
            });
            rcvMySpotImage.setAdapter(listSpotImageAdapter);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uploadMyImage(requestCode, resultCode, data);
    }

    Bitmap bitmapIcon;
    private static final int GET_FROM_GALLERY = 1;

    private void uploadMyImage(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmapIcon = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), selectedImage);
                File file = new File("android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI");
                boolean isLarge = false;

                Log.i("File size: ", "" + file.length());
                if (file.length() > FILE_SIZE_8MB) {
                    isLarge = true;
                }
                if (!isLarge) {
                    //upload bitmap to server
                    PostImageAPI.postImage(mActivity, bitmapIcon, new ServiceCallback() {
                        @Override
                        public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                            JSONObject jsonObject = (JSONObject) response;
                            if (jsonObject.has("success")) {
                                String user_image_id = jsonObject.getString("user_image_id");
                                //post image spot
                                String token = LoginFragment.getmUserToken();
                                ArrayList<String> arrayID = new ArrayList<>();
                                arrayID.add(user_image_id);
                                String s = arrayID.toString();
                                SpotDataAPI.postSpotImage(token, spotId,s, new ServiceCallback() {
                                    @Override
                                    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                                        JSONObject jsonObject = (JSONObject) response;
                                        if (jsonObject.has("success")) {
                                            Log.i("TabMyUploadImg1235","upload success");
                                        }
                                    }

                                    @Override
                                    public void onError(VolleyError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
                } else
                    ProcessDialog.showDialogOk(getContext(), "", "容量が大きすぎるため投稿できません。");
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
