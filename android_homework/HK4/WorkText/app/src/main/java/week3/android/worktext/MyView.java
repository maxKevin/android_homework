package week3.android.worktext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by king on 2015/12/23.
 */
public class MyView extends View {
    private float s;
    private float m;
    private float h;
    float R = 500;
    float x = 500;
    float y = 500;
    float distance=40;
    float secondd=50; //second length
    float minuted=100;//minute length
    float hourd=150;//hour length

    public MyView(Context context,AttributeSet as){
        super(context,as);
    }
    @Override
    public void onDraw(Canvas canvas){
        s++;
        if(s%60 < 1){
            m++;
            if(s==360)
                s=0;
            if(m%12< 1){
                h++;
                if(m==360)
                    m=0;
            }
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Path circle = new Path();
        circle.addCircle(x,y,R, Path.Direction.CW);
        paint.setTextSize(40f);
        for(int j = 0 ; j<=11 ;j++){
            canvas.rotate(30f,x,y);
            canvas.drawText(String.valueOf(j+1),x,distance,paint);
        }

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawPath(circle, paint);

        paint.setStrokeWidth(15f);
        paint.setColor(Color.RED);
        canvas.rotate(h,x,y);
        canvas.drawLine(x,y,x,hourd,paint);
        canvas.rotate(-h,x,y);

        paint.setStrokeWidth(10f);
        paint.setColor(Color.BLUE);
        canvas.rotate(m,x, y);
        canvas.drawLine(x, y, x,minuted, paint);
        canvas.rotate(-m,x,y);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5f);
        canvas.rotate(s, x, y);
        canvas.drawLine(x, y, x, secondd, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x,y,15,paint);
    }

    public void setTime(float[] a){
        h=a[0];
        m=a[1];
        s=a[2];
    }

}
