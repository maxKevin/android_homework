package android_class.week6;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by king on 2016/2/23.
 */
public class Adapter extends ArrayAdapter<String> {
    private int resourceId;
    private List<String> item;
    public Adapter(Context context,int textViewResourceId,List<String> list){
        super(context,textViewResourceId,list);
        resourceId = textViewResourceId;
        item = list;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        String name = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(name);
        return view;
    }

    public String get(int index){
        Log.w("item",item.get(index));
        return item.get(index);
    }
}
