package com.example.chenye.intent_return_result;

import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    // private Button addBtn, mulBtn;
    private final static int ADDREQUESTCODE = 1; // 返回的结果码
    private final static int MULREQUESTCODE = 2; // 返回的结果码

    private EditText addNum1, addNum2, mulNum1, mulNum2;
    private EditText addResult, mulResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // addBtn  = this.findViewById(R.id.addBtn);
        addNum1 = this.findViewById(R.id.addNum1);
        addNum2 = this.findViewById(R.id.addNum2);
        addResult = this.findViewById(R.id.addResult);

        mulNum1 = this.findViewById(R.id.mulNum1);
        mulNum2 = this.findViewById(R.id.mulNum2);
        mulResult = this.findViewById(R.id.mulResult);
        mulResult.clearFocus();
        // mulBtn  = this.findViewById(R.id.mulBtn);


    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public void addBtn(View view) {
        String a = addNum1.getText().toString();
        String b = addNum2.getText().toString();
        Intent intent = new Intent(this, addResultActivity.class);

        if (a == null || a.length() <= 0 || b == null || b.length() <= 0) {
            Toast.makeText(this, "请输入完整参数！", Toast.LENGTH_SHORT).show();
        } else if (isNumeric(a) && isNumeric(b)) {
            intent.putExtra("a", Integer.valueOf(a));
            intent.putExtra("b", Integer.valueOf(b));
            startActivityForResult(intent, ADDREQUESTCODE);  // 表示可以获取结果

        } else {
            Toast.makeText(this, "请输入整数参数！", Toast.LENGTH_SHORT).show();
        }


    }


    public void mulBtn(View view) {
        String a = mulNum1.getText().toString();
        String b = mulNum2.getText().toString();
        Intent intent = new Intent(this, mulResultActivity.class);

        if (a == null || a.length() <= 0 || b == null || b.length() <= 0) {
            Toast.makeText(this, "请输入完整参数！", Toast.LENGTH_SHORT).show();
        } else if (isNumeric(a) && isNumeric(b)) {
            intent.putExtra("a", Integer.valueOf(a));
            intent.putExtra("b", Integer.valueOf(b));
            startActivityForResult(intent, MULREQUESTCODE);  // 表示可以获取结果

        } else {
            Toast.makeText(this, "请输入整数参数！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
        if(resultCode == 2){
            if(requestCode == ADDREQUESTCODE){
                int addResultValue = data.getIntExtra("addResultValue", 0);
                addResult.setText(String.valueOf(addResultValue));
            }
            if(requestCode == MULREQUESTCODE){
                int addResultValue = data.getIntExtra("mulResultValue", 0);
                mulResult.setText(String.valueOf(addResultValue));

            }

        }
        */
        if (requestCode == ADDREQUESTCODE) {
            if (resultCode == 1) {
                String defaultValue = data.getStringExtra("default");
                addResult.setText(defaultValue);

            }
            if (resultCode == 2) {
                int addResultValue = data.getIntExtra("addResultValue", 0);

                addResult.setText(String.valueOf(addResultValue));
            }

        }
        if (requestCode == MULREQUESTCODE) {
            if (resultCode == 1) {
                String defaultValue = data.getStringExtra("default");
                mulResult.setText(String.valueOf(defaultValue));

            }
            if (resultCode == 2) {
                int mulResultValue = data.getIntExtra("mulResultValue", 0);
                mulResult.setText(String.valueOf(mulResultValue));
            }
        }

    }


}
