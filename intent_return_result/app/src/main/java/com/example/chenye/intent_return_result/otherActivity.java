package com.example.chenye.intent_return_result;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by chenye on 2018/2/7.
 */

public class otherActivity extends Activity {
    // private Button otherBtn;
    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other);
        //otherBtn = findViewById(R.id.otherBtn);
        textView = this.findViewById(R.id.otherMsg);
        editText = this.findViewById(R.id.otherEditText);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("sendMsg");
        textView.setText(msg);
    }

    public void otherBtn(View view) {
        Intent intent = new Intent();
        String returnMsg = editText.getText().toString();
        if (returnMsg == null || returnMsg.length() <= 0) {
            intent.putExtra("default", "what?");
            setResult(1, intent);
        } else {
            intent.putExtra("returnMsg", returnMsg);
            // 通过Intent对象返回结果，setResult方法
            setResult(2, intent);
        }

        finish();  // 表示结束当前Activity的生命周期

    }
}
