package class8.android.week8;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        if(myDataBase.DataCount("player")==0)
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

    //添加比赛
    View.OnClickListener AddGame =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            preferences=getSharedPreferences("setting",MODE_PRIVATE);
            //读取games.xml中的所有比赛到list中
            List<Map<String,Object>> tlist=XMLData.getdata(Game.this);
            List<GameStore> templist=new ArrayList<GameStore>();
            //遍历list中的比赛，每个比赛新建一个gamestore类，放入临时list中，准备重新写入xml文件
            for(Map<String,Object> map:tlist){
                GameStore tgamestore=new GameStore();
                tgamestore.setName(map.get("name").toString());
                tgamestore.setDate(map.get("date").toString());
                tgamestore.setGroupNum(map.get("groupNum").toString());
                tgamestore.setGroupPlayerNum(map.get("groupPlayerNum").toString());
                templist.add(tgamestore);
            }
            //在临时list中最后加入新的比赛
            GameStore tgamestore=new GameStore();
            tgamestore.setName(gameName.getText().toString());
            tgamestore.setDate(gameTime.getText().toString());
            //加入默认设置
            tgamestore.setGroupNum(String.valueOf(preferences.getInt("groupNum", 0)));
            tgamestore.setGroupPlayerNum(String.valueOf(preferences.getInt("groupPlayerNum", 0)));
            templist.add(tgamestore);
            boolean flag=false;
            //重写game.xml文件
            String str=XMLData.writeToString(templist);
            if(XMLData.writeToXml(Game.this, str)){
                Toast.makeText(Game.this,"add success",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(Game.this,"add fail",Toast.LENGTH_SHORT).show();
            }

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
        String[] name={"世界锦标赛","奥运会","黄金大奖赛"};
        List<GameStore> gamelist=new ArrayList<GameStore>();

        for(int i=0;i<3;i++){
            GameStore gamestore=new GameStore();
            gamestore.setName(name[i]);
            gamestore.setDate("2015-10-1");
            gamestore.setGroupNum("4");
            gamestore.setGroupPlayerNum("4");
            gamelist.add(gamestore);
        }

        boolean flag=false;
        String str=XMLData.writeToString(gamelist);

        flag=XMLData.writeToXml(Game.this, str);
        if (flag){

        }
        else{
            Toast.makeText(Game.this,"xmlfail2",Toast.LENGTH_SHORT).show();
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


        Message msg=new Message();
        msg.what=SHOW_GAMELIST;
        msg.obj=XMLData.getdata(Game.this);
        mhandler.sendMessage(msg);
    }
}
