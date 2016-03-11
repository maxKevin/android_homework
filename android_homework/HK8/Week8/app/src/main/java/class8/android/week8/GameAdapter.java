package class8.android.week8;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


/**
 * Created by Thorn on 2016/2/13.
 */
public class GameAdapter extends ArrayAdapter<Map<String,Object>> {
    private int resourceId;
    Context context;

    public GameAdapter(Context context,int textViewResourceId,List<Map<String,Object>> objects){
        super(context, textViewResourceId,objects);
        resourceId=textViewResourceId;
        this.context=context;
    }

    @Override
    public View getView(final int position,View convertView,ViewGroup parent){
        Map<String,Object> map=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView gameName=(TextView)view.findViewById(R.id.gameNameItem);
        String name=(String)map.get("name");
        gameName.setText(name);
        ImageView setting=(ImageView)view.findViewById(R.id.gameSet);
        //设置图片点击事件
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,GameSet.class);
                intent.putExtra("game",(String)getItem(position).get("name"));
                context.startActivity(intent);
            }
        });
        return view;
    }
}
