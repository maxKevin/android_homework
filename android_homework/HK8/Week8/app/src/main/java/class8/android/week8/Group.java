package class8.android.week8;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//利用ExpandableListView实现二级列表
public class Group extends AppCompatActivity {

    private static final int REFRESH=1;

    private MyDataBase myDataBase;
    private String GameName;
    private int group;
    private int groupPlayerNum;
    GroupAdapter adapter;

    //一级列表内容
    List<String> parent=null;
    //二级列表内容
    Map<String, List<String>> map = null;
    private ExpandableListView grouplist=null;

    Handler mhanlder = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case REFRESH:
                    adapter=new GroupAdapter();
                    grouplist.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent intent = getIntent();
        GameName=intent.getStringExtra("game");

        myDataBase = new MyDataBase(Group.this,1);

        grouplist=(ExpandableListView)findViewById(R.id.groupList);
        showgrouplist();
        grouplist.setOnChildClickListener(AddNew);

    }

    //读取小组和每组的人员名单作为一级和二级列表的内容
    private void getdata(){
        parent = new ArrayList<String>();
        map = new HashMap<String, List<String>>();

        Log.i("Debug","start");

        List<Map<String,Object>> tlist=XMLData.getdata(Group.this);

        for(Map<String,Object> map:tlist){
            if(GameName.equals(map.get("name"))){
                group=Integer.valueOf(map.get("groupNum").toString());
                groupPlayerNum=Integer.valueOf(map.get("groupPlayerNum").toString());
                break;
            }
        }

        Log.i("Debug","end");
        Log.i("Debug",GameName);
        Log.i("Debug",String.valueOf(group));
        Log.i("Debug",String.valueOf(groupPlayerNum));

        for(int n=1;n<=group;n++){
            String groupNo="小组"+n;
            parent.add(groupNo);
            String sql="select name from player where game='"+GameName+
                    "' and groupNo="+n;
            Cursor child=myDataBase.select(sql);
            List<String> childlist = new ArrayList<String>();
            while(child.moveToNext()){
                childlist.add(child.getString(child.getColumnIndex("name")));
            }
            childlist.add("添加");
            map.put(groupNo,childlist);
        }
    }

    //显示列表
    private void showgrouplist(){
        getdata();


        Message msg=new Message();
        msg.what=REFRESH;
        mhanlder.sendMessage(msg);
    }

    //添加球员时间，若选中“添加”行则可添加球员
    ExpandableListView.OnChildClickListener AddNew = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            if(adapter.getChild(groupPosition, childPosition).equals("添加")){
                //System.out.println(String.valueOf(groupPosition));
                inputTitleDialog(groupPosition+1);
            }
            else{
                Intent intent = new Intent(Group.this,Score.class);
                intent.putExtra("groupNo",groupPosition+1);
                intent.putExtra("gamename",GameName);
                startActivity(intent);
            }
            return false;
        }
    };

    //添加球员的对话框
    private void inputTitleDialog(final int n) {

        final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入姓名").setIcon(
                android.R.drawable.ic_dialog_info).setView(inputServer).setNegativeButton(
                "取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //数据库中计算此小组已有人数
                String countsql = "select count(*) from player where game='" + GameName +
                        "' and groupNo=" + n;
                Cursor countcs = myDataBase.select(countsql);
                int playerNum;
                countcs.moveToNext();
                playerNum = countcs.getInt(0);

                //人数判断
                if (playerNum >= groupPlayerNum) {
                    Toast.makeText(Group.this, "小组人数超出上限", Toast.LENGTH_SHORT).show();
                } else {

                    String inputName = inputServer.getText().toString();
                    ContentValues cv = new ContentValues();
                    cv.put("name", inputName);
                    cv.put("game", GameName);
                    cv.put("groupNo", n);

                    //添加新人物的同时，在数据库得分表中插入新人物和小组中每个人的比分信息，默认不输入比分
                    String sql = "select name from player where game='" + GameName + "' and groupNo=" + n;
                    Cursor cs = myDataBase.select(sql);
                    while (cs.moveToNext()) {
                        //System.out.println(cs.getString(cs.getColumnIndex("name")));

                        ContentValues cv2 = new ContentValues();
                        cv2.put("player1", inputName);
                        cv2.put("player2", cs.getString(cs.getColumnIndex("name")));
                        cv2.put("game", GameName);
                        cv2.put("groupNo", n);
                        myDataBase.insert("score", cv2);

                        ContentValues cv3 = new ContentValues();
                        cv3.put("player1", cs.getString(cs.getColumnIndex("name")));
                        cv3.put("player2", inputName);
                        cv3.put("game", GameName);
                        cv3.put("groupNo", n);
                        myDataBase.insert("score", cv3);
                    }
                    myDataBase.insert("player", cv);

                    Toast.makeText(Group.this, inputName, Toast.LENGTH_SHORT).show();
                    showgrouplist();
                }
            }
        });
        builder.show();
    }

    public class GroupAdapter extends BaseExpandableListAdapter {
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
            String key = Group.this.parent.get(groupPosition);
            String info = map.get(key).get(childPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) Group.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grouplistchild, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.groupChild);
            tv.setText(info);
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
                LayoutInflater inflater = (LayoutInflater) Group.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grouplistparent, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.groupParent);
            tv.setText(Group.this.parent.get(groupPosition));
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
