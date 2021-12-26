package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    //String[] billType = {"餐饮", "购物", "日用", "交通"};

    //记录下拉菜单选择的账单类型
    //private String selectedType;
    AddActivity activity;
    private Context context;
    private EditText inputType, inputAmount, inputDate, inputNote;
    Button addButton;
    String input_type, input_amount, input_date, input_note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        inputType = findViewById(R.id.editTextType);
        inputAmount = findViewById(R.id.editTextAmount);
        inputDate = findViewById(R.id.editTextDate);
        inputNote = findViewById(R.id.editTextNote);

        addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                if (inputType.getText().length() != 0) {
                    input_type = inputType.getText().toString().trim();
                } else {
                    input_type = "";
                }
                if (inputAmount.getText().length() != 0) {
                    input_amount = inputAmount.getText().toString().trim();
                } else {
                    input_amount = "";
                }
                if (inputDate.getText().length() != 0) {
                    input_date = inputDate.getText().toString().trim();
                } else {
                    input_date = "";
                }
                if (inputNote.getText().length() != 0) {
                    input_note = inputNote.getText().toString().trim();
                } else {
                    input_note = "";
                }
                if ((inputType.getText().length() != 0) || (inputAmount.getText().length() != 0)
                || (inputDate.getText().length() != 0) || (inputNote.getText().length() != 0)) {
                    myDB.addData(input_type, input_amount, input_date, input_note);
                    //添加完毕后自动返回主页
                    startActivityForResult(intent, 1);
                }else {
                    Toast.makeText(AddActivity.this, "请输入数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}