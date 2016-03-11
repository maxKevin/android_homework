package class6.android.week6_1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameSet extends AppCompatActivity {

    private MyDataBase myDataBase;

    private String gameName;
    private TextView gameSetName=null;
    private EditText groupNum=null;
    private EditText playerNum=null;
    private Button save=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameset);
        gameSetName=(TextView)findViewById(R.id.gameSetName);
        groupNum=(EditText)findViewById(R.id.gameSetGroupNum);
        playerNum=(EditText)findViewById(R.id.gameSetPlayerNum);
        save=(Button)findViewById(R.id.gameSetSave);

        myDataBase=new MyDataBase(GameSet.this,1);

        Intent intent=getIntent();
        gameName=intent.getStringExtra("game");
        gameSetName.setText(gameName);

        //先从数据库中读取比赛的原先小组数和小组人数
        String sql="select groupNum,groupPlayerNum from game where name='"+
                gameName+"'";
        Cursor cs=myDataBase.select(sql);
        //System.out.println("123");
        while(cs.moveToNext()){
            String gn=String.valueOf(cs.getInt(cs.getColumnIndex("groupNum")));
            String pn=String.valueOf(cs.getInt(cs.getColumnIndex("groupPlayerNum")));
            //System.out.println(gn);
            groupNum.setText(gn);
            playerNum.setText(pn);
        }
        //修改比赛的小组数和小组人数
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDataBase.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("groupNum", groupNum.getText().toString());
                cv.put("groupPlayerNum", playerNum.getText().toString());
                db.update("game", cv, "name=?", new String[]{gameName});
                finish();
            }
        });
    }

}
