package vn.javis.tourde.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.view.Window;
import android.widget.TextView;

import vn.javis.tourde.R;

public class MenuPage extends AppCompatActivity {

    TextView btn_Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu_page);


//        btn_Login=findViewById( R.id.btn_Login );
//        btn_Login.setOnClickListener( onClickLogin );


    }
//    View.OnClickListener onClickLogin = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//           Intent intent = new Intent( MenuPage.this,LoginFragmentDemo.class );
//            startActivity( intent );
//
//        }
//    };


    public void clickMe(View view) {
        Intent intent = new Intent(this, LoginView.class);
        startActivity(intent);
    }


}





