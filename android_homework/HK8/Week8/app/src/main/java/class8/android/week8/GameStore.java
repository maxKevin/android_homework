package class8.android.week8;

/**
 * Created by Thorn on 2016/2/17.
 */
public class GameStore {
    String name;
    String date;
    String groupNum;
    String groupPlayerNum;

    public void setName(String str){
        name=str;
    }
    public void setDate(String str){
        date=str;
    }
    public void setGroupNum(String str){
        groupNum=str;
    }
    public void setGroupPlayerNum(String str){
        groupPlayerNum=str;
    }

    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
    public String getGroupNum(){
        return groupNum;
    }
    public String getGroupPlayerNum(){
        return groupPlayerNum;
    }
}
