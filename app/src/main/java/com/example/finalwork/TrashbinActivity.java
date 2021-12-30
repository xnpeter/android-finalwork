package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class TrashbinActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    MyDatabaseHelper myDB;
    ArrayList<String> bill_id, bill_type, bill_amount, bill_date, bill_note, bill_deleted;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trashbin);

        recyclerView = findViewById(R.id.recyclerView);

        myDB = new MyDatabaseHelper(TrashbinActivity.this);
        bill_id = new ArrayList<>();
        bill_type = new ArrayList<>();
        bill_amount = new ArrayList<>();
        bill_date = new ArrayList<>();
        bill_note = new ArrayList<>();
        bill_deleted = new ArrayList<>();

        storeDataInArrays();

        //将数据传入Adapter，最终显示
        customAdapter = new CustomAdapter(TrashbinActivity.this, this, bill_id, bill_type, bill_amount, bill_date, bill_note);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TrashbinActivity.this));
    }

    //只显示被删除的数据
    void storeDataInArrays() {
        int count = 0;
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                //如果“deleted”数据（也就是序号为6）为false，就将其他数据赋予Array，送入Adapter
                if (cursor.getString(6).equals("true")) {
                    bill_id.add(cursor.getString(0));
                    bill_type.add(cursor.getString(1));
                    bill_amount.add(cursor.getString(2));
                    bill_date.add(cursor.getString(3));
                    bill_note.add(cursor.getString(4));
                    count++;
                }
            }
        }
    }
}