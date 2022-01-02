package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    EditText txtPassword;
    Button loginButton;
    String password_got,input_password;
    String FILE_NAME = "password.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        txtPassword = findViewById(R.id.txtPassword);
        loginButton = findViewById(R.id.loginButton);
        getSupportActionBar().hide();//这种方式默认式亮色主题



//        FileOutputStream fos = null;
//        try {
//            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        String text = "1234";
//        try {
//            fos.write(text.getBytes());
//            fos.flush();
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_password = txtPassword.getText().toString();
                loadFile();

            }
        });
    }

    private void loadFile(){
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            if (fis.available()==0){
                Toast.makeText(LoginActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
            }
            byte[] readBytes = new byte[fis.available()];
            while (fis.read(readBytes) != -1){
            }
            password_got = new String(readBytes);
            if (input_password.equals(password_got)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            //Toast.makeText(LoginActivity.this, "未找到文件", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}