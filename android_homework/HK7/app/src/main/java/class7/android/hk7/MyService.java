package class7.android.hk7;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Random;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags,int startId){
        Random random = new Random();
        int number = random.nextInt(20);
        Intent server = new Intent("class7.android.hk7.SEND_BROADCAST");
        try{
            Thread.sleep(200);
        }catch(Exception e){
            e.printStackTrace();
        }
        server.putExtra("type",false);
        server.putExtra("number", String.valueOf(number));
        sendBroadcast(server);
        return super.onStartCommand(intent,flags,startId);
    }
}
