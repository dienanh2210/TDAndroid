package vn.javis.tourde.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResizeImage {
    private static final int MAX_IMAGE_SIZE = 2500;

    public static String resizeFile(Context context, String filePath) {
        String folder = "cache";
        File mkDir = new File(
                context.getFilesDir(),
//                Environment.getExternalStorageDirectory(), ".shokuji/"
                "TourDe/"
                        + folder);
        mkDir.mkdirs();
        String id = "" + System.currentTimeMillis();
        File pictureFile = new File(
                context.getFilesDir(),
//                Environment.getExternalStorageDirectory(), ".shokuji/"
                "TourDe/"
                        + folder + "/" + id + ".jpg");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (bitmap == null) return filePath;
        if (bitmap.getWidth() <= MAX_IMAGE_SIZE && bitmap.getHeight() <= MAX_IMAGE_SIZE)
            return filePath;
        int width = bitmap.getWidth();
        float scale = (float) width / (float) MAX_IMAGE_SIZE;
        if (bitmap.getHeight() / MAX_IMAGE_SIZE > scale) {
            scale = bitmap.getHeight() / MAX_IMAGE_SIZE;
        }
        if (scale > 0) {
            bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((float) bitmap.getWidth() / scale), (int) ((float) bitmap.getHeight() / scale), false);
        } else {
            return filePath;
        }
        int angle = 0;
        try {
            ExifInterface exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                default:
                    angle = 0;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            angle = 0;
        }
        if (bitmap == null) return filePath;
        Matrix mat = new Matrix();
//        if (angle == 0 && bitmap.getWidth() > bitmap.getHeight())
//            mat.postRotate(90);
//        else
        if (angle > 0)
            mat.postRotate(angle);
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1) {
                float pp = (bitmap.getByteCount() / (10f * 1024 * 1024));
                Log.d("PP RESIZE", "" + pp);
                if (pp > 1 || scale > 0) {
                    if (pp <= 1 && scale > 0) {
                        pp = 1;
                    }
                    FileOutputStream purge = new FileOutputStream(pictureFile);
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true).compress(Bitmap.CompressFormat.JPEG, (int) (100f / pp), purge);
                    purge.close();
                    if (!TextUtils.isEmpty(pictureFile.getPath()))
                        return pictureFile.getPath();
                    else return filePath;
                } else return filePath;
            } else {
                FileOutputStream purge = new FileOutputStream(pictureFile);
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true).compress(Bitmap.CompressFormat.JPEG, 100, purge);
                purge.close();
                if (!TextUtils.isEmpty(pictureFile.getPath()))
                    return pictureFile.getPath();
                else return filePath;
            }

        } catch (FileNotFoundException e) {
            Log.d("DG_DEBUG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("DG_DEBUG", "Error accessing file: " + e.getMessage());
        }
        if (!TextUtils.isEmpty(pictureFile.getPath()))
            return pictureFile.getPath();
        else return filePath;
    }
}
