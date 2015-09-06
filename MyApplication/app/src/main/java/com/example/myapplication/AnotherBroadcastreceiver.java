package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by king on 2015/8/7.
 */
public class AnotherBroadcastreceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context , Intent intent) {
        Toast.makeText(context , "Another Broadcast Receiver" , Toast.LENGTH_LONG).show();
    }
}
