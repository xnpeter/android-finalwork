package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordSettingActivity extends AppCompatActivity {

    EditText input_password;
    Button button;
    public String password_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);

        input_password = findViewById(R.id.txtPassword);
        button = findViewById(R.id.confirmButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_value = input_password.getText().toString();
                Intent intent = new Intent(PasswordSettingActivity.this, PasswordSettingActivity.class);
                intent.putExtra("password", password_value);
                Toast.makeText(PasswordSettingActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}