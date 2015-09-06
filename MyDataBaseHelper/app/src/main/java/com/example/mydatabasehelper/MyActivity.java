package com.example.mydatabasehelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MyActivity extends Activity {
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        dbHelper = new MyDatabaseHelper(this , "BookStore.db",null , 5);
        Button button = (Button) findViewById(R.id.create_database);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });
        Button add_data = (Button) findViewById(R.id.add_data);
        add_data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //数据容器
                values.put("name","The Da Vinci Code");
                values.put("author","DanDown");
                values.put("pages",454);
                values.put("price",16.96);
                db.insert("Book",null,values);
                //添加第一条
                values.clear();
                values.put("name","The Lost Symbol");
                values.put("author","DanDown");
                values.put("pages",510);
                values.put("price",19.95);
                db.insert("Book",null,values);
            }
        });
        Button update = (Button) findViewById(R.id.up_date);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改数据
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",10.99);
                db.update("Book", values, "name = ?", new String[]{
                        "The Da Vinci Code"
                });
            }
        });

        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除数据 区域删除 只能确定范围的写法
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book","pages > ?" , new String[] { "500"});
            }
        });

        Button query_data = (Button) findViewById(R.id.query_data);
        query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,null,null,null);

                if(cursor.moveToFirst()) {
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MyActivity","book name is " + name);
                        Log.d("MyActivity","book author is " + author);
                        Log.d("MyActivity","book pages is " + pages);
                        Log.d("MyActivity","book price is " + price);
                    }while(cursor.moveToNext());
                }
                cursor.close();
            }
        });
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
