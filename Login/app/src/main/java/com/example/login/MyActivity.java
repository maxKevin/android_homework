package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MyActivity extends Activity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Button login;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.passWords);
        rememberPassword = (CheckBox) findViewById(R.id.checkBox);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember) {
            //将账号和密码都设置到复选框中
            String account = pref.getString("account" , "");
            String password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPassword.setChecked(true);
        }
        login = (Button) findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               String account = accountEdit.getText().toString();
               String password = passwordEdit.getText().toString();
               if(account.equals("admin") && password.equals("123456")){
                   editor = pref.edit();
                   if(rememberPassword.isChecked()) {
                       //查看复选框是否被选中
                       editor.putBoolean("remember_password",true);
                       editor.putString("account",account);
                       editor.putString("password",password);
                   }else{
                       editor.clear();
                   }
                   editor.commit();
                   Intent intent = new Intent(MyActivity.this,Init.class);
                   startActivity(intent);
                   finish();
               }else {
                   Toast.makeText(MyActivity.this,"your account or password is wrong!",Toast.LENGTH_SHORT).show();
                   passwordEdit.setText("");
               }

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
