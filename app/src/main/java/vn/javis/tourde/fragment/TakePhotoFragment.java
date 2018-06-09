package vn.javis.tourde.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.MenuPageActivity;
import vn.javis.tourde.adapter.ListBadgeAdapter;
import vn.javis.tourde.apiservice.BadgeAPI;
import vn.javis.tourde.apiservice.GetCourseDataAPI;
import vn.javis.tourde.apiservice.SpotDataAPI;
import vn.javis.tourde.model.Badge;
import vn.javis.tourde.model.CourseDetail;
import vn.javis.tourde.model.SpotData;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.utils.CameraPreview;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class TakePhotoFragment extends BaseFragment implements SurfaceHolder.Callback{

    CourseListActivity mActivity;
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
    Bitmap bitmap;
    Camera camera;
    CameraPreview cameraPreview;
    int cameraType = 0;
    int spotId;
    int courseID;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    private static String imageStoragePath;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mActivity = (CourseListActivity) getActivity();
        spotId = getArguments().getInt(CourseListActivity.SPOT_ID);
        courseID = getArguments().getInt(CourseListActivity.COURSE_DETAIL_ID);

        if(spotId>0){
            SpotDataAPI.getSpotData(spotId, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                    JSONObject jsonObject =(JSONObject)response;
                    if(jsonObject.has("error"))
                        return;
                    SpotData spotData = SpotData.getSpotData(response.toString());
                    if(spotData==null)
                        return;
                    spotTitle.setText(spotData.getData().getTitle());
                  if(spotData.getData().getInsertDatetime() !=null && spotData.getData().getInsertDatetime() !="" )
                      txtTime.setText(spotData.getData().getInsertDatetime());
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
        if(courseID>0){
            GetCourseDataAPI.getCourseData(courseID, new ServiceCallback() {
                @Override
                public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                    JSONObject jsonObject =(JSONObject)response;
                    if(jsonObject.has("error"))
                        return;
                  CourseDetail  mCourseDetail = new CourseDetail((JSONObject) response);
                  courseTitle.setText(mCourseDetail.getmCourseData().getTitle());
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
        if (checkCameraHardware()) {

            /*camera = getCameraInstance(cameraType);
            cameraPreview = new CameraPreview(mActivity, camera, cameraType);
            frameCamera.addView(cameraPreview);
            camera.setFaceDetectionListener(new MyFaceDetectionListener());*/
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            /*setFocus();
            startFaceDetection();*/
        } else {
            Toast.makeText(mActivity, "Device not support camera feature", Toast.LENGTH_SHORT).show();
        }

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(myShutterCallback, null, pictureCallback);

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.take_photo, container, false);
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

        if (mActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

   /* private Camera getCameraInstance(int cameraType) {
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        *//*Camera camera = null;
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

        return camera;*//*
    }*/

    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback() {

        @Override
        public void onShutter() {
            // TODO Auto-generated method stub

        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        camera = android.hardware.Camera.open();
        try {
            setFocus();
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    class MyFaceDetectionListener implements Camera.FaceDetectionListener {

        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            if (faces.length > 0) {
                Log.d("FaceDetection", "face detected: " + faces.length +
                        " Face 1 Location X: " + faces[0].rect.centerX() +
                        "Y: " + faces[0].rect.centerY());
                Toast.makeText(mActivity, "face detected: " + faces.length +
                        " Face 1 Location X: " + faces[0].rect.centerX() +
                        "Y: " + faces[0].rect.centerY(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
           /* if ((ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(mActivity.getApplicationContext(), "Please allow perminssion to access your memory", Toast.LENGTH_LONG);
            }
            File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/" + System.currentTimeMillis() + ".jpg");
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
            String extr = Environment.getExternalStorageDirectory().toString()
                    + File.separator + "testfolder";
            File myPath = new File(extr, "");
            FileOutputStream fos = null;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                MediaStore.Images.Media.insertImage(mActivity.getContentResolver(),
                        bm, myPath.getPath(), "" + new Date().getTime());
            } catch (Exception e) {
                e.getMessage();
                Log.d("getMessage", e.getMessage());
            }
            camera.startPreview();
           // btnCapture.setVisibility(View.GONE);
           // takeScreenshot();
            Log.d("tag", "Camera ok ");*/
            Log.i("", "onPicture: ");
            bitmap = BitmapFactory.decodeByteArray(data, 0, (data) != null ? data.length : 0);
            File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/" + System.currentTimeMillis() + ".jpg");
            imageStoragePath = file.getAbsolutePath();
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            FileOutputStream outStream = null;
            try {
                Log.i("", "onPictureTaken: ");
                outStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                Bitmap second = addLayout(bitmap);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                second.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                MediaStore.Images.Media.insertImage(mActivity.getContentResolver(),
                        second, imageStoragePath, "" + new Date().getTime());
                //previewCapturedImage(second);
            } catch (Exception e) {
                e.getMessage();
                Log.d("getMessage", e.getMessage());
            }


        }



    };
    /*private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = mActivity.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }*/
    /*private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }*/

    private Bitmap addLayout(Bitmap toEdit){

        Bitmap dest = toEdit.copy(Bitmap.Config.ARGB_8888, true);
        int pictureHeight = dest.getHeight();
        int pictureWidth = dest.getWidth();
        int margin = 50;
        int padding = 100;



        Canvas canvas = new Canvas(dest);

        Paint painText = new Paint();  //set the look
        painText.setAntiAlias(true);
        painText.setColor(Color.WHITE);
        painText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        painText.setStyle(Paint.Style.FILL);
        painText.setShadowLayer(2.0f, 1.0f, 1.0f, Color.GRAY);
        painText.setTextSize(pictureHeight * .04629f);

        Paint paintLine = new Paint();  //set the look
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setStrokeWidth(10f);
        paintLine.setShadowLayer(2.0f, 1.0f, 1.0f, Color.GRAY);

        //Draw line 1
        canvas.drawLine(padding,pictureHeight*2/3 + margin,pictureWidth/3,pictureHeight*2/3 + margin,paintLine);
        canvas.drawText("COURSE" , padding,  pictureHeight*2/3 , painText);
        canvas.drawLine(pictureWidth *2/3,pictureHeight*2/3 + margin,pictureWidth - padding,pictureHeight*2/3 + margin,paintLine);
        canvas.drawText("SPOT" , pictureWidth *2/3,  pictureHeight*2/3 , painText);
        //Draw line 2
        canvas.drawLine(padding,pictureHeight*5/6 + margin,pictureWidth/3,pictureHeight*5/6 + margin,paintLine);
        canvas.drawText("TIME" , padding,  pictureHeight*5/6 , painText);
        canvas.drawLine(pictureWidth *2/3,pictureHeight*5/6 + margin,pictureWidth - padding,pictureHeight*5/6 + margin,paintLine);
        canvas.drawText("DISTANCE" , pictureWidth *2/3,  pictureHeight*5/6 , painText);
        return dest;
    }

}
