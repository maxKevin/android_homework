package class8.android.week8;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thorn on 2016/2/17.
 */
//此类包含三个对xml文件读写的方法
public class XMLData {
    //读取games.xml文件中的所有比赛信息，返回list类型
    public static List<Map<String,Object>> getdata(Context context){
        List<Map<String,Object>> tlist=new ArrayList<Map<String,Object>>();

        try{
            String tn="";
            String td="";
            String tg="";
            String tgp="";
            FileInputStream fs=  context.openFileInput("games.xml");

            XmlPullParser parser = Xml.newPullParser();

            parser.setInput(fs, "UTF-8");
            int eventType = parser.getEventType();

            while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
                String nodename=parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if("name".equals(nodename)){
                            tn=parser.nextText();
                        }else if ("date".equals(nodename)){
                            td=parser.nextText();
                        }else if("groupNum".equals(nodename)){
                            tg=parser.nextText();
                        }else if("groupPlayerNum".equals(nodename)){
                            tgp=parser.nextText();
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(!nodename.equals("games")){
                            Map<String,Object> map=new HashMap<String, Object>();
                            map.put("name",tn);
                            map.put("groupNum",tg);
                            map.put("groupPlayerNum",tgp);
                            map.put("date",td);
                            tlist.add(map);
                        }
                        break;
                }
            }
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return tlist;
    };

    //将gamesStores中的所有比赛信息编写为xml格式，返回string类型，准备写入games.xml文件中
    public static String writeToString(List<GameStore> gameStores){
        //实现xml信息序列号的一个对象
        XmlSerializer serializer= Xml.newSerializer();
        StringWriter writer=new StringWriter();

        try{
            //xml数据经过序列化后保存到String中，然后将字串通过OutputStream保存为xml文件
            serializer.setOutput(writer);
            //文档开始
            serializer.startDocument("utf-8", true);
            //开始一个节点

            serializer.startTag("", "games");
            serializer.attribute("", "type", "list");

            for(GameStore gameStore:gameStores){
                serializer.startTag("", "game");

                serializer.startTag("", "name");
                serializer.text(gameStore.getName());
                serializer.endTag("", "name");

                serializer.startTag("", "date");
                serializer.text(gameStore.getDate());
                serializer.endTag("", "date");

                serializer.startTag("", "groupNum");
                serializer.text(gameStore.getGroupNum());
                serializer.endTag("", "groupNum");

                serializer.startTag("", "groupPlayerNum");
                serializer.text(gameStore.getGroupPlayerNum());
                serializer.endTag("", "groupPlayerNum");


                serializer.endTag("", "game");
            }
            serializer.endTag("", "games");

            serializer.endDocument();

        }
        catch (Exception e){
            Log.i("test", "xmlfail1");
        }

        return writer.toString();
    };
    //写入xml文件
    public static boolean writeToXml(Context context,String str){
        try {
            OutputStream out=context.openFileOutput("games.xml", Context.MODE_PRIVATE);
            OutputStreamWriter outw=new OutputStreamWriter(out);
            try {
                outw.write(str);
                outw.close();
                out.close();
                return true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return false;
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            return false;
        }
    };


}
