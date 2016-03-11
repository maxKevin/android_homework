package android_class.week6;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity {

    /**
     * file management I make a listView to show the file list.Only can add new file and show the file list.
     * I will add new function which can delete and select file by the name you give.
     * 思路：采取重写返回键 并且更新adapter的方法来让同一个活动表现出文件的点击进入一会就后退返回
     * 下一步目标：重写一个listView的布预计会采用DrawerLayout来实现侧拉删除键的功能 重写DrawerLayout中的dispatchTouchEvent(MotionEvent e)来方便侧滑
     * 搜索功能我决定用toast来 显示未找到 如果找到直接跳转（最希望的是 能够让 屏幕直接跳到文件的位置上）
     */

    protected String path = new String();
    private List<String> list = new ArrayList<String>();
//    private String[] list;
    private EditText text;
    private Adapter adapter;
//    private List<String> refresh = new ArrayList<String>();
    private ListView listView;
    protected static String deleteName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        listView = (ListView) findViewById(R.id.listView);
        File file = Environment.getExternalStorageDirectory();
        path  = file.getPath();
        ListTemplete.setPath(path);
        list.clear();
        String[] name = file.list();
        for(String each:name)
            list.add(each);
        text = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        adapter = new Adapter(this,R.layout.list_templete,list);
        listView.setAdapter(adapter);
        /*点击增加 文件夹*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = text.getText().toString();
                if(name == null)
                    return ;
                if(name.startsWith(path))
                    name = name.substring(name.indexOf(path)+path.length(),name.length());
                File file = new File(path,name);
                if(file.exists())
                    file.delete();
                file.mkdirs();
                list.add(name);
                Toast.makeText(getApplicationContext(),"Ok ",Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                text.setText("");
            }
        });
        /*增加 listView点击事件*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = adapter.get(position);
                name =path+"/"+name;
                File file = new File(name);
                if(!file.isDirectory()){
                    Intent intent = ListActivity.openFile(name);
                    startActivity(intent);
                    return ;
                }
                String[] refreshName = file.list();
                list.clear();
                for(int i = 0 ; i <= refreshName.length-1 ; i++)
                    list.add(refreshName[i]);
                path = name;
                ListTemplete.setPath(path);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "refresh ok " + path, Toast.LENGTH_SHORT).show();
            }
        });

        //delete refresh
         new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (ListTemplete.REFRESH) {
                        MyActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(deleteName);
                                adapter.notifyDataSetChanged();
                                ListTemplete.REFRESH = false;
                            }
                        });
                    }
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed(){
        if(path.equals(Environment.getExternalStorageState().toString())) {
            super.onBackPressed();
            return ;
        }
        path = path.substring(0,path.lastIndexOf("/"));
        list.clear();
         File file = new File(path);
        String[] fileName = file.list();
        for(int i = 0 ; i <= fileName.length-1 ; i++)
            list.add(fileName[i]);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
