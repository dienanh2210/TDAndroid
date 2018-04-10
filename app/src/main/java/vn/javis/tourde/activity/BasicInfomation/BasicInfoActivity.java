package vn.javis.tourde.activity.BasicInfomation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.BaseActivity;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.MenuPageActivity;

public class BasicInfoActivity extends BaseActivity {

    TextView btn_Back_BasicInfo,btn_Close_BasicInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_basic_info );
        btn_Back_BasicInfo=findViewById( R.id.btn_Back_BasicInfo );
        btn_Back_BasicInfo.setOnClickListener( onClickBackBasicInfo );
        btn_Close_BasicInfo=findViewById( R.id.btn_Close_BasicInfo );
        btn_Close_BasicInfo.setOnClickListener( onClickCloseBasicInfo );
    }
    View.OnClickListener onClickCloseBasicInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( BasicInfoActivity.this, CourseListActivity.class );
            startActivity( intent );

        }
    };
    View.OnClickListener onClickBackBasicInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( BasicInfoActivity.this, MenuPageActivity.class );
            startActivity( intent );

        }
    };
}
