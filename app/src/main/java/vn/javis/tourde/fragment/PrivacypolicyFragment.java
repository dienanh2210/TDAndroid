package vn.javis.tourde.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import vn.javis.tourde.R;
import vn.javis.tourde.activity.MenuPageActivity;

public class PrivacypolicyFragment extends AppCompatActivity {
    TextView tv_back_privacy;
    private WebView webView;
    String strUrl=" http://roots-sports.jp/privacypolicy.html " ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.privacy_policy_fragment );
        tv_back_privacy=findViewById( R.id.tv_back_privacy );
        tv_back_privacy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrivacypolicyFragment.this, MenuPageActivity.class);
                //basic_Info.setVisibility(View.VISIBLE);
                startActivityForResult(intent, 1);
            }
        } );



        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(strUrl);
        webView.setWebViewClient(new WebViewClient());

    }
}
