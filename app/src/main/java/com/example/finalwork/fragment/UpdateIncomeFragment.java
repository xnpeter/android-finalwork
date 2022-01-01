package com.example.finalwork.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.finalwork.MainActivity;
import com.example.finalwork.MyDatabaseHelper;
import com.example.finalwork.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateIncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class UpdateIncomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText inputAmount, inputDate, inputNote;
    private Spinner inputIncomeType;
    DatePickerDialog.OnDateSetListener setListener;
    Button updateButton, deleteButton;
    String id, type, amount, date, note;


    public UpdateIncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateIncomeFragment newInstance(String param1, String param2) {
        UpdateIncomeFragment fragment = new UpdateIncomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_update_income, container, false);
        inputIncomeType = v.findViewById(R.id.spinnerIncomeType2);
        inputAmount = v.findViewById(R.id.editTextAmount2);
        inputDate = v.findViewById(R.id.editTextDate2);
        inputNote = v.findViewById(R.id.editTextNote2);

        updateButton = v.findViewById(R.id.update_button);
        deleteButton = v.findViewById(R.id.delete_button);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //点击输入框后弹出日期选择
        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener(){
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
                Intent intent = new Intent(getActivity(), MainActivity.class);
                MyDatabaseHelper myDB = new MyDatabaseHelper(getActivity());
                type = inputIncomeType.getSelectedItem().toString().trim();
                amount = inputAmount.getText().toString().trim();
                date = inputDate.getText().toString().trim();
                note = inputNote.getText().toString().trim();
                myDB.updateData(id, type, amount, date, note,"true");
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
        return v;
    }

    //获取并设置此界面的EditText
    void getAndSetIntentData() {
        if (getActivity().getIntent().hasExtra("type") && getActivity().getIntent().hasExtra("amount") &&
                getActivity().getIntent().hasExtra("date") && getActivity().getIntent().hasExtra("note")) {
//            从Intent获取传过来的数据
            id = getActivity().getIntent().getStringExtra("id");
            type = getActivity().getIntent().getStringExtra("type");
            amount = getActivity().getIntent().getStringExtra("amount");
            date = getActivity().getIntent().getStringExtra("date");
            note = getActivity().getIntent().getStringExtra("note");


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
                case "其他收入":
                    type_position = 7;
                    break;


            }

            inputIncomeType.setSelection(type_position);
            inputAmount.setText(amount);
            inputDate.setText(date);
            inputNote.setText(note);
        } else {

        }
    }

    //    在删除时弹出确认对话框
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("确认删除");
        builder.setMessage("您确认要删除此条账单吗？\n删除的账单可在回收站中找回");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(getActivity());
                myDB.deleteOneRow(id);
                getActivity().finish();
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