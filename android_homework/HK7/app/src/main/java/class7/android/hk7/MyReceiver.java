package class7.android.hk7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    private MainActivity mainActivity;
    public MyReceiver(){

    }
    public MyReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        boolean type = intent.getBooleanExtra("type", true);
        Log.w("number 1", "run" + " " + type);
        if(type) {
            Intent server = new Intent(context, MyService.class);
            context.startService(server);
            Log.w("number activity", "test");
        }
        else{
            String number = intent.getStringExtra("number");
            MainActivity.number = Integer.valueOf(number);
            Log.w("number",number);
        }
    }
}
