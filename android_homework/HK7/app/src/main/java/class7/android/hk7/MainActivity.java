package class7.android.hk7;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        Button button1 = (Button) findViewById(R.id.button2);
        final TextView textView = (TextView) findViewById(R.id.textView);
        MyReceiver myReceiver = new MyReceiver(MainActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("class7.android.hk7.SEND_BROADCAST");
                intent.putExtra("type", true);
                sendBroadcast(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broadcast = new Intent("class7.android.kh7.SEND_BROADCAST");
                sendBroadcast(broadcast);
                try {
                    Thread.sleep(1000);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                if(number != -1)
                    textView.setText("get number "+ String.valueOf(number));
                else
                    Toast.makeText(MainActivity.this,"Didn't get number",Toast.LENGTH_SHORT).show();
            }
        });
        IntentFilter intentFilter = new IntentFilter("class7.android.hk7.SEND_BORADCAST");
        registerReceiver(myReceiver,intentFilter);
    }
///*    @Override
//    protected void onActivityResult(int requestCode , int resultCode , Intent data){
//        Bundle bundle;
//        switch(requestCode){
//            case 1:
//                if(resultCode == RESULT_OK){
//                    bundle = data.getExtras();
//                    number = bundle.getInt("number");
//
//                }
//        }
//    }*/

    public void getNumber(String number){
        this.number = Integer.valueOf(number);
    }
}
