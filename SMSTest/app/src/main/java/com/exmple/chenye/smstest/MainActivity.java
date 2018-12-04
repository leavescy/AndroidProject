package com.exmple.chenye.smstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView sender;
    private TextView content;
    private IntentFilter receiveFilter;
    private MessageReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sender = findViewById(R.id.sender);
        content = findViewById(R.id.content);
        receiveFilter = new IntentFilter();
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, receiveFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }

    class MessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("tags:",  "onReceive");

            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[])bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for(int i = 0; i < messages.length; i++){
                messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            }
            String address = messages[0].getDisplayOriginatingAddress();
            StringBuffer fullMessage = new StringBuffer();
            for(SmsMessage message: messages){
                fullMessage.append(message.getDisplayMessageBody());
            }
            sender.setText(address);
            content.setText(fullMessage.toString());

        }
    }
}
