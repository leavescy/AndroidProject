package com.example.chenye.globalvariables;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chenye on 2018/1/10.
 */

public class MyApp extends Application {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
