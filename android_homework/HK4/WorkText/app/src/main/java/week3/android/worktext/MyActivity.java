package week3.android.worktext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyActivity extends Activity {
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private int width;
    private static final int CHANGE = 1;
    int[] color = {0xff000000, 0xffffff00, 0xffcd0000};
    int i = 0;
    int left = 0;
    int tt=0;
    int left3=0;
    int tw=0;
    int left2=0;
    boolean UITHREAD;
    boolean HANDLER;
    boolean POSTTHREAD;
    //runnable text3的UI

    Thread onUiThread;

    Thread handlerThread;

    Thread postThread;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case CHANGE:
                    text1.setTextColor(color[i]); //不能在这里面加入死循环
                    text1.setLeft(left);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button time = (Button) findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this,TimeBell.class);
                startActivity(intent);
            }
        });

        Button clock = (Button) findViewById(R.id.clock);
        clock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent jump = new Intent(MyActivity.this,TimeShow.class);
                startActivity(jump);
            }
        });

//        layout = (LayoutView) findViewById(R.id.layout);
        text1 = (TextView) findViewById(R.id.text);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;

        left = text1.getLeft();
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HANDLER=true;
                        while (HANDLER){
                            i++;
                            i %= 3;
                            left += 10;
                            left %= width;
                            Message message = new Message();
                            message.what = CHANGE;
                            handler.sendMessage(message);
                            try {
                                Thread.sleep(400);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });

                handlerThread.start();
            }
        });
        Button handlerStop=(Button) findViewById(R.id.handler_stop);
        handlerStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HANDLER=false;
            }
        });


        Button ViewPost = (Button) findViewById(R.id.ViewPost);
        text2 = (TextView) findViewById(R.id.post);
        ViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               UIStartPost();
            }
        });
        Button ViewStop=(Button) findViewById(R.id.Post_Stop);
        ViewStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POSTTHREAD=false;
            }
        });


        text3 = (TextView) findViewById(R.id.onUi);
        final Button UiThread = (Button) findViewById(R.id.uiThread);
        UiThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnUiThread();
            }
        });
        Button UiStop = (Button) findViewById(R.id.UiStop);
        UiStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UITHREAD=false;
            }
        });
    }

    public void UIStartPost(){
            postThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    left2=text2.getLeft();
                    POSTTHREAD=true;
                    while(POSTTHREAD) {
                        tw++;
                        tw %= 3;
                        left2 += 10;
                        left2 %= width;
                        text2.post(new Runnable() {
                            @Override
                            public void run() {
                                text2.setTextColor(color[tw]);
                                text2.setLeft(left2);
                            }
                        });
                        try {
                            Thread.sleep(400);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        postThread.start();
        }

    public void OnUiThread(){
        onUiThread = new Thread(new Runnable() {
            @Override
            public void run() {
                left3=text3.getLeft();
                UITHREAD=true;
                while(UITHREAD) {
                    tt++;
                    tt %= 3;
                    left3 += 10;
                    left3 %= width;
                    MyActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text3.setTextColor(color[tt]);
                            text3.setLeft(left3);
                        }
                    });
                    try {
                        Thread.sleep(400);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        onUiThread.start();
    }
}