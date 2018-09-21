package com.exmple.chenye.databasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper;
    private Button dbHelperBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelperBtn = findViewById(R.id.dbHelperBtn);
        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);
        dbHelperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDatabaseHelper.getWritableDatabase();
            }
        });
    }
}
