package vn.javis.tourde.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.model.SpotData;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TourDeApplication;
import vn.javis.tourde.utils.CameraPreview;
import vn.javis.tourde.utils.CameraUtils;
import vn.javis.tourde.utils.ProcessDialog;
import vn.javis.tourde.utils.TimeUtil;

/**
 * Created by QuanPham on 6/9/18.
 */

public class TakePhotoActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_course_title)
    TextView courseTitle;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_spot_title)
    TextView spotTitle;
    @BindView(R.id.txt_distance)
    TextView txtDistance;
    @BindView(R.id.frameCamera)
    FrameLayout frameCamera;

    @BindView(R.id.btn_capture)
    ImageButton btnCapture;
    @BindView(R.id.btn_back_take_photo)
    ImageButton btnBack;

    @BindView(R.id.activity_custom_camera)
    LinearLayout activity_custom_camera;
    @BindView(R.id.canvasLayout)
    RelativeLayout canvasLayout;

    Camera camera;
    CameraPreview cameraPreview;
    int cameraType = 0;
    int spotId;
    int courseID;
    String time;
    String distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.take_photo);
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
       showProgressDialog();
        spotId = getIntent().getIntExtra(CourseListActivity.SPOT_ID, 0);
        courseID = getIntent().getIntExtra(CourseListActivity.COURSE_DETAIL_ID, 0);
        time  = getIntent().getStringExtra(CourseListActivity.TIME_FINISH);
        txtTime.setText(time);
        distance  = getIntent().getStringExtra(CourseListActivity.STAMP_DISTANCE);
        txtDistance.setText(distance + "km");
        if (spotId > 0) {
            SpotDataAPI.getSpotData(spotId, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                    JSONObject jsonObject = (JSONObject) response;
                    Log.i("getSpotData", jsonObject.toString());
                    if (jsonObject.has("error"))
                        return;
                    SpotData spotData = SpotData.getSpotData(response.toString());
                    if (spotData == null)
                        return;
                    spotTitle.setText(spotData.getData().getTitle());

                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
        if (courseID > 0) {
            GetCourseDataAPI.getCourseData(courseID, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                    JSONObject jsonObject = (JSONObject) response;
                    Log.i("getCourseData", jsonObject.toString());
                    if (jsonObject.has("error"))
                        return;
                    CourseDetail mCourseDetail = new CourseDetail((JSONObject) response);
                    courseTitle.setText(mCourseDetail.getmCourseData().getTitle());

                  hideProgressDialog();
                }

                @Override
                public void onError(VolleyError error) {
                    hideProgressDialog();
                }
            });
        }


        if (checkCameraHardware()) {
            camera = getCameraInstance(cameraType);
            cameraPreview = new CameraPreview(this, camera, cameraType);
            frameCamera.addView(cameraPreview);
            camera.setFaceDetectionListener(new TakePhotoActivity.MyFaceDetectionListener());

            setFocus();
            startFaceDetection();
        } else {
            Toast.makeText(TakePhotoActivity.this, "Device not support camera feature", Toast.LENGTH_SHORT).show();
        }


        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TourDeApplication.getInstance().trackEvent("tap_photoframe_spot_id="+spotId,"tap","tap_photoframe_spot_id="+spotId);
                camera.takePicture(myShutterCallback, null, pictureCallback);

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setHeightView(View view) {
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        FrameLayout.LayoutParams layoutParams;
        layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.height = height;
    }


    public void setFocus() {
        Camera.Parameters params = camera.getParameters();

        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Camera.Size size = sizes.get(0);
        for (int i = 0; i < sizes.size(); i++) {

            if (sizes.get(i).width > size.width)
                size = sizes.get(i);


        }
        params.setPictureSize(size.width, size.height);
        if (params.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        params.setJpegQuality(100);
        camera.setParameters(params);
    }

    public void startFaceDetection() {
        // Try starting Face Detection
        Camera.Parameters params = camera.getParameters();

        // start face detection only *after* preview has started
        if (params.getMaxNumDetectedFaces() > 0) {
            // camera supports face detection, so can start it:
            camera.startFaceDetection();
        }
    }

    void setCameraZoom(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        int zoom = parameters.getMaxZoom();
        parameters.setZoom(5);
        camera.setParameters(parameters);
    }

    private boolean checkCameraHardware() {

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    private Camera getCameraInstance(int cameraType) {
        Log.i("cameraType", "" + cameraType);
        Camera camera = null;
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        for (int i=0;i<Camera.getNumberOfCameras();i++){
//            Camera.CameraInfo camInfo = new Camera.CameraInfo();
//            Camera.getCameraInfo(i, camInfo);
//
//            if (camInfo.facing==(Camera.CameraInfo.CAMERA_FACING_FRONT)) {
        try {
            camera = Camera.open(cameraType);
        } catch (Exception e) {
            Log.d("tag", "Error setting camera not open " + e);
        }
//            }
//        }

        return camera;
    }

    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback() {

        @Override
        public void onShutter() {
            // TODO Auto-generated method stub

        }
    };

    class MyFaceDetectionListener implements Camera.FaceDetectionListener {

        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            /*if (faces.length > 0) {
                Log.d("FaceDetection", "face detected: " + faces.length +
                        " Face 1 Location X: " + faces[0].rect.centerX() +
                        "Y: " + faces[0].rect.centerY());
                Toast.makeText(TakePhotoActivity.this, "face detected: " + faces.length +
                        " Face 1 Location X: " + faces[0].rect.centerX() +
                        "Y: " + faces[0].rect.centerY(), Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if ((ContextCompat.checkSelfPermission(TakePhotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(getApplicationContext(), "Please allow perminssion to access your memory", Toast.LENGTH_LONG);
            }
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + ".jpg");
            if (file == null) {
                Log.d("tag", "Error creating media file, check storage permissions: ");
                return;
            }

            int width = getResources().getDisplayMetrics().widthPixels;
            int hight = getResources().getDisplayMetrics().heightPixels;

            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, (data) != null ? data.length : 0);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                Bitmap scaledBm = Bitmap.createScaledBitmap(bm, width, hight, true);
                int w = scaledBm.getWidth();
                int h = scaledBm.getHeight();

                Matrix matrix = new Matrix();
                matrix.setRotate(90);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, false);

            }

            /*BitmapDrawable bmd = new BitmapDrawable(getResources(), bm);
            canvasLayout.setBackground(bmd);*/
            hideView();
            bm =  CameraUtils.addView(bm,canvasLayout);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        bm, file.getAbsolutePath(), "" + new Date().getTime());
                CameraUtils.addImageToGallery(file.getAbsolutePath(), TakePhotoActivity.this);
                Log.i("addImageToGallery", file.getAbsolutePath());
            } catch (Exception e) {
                e.getMessage();
                Log.d("getMessage", e.getMessage());
            } finally {
                resetView();
            }
            camera.startPreview();
            // btnCapture.setVisibility(View.GONE);
            Log.d("tag", "Camera ok ");

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.release();
    }

    private void resetView(){
        canvasLayout.setBackground(null);
        btnBack.setVisibility(View.VISIBLE);
        frameCamera.setVisibility(View.VISIBLE);

    }

    private void hideView(){
        btnBack.setVisibility(View.INVISIBLE);
        frameCamera.setVisibility(View.INVISIBLE);
    }
}
