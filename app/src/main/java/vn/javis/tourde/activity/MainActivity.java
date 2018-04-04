package vn.javis.tourde.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;

import android.view.Window;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.login.MenuPage;

public class MainActivity extends Activity {

    ImageButton btn_Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        this.requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_main );
        btn_Next = findViewById( R.id.next );
        btn_Next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, MenuPage.class );
                startActivity( intent );
            }
        } );
    }
}





