package com.exmple.chenye.broadcasttest3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;



public class MyBroadcastReceiver3 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("intent", intent.getAction());
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "received in third BroadcastReceiver", Toast.LENGTH_LONG).show();
        Log.d("Third toast", "Third toast");
    }
}
