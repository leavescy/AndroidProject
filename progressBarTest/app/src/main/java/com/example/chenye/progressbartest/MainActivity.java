package com.example.chenye.progressbartest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button button;
    private Button button2;
    private EditText editText;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;
    private TextView textView;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.Button1);
        button2 = findViewById(R.id.Button2);
        editText = findViewById(R.id.editText);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        textView = findViewById(R.id.textview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.Button1 :
                if(progressBar.getVisibility() == View.GONE){
                    progressBar.setVisibility(View.VISIBLE);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                }
                break;
            case R.id.Button2 :
                new Thread(){
                    @Override
                    public void run() {
                        int i = 0;
                        while(i < 100){
                            i++;
                            try {
                                Thread.sleep(80);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            final int j=i;
                            progressBar2.setProgress(i);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(j+"%");
                                }
                            });
                        }
                    }
                }.start();
                break;

            default:
                break;
        }
    }
}
