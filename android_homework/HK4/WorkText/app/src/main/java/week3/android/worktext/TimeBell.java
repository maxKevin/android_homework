package week3.android.worktext;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

/**
 * Created by king on 2015/12/23.
 */
public class TimeBell extends Activity {
    MyView time;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time);
        time = (MyView) findViewById(R.id.time);
        float[] a = getTime();
        time.setTime(a);
        Log.w("th",String.valueOf(a[0]));
        Log.w("tm",String.valueOf(a[1]));
        Log.w("ts",String.valueOf(a[2]));
        timeRun();
    }

    public void timeRun(){
        new Thread(new Runnable() {
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                time.postInvalidate();
                }
            }
        }).start();
    }

    public float[] getTime(){
        Time time = new Time();
        time.setToNow();
        float[] t = new float[3];
        if(time.hour <= 12)
            t[0]=time.hour*360f/12;
        else
            t[0]=(time.hour-12)*360f/12;
        t[1]=time.minute*6f;
        t[2]=time.second*6f;
        t[0]+=time.minute*30f/60;
        t[1]+=time.second/10f;
        return t;
    }

}
