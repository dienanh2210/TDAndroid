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

    TextView tv_back_basicInfo,tv_close_basicInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_basic_info );
        tv_back_basicInfo=findViewById( R.id.tv_back_basicInfo );
        tv_back_basicInfo.setOnClickListener( onClickBackBasicInfo );
        tv_close_basicInfo=findViewById( R.id.tv_close_basicInfo );
        tv_close_basicInfo.setOnClickListener( onClickCloseBasicInfo );
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
