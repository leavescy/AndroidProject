package com.exmple.chenye.notificationtest;


import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        NotificationManager managers = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        managers.cancel(1);

    }



}
