package vn.javis.tourde.ScreenMain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.javis.tourde.R;
import android.app.Activity;
import android.view.Window;
public class ScreenMain extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.activity_screen_main );
    }
}
