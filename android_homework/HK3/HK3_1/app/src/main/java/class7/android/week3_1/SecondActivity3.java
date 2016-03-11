package class7.android.week3_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Jason Song(wolfogre@outlook.com) on 01/31/2016.
 */
public class SecondActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tv1= (TextView) findViewById(R.id.tv1);
        TextView tv2= (TextView) findViewById(R.id.tv2);
        CheckBox ck= (CheckBox) findViewById(R.id.ck);

        MyApplication ma = (MyApplication)getApplication();

        tv1.setText(ma.getName());
        tv2.setText(String.format("%d", ma.getAge()));
        ck.setChecked(ma.isPass());

    }
}
