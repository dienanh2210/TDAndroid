package vn.javis.tourde.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.javis.tourde.R;

public class SrcollViewImageActivity extends BaseActivity {
Button bt_rule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_srcoll_view_image );
        bt_rule=findViewById( R.id.bt_rule );
        bt_rule.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rule();
            }
        } );
    }
    void Rule(){
        Intent intent = new Intent(this, ViewPageActivity.class);
     //  Intent intent = new Intent(this, UsePageActivity.class);
        startActivity(intent);
        finish();
    }
}
