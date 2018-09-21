package com.exmple.chenye.databasetest;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper;
    private Button dbHelperBtn;
    private Button addDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelperBtn = findViewById(R.id.dbHelperBtn);
        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 4);
        dbHelperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDatabaseHelper.getWritableDatabase();
            }
        });

        addDataBtn = findViewById(R.id.addDataBtn);
        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values);
            }
        });
    }
}
