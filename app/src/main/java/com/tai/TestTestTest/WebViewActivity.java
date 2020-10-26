package com.tai.TestTestTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();
        myProcess();
    }

    private void initView() {
        webView = findViewById(R.id.webView);
    }

    private void myProcess() {
        String url = "http://www.baidu.com";
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        MyLog.i("TAG", new DebugInfo(), "canGoBack: " + webView.canGoBack());
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }
}
