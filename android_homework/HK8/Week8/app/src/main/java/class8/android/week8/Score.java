package class8.android.week8;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//利用ExpandableListView
public class Score extends AppCompatActivity {
    private static final int REFRESH=1;

    private MyDataBase myDataBase;
    private String GameName;
    private int GroupNo;

    ScoreAdapter adapter;
    //一级列表内容
    List<String> parent=null;
    //二级列表内容
    Map<String, List<Map<String,String>>> map = null;
    private ExpandableListView scorelist=null;
    private TextView scoreGroupNo=null;

    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case REFRESH:
                    adapter=new ScoreAdapter();
                    scorelist.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent intent=getIntent();
        GroupNo = intent.getIntExtra("groupNo", 0);
        GameName=intent.getStringExtra("gamename");
        myDataBase=new MyDataBase(Score.this,1);
        scorelist=(ExpandableListView)findViewById(R.id.scoreList);
        scoreGroupNo=(TextView)findViewById(R.id.scoreGroupNo);
        scoreGroupNo.setText("第"+String.valueOf(GroupNo)+"小组比分记录");

        ShowScoreList();
        scorelist.setOnChildClickListener(UpdateScore);
    }

    private void ShowScoreList(){
        getdata();

        Message msg=new Message();
        msg.what=REFRESH;
        mhandler.sendMessage(msg);
    }
    //从数据库中获取名单和比分情况
    private void getdata(){
        parent = new ArrayList<String>();
        map = new HashMap<String, List<Map<String,String>>>();
        //获取参加此比赛的名单
        String sql="select name from player where groupNo="+GroupNo+" and game='"+GameName+"'";
        Cursor cs=myDataBase.select(sql);
        while(cs.moveToNext()){
            //获取每位选手和其他选手的比分数据，比分和对手名字放入hashmap，每位选手的比分数据放入一个list
            String pm=cs.getString(cs.getColumnIndex("name"));
            parent.add(pm);
            String sql2="select player2,score1,score2 from score where player1='"+
                    pm+"' and game='"+GameName+"'";
            Cursor score=myDataBase.select(sql2);
            List<Map<String,String>> listchild=new ArrayList<Map<String, String>>();
            while(score.moveToNext()){
                String scorestr="";
                if(score.getInt(score.getColumnIndex("score1"))!=0 ||
                        score.getInt(score.getColumnIndex("score2"))!=0){
                    scorestr=score.getString(score.getColumnIndex("score1"))+":"+
                            score.getString(score.getColumnIndex("score2"));
                    //System.out.println(scorestr);
                }
                String namestr= score.getString(score.getColumnIndex("player2"));
                Map<String,String> mapChild= new HashMap<String,String>();
                mapChild.put("score",scorestr);
                mapChild.put("name", namestr);
                //System.out.println(pm+namestr);
                listchild.add(mapChild);
            }
            map.put(pm, listchild);
        }
    }

    ExpandableListView.OnChildClickListener UpdateScore = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            String player1name=(String)adapter.getGroup(groupPosition);
            Map<String,String> tempmap= (Map<String,String>)adapter.getChild(groupPosition, childPosition);
            String player2name=tempmap.get("name");
            String n=String.valueOf(GroupNo);
            inputTitleDialog(player1name, player2name, n);
            //System.out.println(player1name+player2name);
            return false;
        }
    };

    //修改比分对话框，事先在layout中编写布局
    private void inputTitleDialog(final String str1, final String str2,final String n){
        //System.out.println(str1+str2+n);
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.scoredialog, null);
        final EditText et1=(EditText)textEntryView.findViewById(R.id.scoredialog1);
        final EditText et2=(EditText)textEntryView.findViewById(R.id.scoredialog2);
        final TextView tv1=(TextView)textEntryView.findViewById(R.id.dialogtext);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("输入比分");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(textEntryView);
        tv1.setText(str1+" VS "+str2);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String score1 = et1.getText().toString();
                String score2 = et2.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("score1", score1);
                cv.put("score2", score2);
                SQLiteDatabase db = myDataBase.getWritableDatabase();
                db.update("score", cv, "player1=? and player2=? and groupNo=? and game=?", new String[]{str1, str2, n, GameName});
                //System.out.println(score1);
                ContentValues cv2 = new ContentValues();
                cv2.put("score1", score2);
                cv2.put("score2", score1);
                db.update("score", cv2, "player1=? and player2=? and groupNo=? and game=?", new String[]{str2, str1, n, GameName});
                ShowScoreList();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    public class ScoreAdapter extends BaseExpandableListAdapter {
        //得到子item需要关联的数据
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String key = parent.get(groupPosition);
            return (map.get(key).get(childPosition));
        }

        //得到子item的ID
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //设置子item的组件
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            String key = Score.this.parent.get(groupPosition);
            Map<String,String> info = map.get(key).get(childPosition);
            String score=info.get("score");
            String name=info.get("name");
            //System.out.println(key+childPosition+name);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) Score.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grouplistchild, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.groupChild);
            tv.setText(score+"  "+name);
            return tv;
        }

        //获取当前父item下的子item的个数
        @Override
        public int getChildrenCount(int groupPosition) {
            String key = parent.get(groupPosition);
            int size=map.get(key).size();
            return size;
        }

        //获取当前父item的数据
        @Override
        public Object getGroup(int groupPosition) {
            return parent.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return parent.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        //设置父item组件
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) Score.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grouplistparent, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.groupParent);
            tv.setText(Score.this.parent.get(groupPosition));
            return tv;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }
}
