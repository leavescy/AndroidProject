package com.example.chenye.globalvariables;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private MyApp myApp;
    private String clickType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    // 全局变量传递数据
    public void clickButton(View view) {
        clickType = "1";
        myApp = (MyApp) getApplication();
        myApp.setText("简单数据");
        Intent intent = new Intent(this, OtherActivity.class);
        intent.putExtra("clickType", clickType);
        startActivity(intent);
    }
    // 采用剪贴板传递数据，简单数据&复杂数据
    public void ClickClipBoardButton(View view) {
        clickType = "2";
        Log.i("clickType->", String.valueOf(clickType));
        /*  方法一：剪贴板传递简单数据
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String text = "简单数据";
        clipboardManager.setText(text);
        Intent intent = new Intent(this, OtherActivity.class);
        intent.putExtra("clickType", clickType);
        startActivity(intent);
  */


        // 方法二：剪贴板传递复杂数据
        MyData myData = new MyData("剪贴板复杂数据", 2018);
        // 将对象转换为字符串
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String base64String = "";
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(myData);
            base64String = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClipboardManager clipboardManager1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager1.setText(base64String);
        Intent intent1 = new Intent(this, OtherActivity.class);
        intent1.putExtra("clickType", clickType);
        startActivity(intent1);

    }
     // 使用静态变量传递数据
    public void ClickStaticButton(View view) {
        clickType = "3";
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, OtherActivity.class);
        OtherActivity.name = "静态变量";
        OtherActivity.age = 24;
        intent.putExtra("clickType", clickType);
        startActivity(intent);
    }
}
