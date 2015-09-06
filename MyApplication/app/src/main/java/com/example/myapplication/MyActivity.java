package com.example.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MyActivity extends Activity {
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);// get example
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               Intent intent = new Intent("com.example.broad.LOCAL_BROADCAST");
              localBroiadcastManager.sendBroadcast(intent);
           }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broad.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registterReceiver(localReceiver,intentFilter);
    }
    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context , Intent intent){
            Toast.makeText(context , "received local broadcast" , Toast.LENGTH_LONG).show();
        }
    }
}
