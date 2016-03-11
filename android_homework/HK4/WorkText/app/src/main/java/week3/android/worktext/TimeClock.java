package week3.android.worktext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Handler;

/**
 * Created by king on 2015/12/25.
 */
public class TimeClock extends View {
    private int hour;
    private int minute;
    private int second;
    int mark;
    float rectx;
    float recty;
    float length;
    float high;
    int color;

    public TimeClock(Context context ,AttributeSet as){
        super(context,as);
        TypedArray a =context.obtainStyledAttributes(as,R.styleable.show);
        rectx   = a.getFloat(R.styleable.show_rectx,100);
        recty   = a.getFloat(R.styleable.show_recty,100);
//        TypedArray a = context.obtainStyledAttributes(as,R.styleable.Clock);
        length  = a.getFloat(R.styleable.show_length,500);
        high    = a.getFloat(R.styleable.show_high,100);
        color   = a.getInteger(R.styleable.show_colour,0xff000000);
        mark    = a.getInteger(R.styleable.show_mark,12);
    }

    @Override
    public void onDraw(Canvas canvas){
        second++;
        if(second==60){
            minute++;
            second=0;
            if(minute==60 ){
                hour++;
                minute=0;
            }
        }
        Paint paint = new Paint();
        Path path = new Path();
        paint.setStyle(Paint.Style.STROKE);

        paint.setColor(color);
        Path rect = new Path();
        rect.moveTo(rectx,recty);
        rect.lineTo(rectx + length, recty);
        rect.lineTo(rectx + length, recty + high);
        rect.lineTo(rectx,recty+high);
        rect.lineTo(rectx,recty);
        rect.close();
        path.moveTo(rectx,recty+high);
        path.lineTo(rectx+length,recty+high);
        canvas.drawPath(rect,paint);
        paint.setTextSize(50f);
        String time = String.valueOf(hour)+":"+String.valueOf(minute)+":"+String.valueOf(second);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawTextOnPath(time,path,0f,-5f,paint);
    }

    public void getTime(String time){
        String[] array = time.split(":");
        hour = Integer.valueOf(array[0]);
        minute = Integer.valueOf(array[1]);
        second = Integer.valueOf(array[2]);
    }

}
