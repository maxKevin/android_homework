package class8.android.week8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Thorn on 2016/1/13.
 */
public class MyDataBase extends SQLiteOpenHelper {
    Context mcontext;
    SQLiteDatabase mDb;

    private static final String CREATE_SCORE="create table score (player1 varchar(20)," +
            "player2 varchar(20),score1 int,score2 int,game varchar(16),groupNo int)";

    private static final String CREATE_PLAYER="create table player (name varchar(20)," +
            "game varchar(16),groupNo int)";


    public MyDataBase(Context context,int version){
        super(context, "GameDataBase", null, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        this.mDb=db;
        db.execSQL(CREATE_PLAYER);
        db.execSQL(CREATE_SCORE);

        Toast.makeText(mcontext, "create successfully", Toast.LENGTH_SHORT).show();
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Cursor query(String table){
        SQLiteDatabase db =getReadableDatabase();
        String sql="select * from " + table;
        Cursor cs=db.rawQuery(sql,new String[0]);
        return cs;
    }

    public int DataCount(String table){
        int n=0;
        SQLiteDatabase db=getReadableDatabase();
        String sql="select count(*) from "+table;
        Cursor cs=db.rawQuery(sql,new String[0]);
        if(cs.moveToNext()){
            n=cs.getInt(0);
        }
        return n;
    }

    public void insert(String table,ContentValues values){
        SQLiteDatabase db=getWritableDatabase();
        db.insert(table, null, values);
        close();
    }

    public Cursor select(String sql){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cs=db.rawQuery(sql,new String[0]);
        return cs;
    }
}
