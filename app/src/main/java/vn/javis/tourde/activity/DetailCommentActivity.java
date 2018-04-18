package vn.javis.tourde.activity;

import android.support.annotation.Dimension;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import vn.javis.tourde.R;

public class DetailCommentActivity extends BaseActivity {
    RatingBar mRatingBar;
    EditText edt_text;
    private boolean isReached = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail_comment );
        edt_text=findViewById( R.id.edt_text );

        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRatingBar.setRating(0);

            }
        });

        edt_text.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {

                for(int i = s.length()-1; i >= 0; i--){
                    if(s.charAt(i) == '\n'){
                        s.delete(i, i + 1);
                        return;
                    }
                }
            }
        });
    }
}
