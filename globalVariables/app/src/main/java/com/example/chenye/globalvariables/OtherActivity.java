package com.example.chenye.globalvariables;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by chenye on 2018/1/10.
 */

public class OtherActivity extends AppCompatActivity {
    private MyApp myApp;
    private TextView textView;
    private TextView ClipBoardTextView;
    private TextView StaticTextView;
    public static String name;
    public static int age;

    String TAG = "clickType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        Intent intent = getIntent();
        String clickType = intent.getStringExtra("clickType");

        Log.i(TAG, String.valueOf(clickType));
        /**
            条件1：全局变量传递数据；条件2：剪贴板传递数据；条件3：静态变量传递数据

         */
        if("1".equals(clickType)){
            textView = findViewById(R.id.msgText);
            myApp = (MyApp) getApplication();
            textView.setText("全局变量 :" + myApp.getText());
        }
        else if("2".equals(clickType)){
            /* 剪贴板简单数据
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String msg = clipboardManager.getText().toString();
            ClipBoardTextView = findViewById(R.id.ClipBoardMsgText);
            ClipBoardTextView.setText("ClipBoardText:" + msg);

            */

            // 剪贴板复杂数据处理
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String msg = clipboardManager.getText().toString();
            ClipBoardTextView = findViewById(R.id.ClipBoardMsgText);

            byte[] base64_byte = Base64.decode(msg, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(base64_byte);
            ObjectInputStream objectInputStream = null;
            try {
                objectInputStream = new ObjectInputStream(byteArrayInputStream);
                MyData myData = (MyData) objectInputStream.readObject();

                ClipBoardTextView.setText(myData.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        else{
            StaticTextView = findViewById(R.id.StaticMsgText);
            StaticTextView.setText("--name-->" + name + "---age-->"+ age);
        }




    }
}
