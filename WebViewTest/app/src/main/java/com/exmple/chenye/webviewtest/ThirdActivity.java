package com.exmple.chenye.webviewtest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.webkit.WebSettings;
import com.tencent.smtt.sdk.WebView;
import android.webkit.WebViewClient;


public class ThirdActivity extends AppCompatActivity {

    private WebView X5WebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        X5WebView = (com.tencent.smtt.sdk.WebView)findViewById(R.id.tbsContent);
        X5WebView.loadUrl("http://fa.163.com/t/help/centerviewforh5");
        WebSettings webSettings = X5WebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        X5WebView.setWebViewClient(new WebViewClient());
    }
}
