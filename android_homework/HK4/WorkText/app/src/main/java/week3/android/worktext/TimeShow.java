package week3.android.worktext;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.text.format.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by king on 2015/12/25.
 */
public class TimeShow extends Activity{
    private TimeClock timeClock;
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.time_clock);
        timeClock = (TimeClock) findViewById(R.id.clock);
        String time = getTime();
        timeClock.getTime(time);
        timeRun();
    }

    public String getTime(){
//        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
//        return sdf.format(new Date());
        Time time = new Time();
        time.setToNow();
        return String.valueOf(time.hour+":"+time.minute+":"+time.second);
    }

    public void timeRun(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timeClock.postInvalidate();
                }
            }
        }).start();
    }
}
