package com.example.finalwork.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.finalwork.AddActivity;
import com.example.finalwork.MainActivity;
import com.example.finalwork.MyDatabaseHelper;
import com.example.finalwork.ParentAddActivity;
import com.example.finalwork.R;

import java.lang.reflect.Method;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddIncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIncomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    AddActivity activity;
    private Context context;
    private Spinner inputIncomeType;
    private EditText inputAmount, inputDate, inputNote;
    DatePickerDialog.OnDateSetListener setListener;
    Button addButton;
    String input_type="", input_amount="", input_date="", input_note="";


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
        return getActivity().onMenuOpened(featureId, menu);
    }


    public AddIncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddIncomeFragment newInstance(String param1, String param2) {
        AddIncomeFragment fragment = new AddIncomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_add_income, container, false);
        inputIncomeType = v.findViewById(R.id.spinnerIncomeType);
        inputAmount = v.findViewById(R.id.editTextAmount);
        inputDate = v.findViewById(R.id.editTextDate);
        inputNote = v.findViewById(R.id.editTextNote);

        addButton = v.findViewById(R.id.add_button);

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

        //添加按钮的功能
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                MyDatabaseHelper myDB = new MyDatabaseHelper(getActivity());
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

        return v;
    }
}