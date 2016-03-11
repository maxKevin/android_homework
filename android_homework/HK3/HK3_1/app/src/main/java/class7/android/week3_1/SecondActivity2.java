package class7.android.week3_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Jason Song(wolfogre@outlook.com) on 01/31/2016.
 */
public class SecondActivity2 extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		TextView tv1 = (TextView) findViewById(R.id.tv1);
		TextView tv2 = (TextView) findViewById(R.id.tv2);
		CheckBox ck = (CheckBox) findViewById(R.id.ck);

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("info");
		String name = bundle.getString("name");
		int age = bundle.getInt("age", 0);
		boolean pass = bundle.getBoolean("pass", false);

		tv1.setText(name);
		//tv2.setText(Integer.toString(age));
		// Never call Number#toString() to format numbers;
		// it will not handle fraction separators and locale-specific digits properly.
		// Consider using String#format with proper format specifications (%d or %f) instead.
		tv2.setText(String.format("%d", age));
		ck.setChecked(pass);
	}
}