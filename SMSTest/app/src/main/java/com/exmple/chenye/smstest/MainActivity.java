package com.exmple.chenye.smstest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText to;
    private EditText msgInput;
    private Button send;
    private TextView sender;
    private TextView content;
    private IntentFilter sendFilter, receiveFilter;
    private MessageReceiver messageReceiver;
    private SendStatusReceiver sendStatusReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        to = findViewById(R.id.to);
        msgInput = findViewById(R.id.msg_input);
        send = findViewById(R.id.send);

        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver, sendFilter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                Intent sendIntent = new Intent("SENT_SMS_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, sendIntent, 0);
                smsManager.sendTextMessage(to.getText().toString(), null, msgInput.getText().toString(), pi, null);
            }
        });
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

    class SendStatusReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode() == RESULT_OK){
                Toast.makeText(context, "Send succeeded", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Send failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
