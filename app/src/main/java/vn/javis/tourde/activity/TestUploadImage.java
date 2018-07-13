package vn.javis.tourde.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import java.util.Map;

import vn.javis.tourde.R;
import vn.javis.tourde.apiservice.ApiEndpoint;
import vn.javis.tourde.apiservice.PostImageAPI;
import vn.javis.tourde.services.HttpsTrustManager;
import vn.javis.tourde.services.ServiceCallback;
import vn.javis.tourde.services.ServiceResult;
import vn.javis.tourde.services.TLSSocketFactory;
import vn.javis.tourde.volley.VolleyMultipartRequest;

public class TestUploadImage extends AppCompatActivity {
    ImageView imageView;

    //edittext for getting the tags input
    EditText editTextTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_upload_img);
        imageView = (ImageView) findViewById(R.id.imageView);
        editTextTags = (EditText) findViewById(R.id.editTextTags);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }
        findViewById(R.id.buttonUploadImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextTags.getText().toString().trim().isEmpty()) {
                    editTextTags.setError("Enter tags first");
                    editTextTags.requestFocus();
                    return;
                }
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);

            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
                PostImageAPI.postImage(this, bitmap, new ServiceCallback() {
                    @Override
                    public void onSuccess(ServiceResult resultCode, Object response) throws JSONException {
                        Log.i("uploadimage",response.toString());
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

