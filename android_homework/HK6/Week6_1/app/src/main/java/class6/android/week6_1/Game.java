package class6.android.week6_1;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game extends AppCompatActivity {
    private static final String TAG="Debug";
    private static final int SHOW_GAMELIST=1;
    SharedPreferences preferences;

    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");

    private MyDataBase myDataBase;

    private ListView gameList;
    private Button gameAdd;
    private EditText gameName;
    private EditText gameTime;

    List<Map<String,Object>> list;

    //UI更新
    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOW_GAMELIST:
                    list=(List<Map<String,Object>>)msg.obj;
                    //新建GameAdapter类继承自ArrayAdapter，显示比赛列表
                    GameAdapter adapter=new GameAdapter(Game.this,R.layout.gamelist,list);
                    gameList.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameList=(ListView)findViewById(R.id.gameList);
        //新比赛添加按钮
        gameAdd=(Button)findViewById(R.id.Addgame);
        gameName=(EditText)findViewById(R.id.GameName);
        gameTime=(EditText)findViewById(R.id.GameTime);

        myDataBase=new MyDataBase(Game.this,1);

        //若数据库中比赛数等于0，则创建默认的比赛数据库，包括三个比赛和其中一个比赛中的人员和比分数据
        if(myDataBase.DataCount("game")==0)
        {
            Log.i(TAG,"1");
            createdefaultdatabase();
        }
        //显示列表
        showgamelist();

        gameAdd.setOnClickListener(AddGame);
        //长按比赛条目事件
        gameList.setOnItemLongClickListener(SetGame);

    }

    View.OnClickListener AddGame =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(gameName.getText().toString().equals("") || gameTime.getText().toString().equals("")) {
                gameName.setText("");
                gameTime.setText("");
                return;
            }
            ContentValues cv=new ContentValues();
            cv.put("name", gameName.getText().toString());
            cv.put("date",gameTime.getText().toString());
            preferences=getSharedPreferences("setting",MODE_PRIVATE);
            cv.put("groupNum",String.valueOf(preferences.getInt("groupNum", 0)));
            cv.put("groupPlayerNum",String.valueOf(preferences.getInt("groupPlayerNum", 0)));
            myDataBase.insert("game", cv);
            gameName.setText("");
            gameTime.setText("");
            showgamelist();
        }
    };

    AdapterView.OnItemLongClickListener SetGame = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Adapter adapter=parent.getAdapter();
            Map<String, Object> map = (Map<String, Object>) adapter.getItem(position);

            Intent intent=new Intent(Game.this,Group.class);
            intent.putExtra("game",(String)map.get("name"));
            startActivity(intent);
            return false;
        }
    };

    public void createdefaultdatabase(){
        //默认比赛表
        ContentValues cv=new ContentValues();
        String[] name={"世界锦标赛","奥运会","黄金大奖赛"};
        for(int i=0;i<3;i++){
            cv.put("name",name[i]);
            cv.put("date","2015-1-10");
            cv.put("groupNum",4);
            cv.put("groupPlayerNum",4);

            myDataBase.insert("game",cv);
        }
        //默认名单表
        String[] player={"王励勤","王皓","马林","波尔","柳承敏"};
        String[] groupNo={"1","1","2","3","4"};
        ContentValues cv2=new ContentValues();
        for(int n=0;n<5;n++){
            cv2.put("name",player[n]);
            cv2.put("game","世界锦标赛");
            cv2.put("groupNo",groupNo[n]);

            myDataBase.insert("player",cv2);
        }
        //默认比分表
        String[] player1={"王励勤","王皓"};
        String[] player2={"王皓","王励勤"};
        String[] score1={"3","5"};
        String[] score2={"5","3"};
        String scoregame="世界锦标赛";
        String scoregroupNo="1";
        ContentValues cv3=new ContentValues();
        for (int n=0;n<2;n++){
            cv3.put("player1",player1[n]);
            cv3.put("player2",player2[n]);
            cv3.put("score1",score1[n]);
            cv3.put("score2",score2[n]);
            cv3.put("game",scoregame);
            cv3.put("groupNo",scoregroupNo);

            myDataBase.insert("score",cv3);
        }
        System.out.println("create default successfully");
    }


    public void showgamelist(){
        list=new ArrayList<Map<String,Object>>();
        Cursor cs=myDataBase.query("game");

        while(cs.moveToNext()){
            String gameName=cs.getString(cs.getColumnIndex("name"));
            int teamNum = cs.getInt(cs.getColumnIndex("groupNum"));
            int teamPlayerNum=cs.getInt(cs.getColumnIndex("groupPlayerNum"));
            String str=cs.getString(cs.getColumnIndex("date"));
            Date date=null;
            try{
                date=format.parse(str);
            }catch (Exception e){}

            Map<String,Object> map=new HashMap<String, Object>();
            map.put("name",gameName);
            map.put("groupNum",teamNum);
            map.put("groupPlayerNum",teamPlayerNum);
            map.put("date",date);

            list.add(map);
        }

        Message msg=new Message();
        msg.what=SHOW_GAMELIST;
        msg.obj=list;
        mhandler.sendMessage(msg);
    }
}
