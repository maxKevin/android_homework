package class6.android.week6_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    private String[] title={"新比赛","积分查询","赛场新闻","论坛"};
    private GridView gridView;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView=(GridView)findViewById(R.id.titleGrid);
        preferences = getSharedPreferences("setting", MODE_PRIVATE);
        if(preferences.getInt("groupNum", 0)==0){
            SharedPreferences.Editor editor=getSharedPreferences("setting",MODE_PRIVATE).edit();
            editor.putInt("groupNum",4);
            editor.putInt("groupPlayerNum",4);
            editor.commit();
        }


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, title);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(jump);

    }

    AdapterView.OnItemClickListener jump = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Adapter adapter=parent.getAdapter();
            String title=(String)adapter.getItem(position);
            if(title.equals("新比赛")){
                Intent intent=new Intent(MainActivity.this,Game.class);
                startActivity(intent);
            }
        }
    };
}
