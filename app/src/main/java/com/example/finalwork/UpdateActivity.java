package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText inputType, inputAmount, inputDate, inputNote;
    Button updateButton;
    String id, type, amount, date, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        inputType = findViewById(R.id.editTextType2);
        inputAmount = findViewById(R.id.editTextAmount2);
        inputDate = findViewById(R.id.editTextDate2);
        inputNote = findViewById(R.id.editTextNote2);

        updateButton = findViewById(R.id.update_button);

        //        获取并设置edittext内容
        getAndSetIntentData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                type = inputType.getText().toString().trim();
                amount = inputAmount.getText().toString().trim();
                date = inputDate.getText().toString().trim();
                note = inputNote.getText().toString().trim();
                myDB.updateData(id, type, amount, date, note);
                //添加完毕后自动返回主页
                startActivityForResult(intent, 1);
            }
        });

    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("type") && getIntent().hasExtra("amount") &&
                getIntent().hasExtra("date") && getIntent().hasExtra("note")) {
//            从Intent获取传过来的数据
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
            amount = getIntent().getStringExtra("amount");
            date = getIntent().getStringExtra("date");
            note = getIntent().getStringExtra("note");
//            将当前的输入框设置为传过来的数据
            inputType.setText(type);
            inputAmount.setText(amount);
            inputDate.setText(date);
            inputNote.setText(note);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}