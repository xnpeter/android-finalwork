package com.example.finalwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton buttonAdd;
    ImageView empty_imageView;
    TextView no_data;

    MyDatabaseHelper myDB;
    ArrayList<String> bill_id, bill_type, bill_amount, bill_date, bill_note, bill_deleted;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        buttonAdd = findViewById(R.id.add_button);
        empty_imageView = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.no_data);

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
//    统计未删除状态的记录个数，如果为0则显示相应信息
    void storeDataInArrays() {
        int count = 0;
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                //如果“deleted”数据（也就是序号为6）为false，就将其他数据赋予Array，送入Adapter
                if (cursor.getString(6).equals("false")) {
                    bill_id.add(cursor.getString(0));
                    bill_type.add(cursor.getString(1));
                    bill_amount.add(cursor.getString(2));
                    bill_date.add(cursor.getString(3));
                    bill_note.add(cursor.getString(4));
                    count++;
                }
            }
            if (count == 0) {
                //如果没有数据，将“没有账单”的图片和文字设为可见
                //Toast.makeText(this, "No visible data", Toast.LENGTH_SHORT).show();
                empty_imageView.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
            }else {
                //如果有数据，则将“没有账单”的图片和文字设为不可见
                empty_imageView.setVisibility(View.GONE);
                no_data.setVisibility(View.GONE);
            }

        }
        }

//        创建右上角菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_right, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    当菜单被选中

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAll) {
            confirmDialog();
        } else if (item.getItemId() == R.id.trashBin) {
            Intent intent = new Intent(MainActivity.this, TrashbinActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //弹出“全部删除”确认框
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除");
        builder.setMessage("您确认要删除全部账单吗？\n删除的账单可在回收站中找回");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                recreate();
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






















