package com.example.finalwork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    EditText inputAmount, inputDate, inputNote;
    private Spinner inputIncomeType;
    DatePickerDialog.OnDateSetListener setListener;
    Button updateButton, deleteButton;
    String id, type, amount, date, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        inputIncomeType = findViewById(R.id.spinnerIncomeType2);
        inputAmount = findViewById(R.id.editTextAmount2);
        inputDate = findViewById(R.id.editTextDate2);
        inputNote = findViewById(R.id.editTextNote2);

        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //点击输入框后弹出日期选择
        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = year+"/"+month+"/"+day;
                        inputDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //选中日期后设置textview内容
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = year+"/"+month+"/"+dayOfMonth;
                inputDate.setText(date);
            }
        };

        //        获取并设置edittext内容
        getAndSetIntentData();


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                type = inputIncomeType.getSelectedItem().toString().trim();
                amount = inputAmount.getText().toString().trim();
                date = inputDate.getText().toString().trim();
                note = inputNote.getText().toString().trim();
                myDB.updateData(id, type, amount, date, note);
                //添加完毕后自动返回主页
                startActivityForResult(intent, 1);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    //获取并设置此界面的EditText
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

            //获取相应选项在spinner中对应的position
            int type_position = 0;
            switch (type) {
                case "奖学金":
                    type_position = 0;
                    break;
                case "补助金":
                    type_position = 1;
                    break;
                case "比赛奖金":
                    type_position = 2;
                    break;
                case "业余兼职":
                    type_position = 3;
                    break;
                case "基本工资":
                    type_position = 4;
                    break;
                case "福利分红":
                    type_position = 5;
                    break;
                case "加班津贴":
                    type_position = 6;
                    break;
                case "其他":
                    type_position = 7;
                    break;

            }

            inputIncomeType.setSelection(type_position);
            inputAmount.setText(amount);
            inputDate.setText(date);
            inputNote.setText(note);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

//    在删除时弹出确认对话框
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除");
        builder.setMessage("您确认要删除此条账单吗？\n删除的账单可在回收站中找回");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}