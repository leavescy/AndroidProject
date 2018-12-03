package com.exmple.chenye.webviewtest;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if(0 != (getApplicationInfo().flags = ApplicationInfo.FLAG_DEBUGGABLE)){
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
    }
}
