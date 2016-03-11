package class7.android.week3_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/12/7.
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tv1= (TextView) findViewById(R.id.tv1);
        TextView tv2= (TextView) findViewById(R.id.tv2);
        CheckBox ck= (CheckBox) findViewById(R.id.ck);

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        int age=intent.getIntExtra("age", 0);

        boolean pass=intent.getBooleanExtra("pass",false);

        tv1.setText(name);
        tv2.setText(Integer.toString(age));
        ck.setChecked(pass);

    }
}
