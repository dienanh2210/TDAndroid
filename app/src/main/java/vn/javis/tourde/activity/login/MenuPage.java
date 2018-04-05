package vn.javis.tourde.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.CourseListActivity;
import vn.javis.tourde.activity.MainActivity;

public class MenuPage extends Activity {

 TextView btn_Basic,btn_User,btn_Badge,btn_NewUser,btn_Login,btn_Logout,btn_AppInfo,btn_HowTo,btn_Inquiry,btn_Privacy,btn_RateInApp,btn_Version,btn_Close;

 RelativeLayout user_Info,basic_Info,badge_Cllection,new_User,login,logout,app_Info,how_To,inquiry,privacy,rateInApp,version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        this.requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_menu_page);

        btn_User=findViewById( R.id.btn_User );
        btn_Basic=findViewById( R.id.btn_Basic );
        btn_Badge=findViewById( R.id.btn_Badge);
        btn_NewUser=findViewById(   R.id.btn_NewUser );
        btn_Login=findViewById( R.id.btn_Login );
        btn_Login.setOnClickListener( onClickLogin );
        btn_Logout=findViewById( R.id.btn_Logout );
        btn_AppInfo=findViewById( R.id.btn_AppInfo );
        btn_HowTo=findViewById( R.id.btn_HowTo );
        btn_Inquiry=findViewById( R.id.btn_Inquiry );
        btn_Privacy=findViewById( R.id.btn_Privacy );
        btn_RateInApp=findViewById( R.id.btn_RateInApp );
        btn_Version=findViewById( R.id.btn_Version );
        btn_Close=findViewById( R.id.btn_Close );
        btn_Close.setOnClickListener( onClick );

        user_Info=findViewById( R.id.user_Info );
        basic_Info=findViewById( R.id.basic_Info );
        badge_Cllection=findViewById( R.id.badge_Collection );
        new_User=findViewById( R.id.new_User);
        login=findViewById( R.id.login );
        logout=findViewById( R.id.logout );
        app_Info=findViewById( R.id.app_Info );
        how_To=findViewById( R.id.how_To );
        inquiry=findViewById( R.id.inquiry );
        privacy=findViewById( R.id.privacy );
        rateInApp=findViewById( R.id.rateInApp );
        version=findViewById( R.id.version );

    }
    View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( MenuPage.this, LoginView.class );
            startActivity( intent );

        }
    };
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent( MenuPage.this, CourseListActivity.class );
            startActivity( intent );
            btn_Close.setTextSize( TypedValue.COMPLEX_UNIT_SP,27f );
            return;
        }
    };

    public void clickMe(View v){

        switch(v.getId()){
            case R.id.btn_User:
                inquiry.setBackgroundColor( Color.WHITE );
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.GRAY );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.WHITE );
                rateInApp.setBackgroundColor( Color.WHITE );
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_Basic:
                inquiry.setBackgroundColor( Color.WHITE );
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.GRAY);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.WHITE );
                rateInApp.setBackgroundColor( Color.WHITE );
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_Badge:
                inquiry.setBackgroundColor( Color.WHITE );
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.GRAY );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.WHITE );
                rateInApp.setBackgroundColor( Color.WHITE );
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_NewUser:
                inquiry.setBackgroundColor( Color.WHITE);
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.GRAY);
                privacy.setBackgroundColor( Color.WHITE );
                rateInApp.setBackgroundColor( Color.WHITE );
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_Login:
                inquiry.setBackgroundColor( Color.WHITE);
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.GRAY );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.WHITE );
                rateInApp.setBackgroundColor( Color.WHITE );
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_Logout:
                inquiry.setBackgroundColor( Color.WHITE );
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.GRAY );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.WHITE );
                rateInApp.setBackgroundColor( Color.WHITE );
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_AppInfo:
                inquiry.setBackgroundColor( Color.WHITE );
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.GRAY );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.WHITE );
                rateInApp.setBackgroundColor( Color.WHITE );
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_HowTo:
                inquiry.setBackgroundColor( Color.WHITE );
                how_To.setBackgroundColor( Color.GRAY);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.WHITE );
                rateInApp.setBackgroundColor( Color.WHITE );
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_Inquiry:
                inquiry.setBackgroundColor( Color.GRAY );
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.WHITE );
                rateInApp.setBackgroundColor( Color.WHITE );
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_Privacy:
                inquiry.setBackgroundColor( Color.WHITE );
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.GRAY );
                rateInApp.setBackgroundColor( Color.WHITE );
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_RateInApp:
                inquiry.setBackgroundColor( Color.WHITE );
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.WHITE);
                rateInApp.setBackgroundColor( Color.GRAY);
                version.setBackgroundColor( Color.WHITE );
                break;
            case R.id.btn_Version:
                inquiry.setBackgroundColor( Color.WHITE );
                how_To.setBackgroundColor( Color.WHITE);
                app_Info.setBackgroundColor( Color.WHITE );
                logout.setBackgroundColor( Color.WHITE );
                login.setBackgroundColor( Color.WHITE );
                user_Info.setBackgroundColor( Color.WHITE );
                basic_Info.setBackgroundColor( Color.WHITE);
                badge_Cllection.setBackgroundColor( Color.WHITE );
                new_User.setBackgroundColor( Color.WHITE);
                privacy.setBackgroundColor( Color.WHITE);
                rateInApp.setBackgroundColor( Color.WHITE);
                version.setBackgroundColor( Color.GRAY );
                break;


                default:
                    inquiry.setBackgroundColor( Color.WHITE );
                    how_To.setBackgroundColor( Color.WHITE);
                    app_Info.setBackgroundColor( Color.WHITE );
                    logout.setBackgroundColor( Color.WHITE );
                    login.setBackgroundColor( Color.WHITE );
                    user_Info.setBackgroundColor( Color.WHITE );
                    basic_Info.setBackgroundColor( Color.WHITE);
                    badge_Cllection.setBackgroundColor( Color.WHITE );
                    new_User.setBackgroundColor( Color.WHITE);
                    privacy.setBackgroundColor( Color.WHITE);
                    rateInApp.setBackgroundColor( Color.WHITE);
                    version.setBackgroundColor( Color.WHITE );
                    break;
        }

    }
}





