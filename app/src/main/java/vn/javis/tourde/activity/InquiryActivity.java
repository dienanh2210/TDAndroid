package vn.javis.tourde.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import vn.javis.tourde.R;

public class InquiryActivity extends AppCompatActivity {
    TextView tv_back_privacy;
    private WebView webView;
    String strUrl=" https://www.app-tour-de-nippon.jp/test/contact_app/  " ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.inquiry_fragment );
        tv_back_privacy=findViewById( R.id.tv_back_privacy );
        tv_back_privacy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InquiryActivity.this, MenuPageActivity.class);
                startActivityForResult(intent, 1);
            }
        } );


        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(strUrl);
        webView.setWebViewClient(new WebViewClient());

    }
}
