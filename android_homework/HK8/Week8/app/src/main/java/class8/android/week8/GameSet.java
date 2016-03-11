package class8.android.week8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameSet extends AppCompatActivity {

    private MyDataBase myDataBase;

    private String gameName;
    private TextView gameSetName=null;
    private EditText groupNum=null;
    private EditText playerNum=null;
    private Button save=null;
    private List<Map<String,Object>> tlist;

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

        //先从games.xml中读取比赛的原先小组数和小组人数
        tlist=XMLData.getdata(GameSet.this);
        for(Map<String,Object> map:tlist){
            if(gameName.equals(map.get("name"))){
                String gn=map.get("groupNum").toString();
                String pn=map.get("groupPlayerNum").toString();
                groupNum.setText(gn);
                playerNum.setText(pn);
                break;
            }
        }

        //修改比赛的小组数和小组人数
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从games.xml中获取所有比赛信息，修改指定比赛的信息后重写games.xml文件
                String gn=groupNum.getText().toString();
                String pn=playerNum.getText().toString();
                for(Map<String,Object> map:tlist){
                    if(gameName.equals(map.get("name"))){
                        map.remove("groupNum");
                        map.put("groupNum",gn);
                        map.remove("groupPlayerNum");
                        map.put("groupPlayerNum",pn);
                        break;
                    }
                }
                List<GameStore> templist=new ArrayList<GameStore>();
                for(Map<String,Object> map:tlist){
                    GameStore tgamestore=new GameStore();
                    tgamestore.setName(map.get("name").toString());
                    tgamestore.setDate(map.get("date").toString());
                    tgamestore.setGroupNum(map.get("groupNum").toString());
                    tgamestore.setGroupPlayerNum(map.get("groupPlayerNum").toString());
                    templist.add(tgamestore);
                }
                boolean flag=false;
                String str=XMLData.writeToString(templist);
                if(XMLData.writeToXml(GameSet.this, str)){
                    Toast.makeText(GameSet.this, "update success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(GameSet.this,"update fail",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
