package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PasswordSettingActivity extends AppCompatActivity {

    EditText input_password;
    Button button;
    public String password_value = null;
    String input="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);

        input_password = findViewById(R.id.txtPassword);
        button = findViewById(R.id.confirmButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFile();


//                Intent intent = new Intent(PasswordSettingActivity.this, PasswordSettingActivity.class);
//                intent.putExtra("password", password_value);
//                Toast.makeText(PasswordSettingActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        button.setEnabled(false);
        input_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input = charSequence.toString().trim();
                if (input.length() != 0) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void saveFile(){
        FileOutputStream fos = null;
        try {
            String FILE_NAME = "password.txt";
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            String password_value = input_password.getText().toString();
            fos.write(password_value.getBytes());
            Toast.makeText(PasswordSettingActivity.this, "设置成功", Toast.LENGTH_SHORT).show();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (fos != null){
                try {
                    fos.flush();
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}