package com.example.finalwork;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton buttonAdd;

    MyDatabaseHelper myDB;
    ArrayList<String> bill_id, bill_type, bill_amount, bill_date, bill_note, bill_deleted;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        buttonAdd = findViewById(R.id.add_button);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        bill_id = new ArrayList<>();
        bill_type = new ArrayList<>();
        bill_amount = new ArrayList<>();
        bill_date = new ArrayList<>();
        bill_note = new ArrayList<>();
        bill_deleted = new ArrayList<>();

        storeDataInArrays();
        //if (bill_deleted.get())
        //将数据传入Adapter，最终显示
        customAdapter = new CustomAdapter(MainActivity.this, this, bill_id, bill_type, bill_amount, bill_date, bill_note);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    //在返回时重新加载，刷新activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }


    //将数据从数据可存放到Array中
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                //如果“deleted”数据（也就是序号为6）为false，就将其他数据赋予Array，送入Adapter
                if (cursor.getString(6).equals("false")) {
                    bill_id.add(cursor.getString(0));
                    bill_type.add(cursor.getString(1));
                    bill_amount.add(cursor.getString(2));
                    bill_date.add(cursor.getString(3));
                    bill_note.add(cursor.getString(4));
                }
            }
        }
    }
}






















