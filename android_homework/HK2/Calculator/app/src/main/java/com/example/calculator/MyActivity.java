package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;


public class MyActivity extends Activity {
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("das", "12313213");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my);
        result = (TextView) findViewById(R.id.RESULT);
        Button num0 = (Button) findViewById(R.id.num0);
        Button num1 = (Button) findViewById(R.id.num1);
        Button num2 = (Button) findViewById(R.id.num2);
        Button num3 = (Button) findViewById(R.id.num3);
        Button num4 = (Button) findViewById(R.id.num4);
        Button num5 = (Button) findViewById(R.id.num5);
        Button num6 = (Button) findViewById(R.id.num6);
        Button num7 = (Button) findViewById(R.id.num7);
        Button num8 = (Button) findViewById(R.id.num8);
        Button num9 = (Button) findViewById(R.id.num9);
        Button numd = (Button) findViewById(R.id.numd);
        Button CLEAR = (Button) findViewById(R.id.CLEAR);
        Button DELETE = (Button) findViewById(R.id.DELETE);
        Button DIV = (Button) findViewById(R.id.DIV);
        Button ADD = (Button) findViewById(R.id.ADD);
        Button MUL = (Button) findViewById(R.id.MUL);
        Button SUB = (Button) findViewById(R.id.SUB);
        Button EQU = (Button) findViewById(R.id.equal);
        SetFunction(num0,"0");
        SetFunction(num1,"1");
        SetFunction(num2,"2");
        SetFunction(num3,"3");
        SetFunction(num4,"4");
        SetFunction(num5,"5");
        SetFunction(num6,"6");
        SetFunction(num7,"7");
        SetFunction(num8,"8");
        SetFunction(num9,"9");
        SetFunction(numd,".");
        SetFunction(ADD,"+");
        SetFunction(SUB,"-");
        SetFunction(MUL,"×");
        SetFunction(DIV,"÷");
        EQU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = result.getText().toString();
                String con=content;
                content=content.replaceAll("×","*");
                content=content.replaceAll("÷","/");
                Result over = new Result();
                Log.w("result=",content);
                String answer=over.result(content);
                if(answer == "Infinity")
                    result.setText("∞");
                else
                    result.setText(con+"=\n"+answer);
            }
        });
        DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content =  result.getText().toString();
                content = content.substring(0,content.length()-1);
                result.setText(content);
            }
        });
        CLEAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(null);
            }
        });
    }

    private void SetFunction(Button button, final String function){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = result.getText().toString();
                if("+-.÷×".contains(function)||content==null || content.contains("=")) {
                    if(content==null)
                        return ;
                    if(content.contains("=")){
                        String[] numbers = content.split("=");
                        content=numbers[numbers.length-1];
                        return ;
                    }
                    if(content.endsWith("+") || content.endsWith("-")||content.endsWith("×") || content.endsWith("÷"))
                        return ;
//                    num=new int[]{content.lastIndexOf("+"),content.lastIndexOf("-"),content.lastIndexOf("×"),content.lastIndexOf("÷")};
//                    Arrays.sort(num);
                }
                if(function == "."){
                    String[] num = content.split("[.]");
                    int length = num.length-1;
                    if(length != 0) {
                        String last = num[length];
                        if (!(last.contains("+") || last.contains("-") || last.contains("×") || last.contains("÷"))) {
                            return;
                        }
                    }
                }
//                try {
//                    if (num[3] != -1) {
//                        String con = content.substring(num[3], content.length() - 1);
//                        if (con.contains(".") && function == ".")
//                            return;
//                    }
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
                if(content.contains("="))
                    result.setText(function);
                else
                    result.setText(content+function);

            }
        });
    }
        private double get(){
            String content = result.toString();
            if(content != null) {
                double num = Double.valueOf(content);
                if(num != 0){
                    return num;
                }
            }
            return 0;
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
