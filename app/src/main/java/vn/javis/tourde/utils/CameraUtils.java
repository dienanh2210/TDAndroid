package vn.javis.tourde.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import vn.javis.tourde.BuildConfig;

public class CameraUtils {
    private static final String TAG = CameraUtils.class.getSimpleName();


    public static boolean checkWriteToDiskPermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isDeviceSupportCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static void openSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String getAppDirectory(Context context) {
        PackageManager m = context.getPackageManager();
        String s = context.getPackageName();
        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            s = p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return s;
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    public static Bitmap loadBitmapFromViewAuto(View v, int originalWidth, int originalHeight) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }


    public static Bitmap optimizeBitmap(int sampleSize, String filePath) {
        // bitmap factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = sampleSize;

        return BitmapFactory.decodeFile(filePath, options);
    }

    public Bitmap combineImages(Bitmap ScaledBitmap, Bitmap bit) {

        int X = bit.getWidth();
        int Y = bit.getHeight();

        Bitmap overlaybitmap = Bitmap.createBitmap(ScaledBitmap.getWidth(),
                ScaledBitmap.getHeight(), ScaledBitmap.getConfig());
        Canvas canvas = new Canvas(overlaybitmap);
        canvas.drawBitmap(ScaledBitmap, new Matrix(), null);
        canvas.drawBitmap(bit, new Matrix(), null);
        return overlaybitmap;
    }

    public static void storeImage(Bitmap image, File file) {
        if (file == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

    }

    public static Bitmap addLayout(Bitmap toEdit, String courseDesc, String distance, String spot, String time, String logo) {
        Log.i(TAG, "addLayout: ");
        Bitmap dest = toEdit.copy(Bitmap.Config.ARGB_8888, true);
        int pictureHeight = dest.getHeight();
        int pictureWidth = dest.getWidth();
        int margin = 50;
        int padding = 100;
        int paddingLine = 200;


        Canvas canvas = new Canvas(dest);
        //Text Info Design
        Paint painTextInfo = new Paint();  //set the look
        painTextInfo.setAntiAlias(true);
        painTextInfo.setColor(Color.WHITE);
        painTextInfo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        painTextInfo.setStyle(Paint.Style.FILL);
        painTextInfo.setShadowLayer(2.0f, 1.0f, 1.0f, Color.GRAY);
        painTextInfo.setTextSize(pictureHeight * .03f);

        //Text Header design
        Paint painTextHeader = new Paint();  //set the look
        painTextHeader.setAntiAlias(true);
        painTextHeader.setColor(Color.WHITE);
        painTextHeader.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        painTextHeader.setStyle(Paint.Style.FILL);
        painTextHeader.setShadowLayer(2.0f, 1.0f, 1.0f, Color.GRAY);
        painTextHeader.setTextSize(pictureHeight * .03f);

        //Logo design
        Paint painTextLogo = new Paint();  //set the look
        painTextLogo.setAntiAlias(true);
        painTextLogo.setColor(Color.WHITE);
        painTextLogo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        painTextLogo.setStyle(Paint.Style.FILL);
        painTextLogo.setShadowLayer(2.0f, 1.0f, 1.0f, Color.GRAY);
        painTextLogo.setTextSize(pictureHeight * .03f);

        //Line design
        Paint paintLine = new Paint();  //set the look
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setStrokeWidth(10f);
        paintLine.setShadowLayer(2.0f, 1.0f, 1.0f, Color.GRAY);
        //Logo
        canvas.drawText(logo, pictureWidth * 3 / 4, 100, painTextLogo);
        //Draw line 1
        canvas.drawLine(padding, pictureHeight * 2 / 3 + margin, pictureWidth / 3, pictureHeight * 2 / 3 + margin, paintLine);
        canvas.drawText("COURSE", padding, pictureHeight * 2 / 3, painTextHeader);
        canvas.drawText(courseDesc, padding, pictureHeight * 2 / 3 + paddingLine, painTextHeader);
        canvas.drawLine(pictureWidth * 2 / 3, pictureHeight * 2 / 3 + margin, pictureWidth - padding, pictureHeight * 2 / 3 + margin, paintLine);
        canvas.drawText("SPOT", pictureWidth * 2 / 3, pictureHeight * 2 / 3, painTextHeader);
        canvas.drawText(spot, pictureWidth * 2 / 3, pictureHeight * 2 / 3 + paddingLine, painTextHeader);

        //Draw line 2
        canvas.drawLine(padding, pictureHeight * 5 / 6 + margin, pictureWidth / 3, pictureHeight * 5 / 6 + margin, paintLine);
        canvas.drawText("TIME", padding, pictureHeight * 5 / 6, painTextHeader);
        canvas.drawText(time, padding, pictureHeight * 5 / 6 + paddingLine, painTextHeader);
        canvas.drawLine(pictureWidth * 2 / 3, pictureHeight * 5 / 6 + margin, pictureWidth - padding, pictureHeight * 5 / 6 + margin, paintLine);
        canvas.drawText("DISTANCE", pictureWidth * 2 / 3, pictureHeight * 5 / 6, painTextHeader);
        canvas.drawText(distance, pictureWidth * 2 / 3, pictureHeight * 5 / 6 + paddingLine, painTextHeader);

        return dest;
    }

    public static Bitmap addView(Bitmap bitmap, View v) {
        Log.i(TAG, "addLayout: ");
        Bitmap view = loadBitmapFromView(v);
        Bitmap scaleView = Bitmap.createScaledBitmap(view, bitmap.getWidth(), bitmap.getHeight() , false);
        Bitmap dest = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas c = new Canvas(dest);
        c.drawBitmap(scaleView, 0, 0, null);
        return dest;
    }


}
