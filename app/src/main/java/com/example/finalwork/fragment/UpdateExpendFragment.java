package com.example.finalwork.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
 * Use the {@link UpdateExpendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateExpendFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText inputAmount, inputDate, inputNote;
    private Spinner inputExpendType;
    DatePickerDialog.OnDateSetListener setListener;
    Button updateButton, deleteButton;
    String id, type, amount, date, note;

    public UpdateExpendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateExpendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateExpendFragment newInstance(String param1, String param2) {
        UpdateExpendFragment fragment = new UpdateExpendFragment();
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
        View v = inflater.inflate(R.layout.fragment_update_expend, container, false);
        inputExpendType = v.findViewById(R.id.spinnerExpendType2);
        inputAmount = v.findViewById(R.id.editTextAmount2);
        inputDate = v.findViewById(R.id.editTextDate2);
        inputNote = v.findViewById(R.id.editTextNote2);

        updateButton = v.findViewById(R.id.update_button);
        deleteButton = v.findViewById(R.id.delete_button);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //????????????????????????????????????
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

        //?????????????????????textview??????
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = year+"/"+month+"/"+dayOfMonth;
                inputDate.setText(date);
            }
        };

        //        ???????????????edittext??????
        getAndSetIntentData();


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                MyDatabaseHelper myDB = new MyDatabaseHelper(getActivity());
                type = inputExpendType.getSelectedItem().toString().trim();
                amount = inputAmount.getText().toString().trim();
                date = inputDate.getText().toString().trim();
                note = inputNote.getText().toString().trim();
                myDB.updateData(id, type, amount, date, note,"false");
                //?????????????????????????????????
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
    //???????????????????????????EditText
    void getAndSetIntentData() {
        if (getActivity().getIntent().hasExtra("type") && getActivity().getIntent().hasExtra("amount") &&
                getActivity().getIntent().hasExtra("date") && getActivity().getIntent().hasExtra("note")) {
//            ???Intent????????????????????????
            id = getActivity().getIntent().getStringExtra("id");
            type = getActivity().getIntent().getStringExtra("type");
            amount = getActivity().getIntent().getStringExtra("amount");
            date = getActivity().getIntent().getStringExtra("date");
            note = getActivity().getIntent().getStringExtra("note");
//            ????????????????????????????????????????????????

            //?????????????????????spinner????????????position
            int type_position = 0;
            switch (type) {
                case "??????":
                    type_position = 0;
                    break;
                case "??????":
                    type_position = 1;
                    break;
                case "??????":
                    type_position = 2;
                    break;
                case "??????":
                    type_position = 3;
                    break;
                case "??????":
                    type_position = 4;
                    break;
                case "??????":
                    type_position = 5;
                    break;
                case "??????":
                    type_position = 6;
                    break;
                case "??????":
                    type_position = 7;
                    break;
                case "????????????":
                    type_position = 8;
                    break;


            }

            inputExpendType.setSelection(type_position);
            inputAmount.setText(amount);
            inputDate.setText(date);
            inputNote.setText(note);
        } else {

        }
    }

    //    ?????????????????????????????????
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("????????????");
        builder.setMessage("????????????????????????????????????\n???????????????????????????????????????");
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(getActivity());
                myDB.deleteOneRow(id);
                getActivity().finish();
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}