package android_class.week6;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by king on 2016/2/23.
 */
public class ListTemplete extends LinearLayout {
    private static String path;
    private boolean OK;
    public static boolean REFRESH;
    private boolean DELETE;

    public ListTemplete(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.activity_list, this);
        Button button = (Button) findViewById(R.id.button);
        final TextView textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textView.getText().toString();
                name = path + "/" + name;
                File file = new File(name);
                if (file.isDirectory() && !OK) {
                    Toast.makeText(getContext(), "再次确认删除文件夹", Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(200);
                        OK = true;
                        Log.w("week6 delete", "ok");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                file.delete();
                MyActivity.deleteName = textView.getText().toString();
                Toast.makeText(getContext(), "文件已删除", Toast.LENGTH_SHORT).show();
                Log.w("week6 delete", "finished");
                REFRESH = true;
                OK = false;
            }
        });
    }

    public static boolean setPath(String Newpath) {
        path = Newpath;
        return true;
    }
}
