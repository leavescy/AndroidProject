package com.exmple.chenye.notificationtest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button sendChat;
    private Button sendSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendChat = findViewById(R.id.send_chat);
        sendSubscribe = findViewById(R.id.send_subscribe);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }
        sendChat.setOnClickListener(this);
        sendSubscribe.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_subscribe :
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(this);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    builder.setChannelId("subscribe");
                }
                builder.setSmallIcon(R.drawable.notify);
                builder.setTicker("This is ticker text.");
                builder.setWhen(System.currentTimeMillis());
                builder.setContentTitle("收到一条订阅消息");
                builder.setContentText("地铁沿线30分钟商铺招商中.");
                builder.setNumber(10);
                manager.notify(1,  builder.build());
                break;
            case R.id.send_chat:
                NotificationManager managers = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builders = new Notification.Builder(this);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel channel = managers.getNotificationChannel("chat");
                    builders.setChannelId("chat");
                    if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                        Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                        startActivity(intent);
                    }

                }
                builders.setSmallIcon(R.drawable.notify);
                builders.setTicker("This is ticker text.");
                builders.setWhen(System.currentTimeMillis());
                builders.setContentTitle("收到一条聊天消息");
                builders.setContentText("今天几点下班啊?");
                builders.setNumber(10);
                managers.notify(1,  builders.build());
                break;

            default:
                break;
        }
    }


}
