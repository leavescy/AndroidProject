package com.example.chenye.intent_return_result;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by chenye on 2018/2/7.
 */

public class addResultActivity extends Activity {
    // private Button addBtn;
    private TextView textView;
    private EditText editText, otherEditText;
    private static final int OTHERREQUESTCODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addresult);
       // addBtn = findViewById(R.id.addBtn);
        textView = this.findViewById(R.id.addmsg);
        editText = this.findViewById(R.id.addResult);
        otherEditText = this.findViewById(R.id.otherEditText);
        Intent intent = getIntent();
        int a = intent.getIntExtra("a", 0);
        int b = intent.getIntExtra("b", 0);
        textView.setText(a + "+" + b + "=" + "?");
    }
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    public void addBtn(View view) {
        Intent intent = new Intent();
        String addResultValue = editText.getText().toString();

        if(addResultValue == null || addResultValue.length() <=0){
            intent.putExtra("default", "?");
            setResult(1, intent);
            finish();

        }
        else if(isNumeric(addResultValue)){

            intent.putExtra("addResultValue", Integer.parseInt(addResultValue));
            // 通过Intent对象返回结果，setResult方法
            setResult(2, intent);
            finish();  // 表示结束当前Activity的生命周期
        }
        else{
            Toast.makeText(this, "请输入整数！", Toast.LENGTH_SHORT).show();
        }


    }

    public void otherBtn(View view) {
        Intent intent = new Intent(addResultActivity.this, otherActivity.class);
        String msg = otherEditText.getText().toString();
        intent.putExtra("sendMsg", msg);
        startActivityForResult(intent, OTHERREQUESTCODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         /*
        if(resultCode == 2){
            if(requestCode == OTHERREQUESTCODE){
                String returnMsg = data.getStringExtra("returnMsg");
                otherEditText.setText(returnMsg);
            }
        }
        if(resultCode == 1){
            if(requestCode == OTHERREQUESTCODE){
                String returnMsg = data.getStringExtra("default");
                otherEditText.setText(returnMsg);

            }
        }
        */
        if(requestCode == OTHERREQUESTCODE){
            if(resultCode == 2){
                String returnMsg = data.getStringExtra("returnMsg");
                otherEditText.setText(returnMsg);
            }
            if(resultCode == 1){
                String returnMsg = data.getStringExtra("default");
                otherEditText.setText(returnMsg);
            }
        }
    }
}
