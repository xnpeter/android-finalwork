package com.example.finalwork;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;

import java.lang.reflect.Method;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    //String[] billType = {"餐饮", "购物", "日用", "交通"};

    //记录下拉菜单选择的账单类型
    //private String selectedType;
    AddActivity activity;
    private Context context;
    private Spinner inputIncomeType;
    private EditText  inputAmount, inputDate, inputNote;
    DatePickerDialog.OnDateSetListener setListener;
    Button addButton;
    String input_type="", input_amount="", input_date="", input_note="";

    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){

                }
                catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        inputIncomeType = findViewById(R.id.spinnerIncomeType);
        inputAmount = findViewById(R.id.editTextAmount);
        inputDate = findViewById(R.id.editTextDate);
        inputNote = findViewById(R.id.editTextNote);

        addButton = findViewById(R.id.add_button);

//        按钮默认为不可按下状态，只有在前三个输入框不为空时才能被按下
//        实现方法：给每个edittext设置一个改变监听器，一改变就检查一遍是否符合条件
        addButton.setEnabled(false);
        inputAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input_type = charSequence.toString().trim();
//                Toast.makeText(AddActivity.this, input_type, Toast.LENGTH_SHORT).show();
                //只有前三个输入框都有信息时才能按下按钮
                if ((input_amount.length() != 0) && (input_date.length() != 0) ) {
                    addButton.setEnabled(true);
                }else {
                    addButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input_amount = charSequence.toString().trim();
                //只有前三个输入框都有信息时才能按下按钮
                if ((input_type.length() != 0) && (input_amount.length() != 0) && (input_date.length() != 0) ) {
                    addButton.setEnabled(true);
                }else {
                    addButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input_date = charSequence.toString().trim();
                //只有前三个输入框都有信息时才能按下按钮
                if ((input_type.length() != 0) && (input_amount.length() != 0) && (input_date.length() != 0) ) {
                    addButton.setEnabled(true);
                }else {
                    addButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input_note = charSequence.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //点击输入框后弹出日期选择
        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddActivity.this, new DatePickerDialog.OnDateSetListener(){
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



        //添加按钮的功能
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
//                if (inputType.getText().length() != 0) {
//                    input_type = inputType.getText().toString().trim();
//                } else {
//                    input_type = "";
//                }
//                if (inputAmount.getText().length() != 0) {
//                    input_amount = inputAmount.getText().toString().trim();
//                } else {
//                    input_amount = "";
//                }
//                if (inputDate.getText().length() != 0) {
//                    input_date = inputDate.getText().toString().trim();
//                } else {
//                    input_date = "";
//                }
//                if (inputNote.getText().length() != 0) {
//                    input_note = inputNote.getText().toString().trim();
//                } else {
//                    input_note = "";
//                }
//                if ((inputType.getText().length() != 0) && (inputAmount.getText().length() != 0)
//                && (inputDate.getText().length() != 0) ) {
//                    myDB.addData(input_type, input_amount, input_date, input_note);
//                    //添加完毕后自动返回主页
//                    startActivityForResult(intent, 1);
//                }else {
//                    Toast.makeText(AddActivity.this, "数据不全", Toast.LENGTH_SHORT).show();
//                }
                myDB.addData(inputIncomeType.getSelectedItem().toString().trim(),
                        inputAmount.getText().toString().trim(),
                        inputDate.getText().toString().trim(),
                        inputNote.getText().toString().trim());

                startActivityForResult(intent, 1);

            }
        });
    }
}


















