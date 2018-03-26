package vn.javis.tourde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import vn.javis.tourde.Adapters.DatabaseAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Intent intent = new Intent(MainActivity.this, CourseList.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
