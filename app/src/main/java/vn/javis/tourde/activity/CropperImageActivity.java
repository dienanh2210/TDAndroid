package vn.javis.tourde.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.OnClick;
import vn.javis.tourde.R;
import vn.javis.tourde.fragment.RegisterFragment;
import vn.javis.tourde.utils.Constant;

/**
 * Created by QuanPham on 7/4/18.
 */


public class CropperImageActivity extends BaseActivity implements CropImageView.OnSetImageUriCompleteListener,
        CropImageView.OnCropImageCompleteListener {

    @BindView(R.id.cropImageView)
    CropImageView mCropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cropper_image);
        super.onCreate(savedInstanceState);
        mCropImageView.setOnSetImageUriCompleteListener(this);
        mCropImageView.setOnCropImageCompleteListener(this);
        Uri uri = getIntent().getParcelableExtra(Constant.KEY_IMAGE_URI);
        setImageUri(uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            handleCropResult(result);
        }
    }

    /**
     * Set the image to show for cropping.
     */
    public void setImageUri(Uri imageUri) {
        mCropImageView.setImageUriAsync(imageUri);
        //        CropImage.activity(imageUri)
        //                .start(getContext(), this);
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {

    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        handleCropResult(result);
    }

    private void handleCropResult(CropImageView.CropResult result) {
        Intent intent = new Intent();

        if (result.getUri() != null) {
            Log.i("getUri", "------------->>>>");
            intent.putExtra(Constant.KEY_IMAGE_CROPPER, result.getUri());
        } else {
            Log.i("getBitmap", "------------->>>>");
            RegisterFragment.bitmapIcon = result.getBitmap();
        }
        setResult(RESULT_OK, intent);
        CropperImageActivity.this.finish();
    }

    @OnClick({R.id.btnCropper, R.id.btnClose})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btnCropper:
                Log.i("btnCropper", "--------->");
                mCropImageView.getCroppedImageAsync();
                break;
            case R.id.btnClose:
                Log.i("btnClose", "--------->");
                CropperImageActivity.this.finish();
                break;
        }
    }

}
