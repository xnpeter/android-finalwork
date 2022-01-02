package com.example.finalwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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

import com.example.finalwork.adapter.CustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton buttonAdd;
    ImageView empty_imageView;
    TextView no_data;

    MyDatabaseHelper myDB;
    ArrayList<String> bill_id, bill_type, bill_amount, bill_date, bill_note;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        buttonAdd = findViewById(R.id.add_button);
        empty_imageView = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.no_data);

        //滑动item需要的
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        //添加按钮
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ParentAddActivity.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        bill_id = new ArrayList<>();
        bill_type = new ArrayList<>();
        bill_amount = new ArrayList<>();
        bill_date = new ArrayList<>();
        bill_note = new ArrayList<>();

        storeDataInArrays();
        //if (bill_deleted.get())
        //将数据传入Adapter，最终显示
        customAdapter = new CustomAdapter(MainActivity.this, this, bill_id, bill_type, bill_amount, bill_date, bill_note);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    //滑动item
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            String  id;



            id = bill_id.get(position);
            MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
            myDB.deleteOneRow(id);

//            customAdapter.RemoveItem(position);
//            customAdapter.notifyItemRemoved(position);
//            customAdapter.notifyItemChanged(position);
//            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition( 0, 0);
            finish();
            overridePendingTransition( 0, 0);

            //recreate();
        }
    };

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
        int billCount = 0;
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {

        } else {
            while (cursor.moveToNext()) {
                //如果“deleted”数据（也就是序号为6）为false，就将其他数据赋予Array，送入Adapter
                if (cursor.getString(6).equals("false")) {
                    bill_id.add(cursor.getString(0));
                    bill_type.add(cursor.getString(1));
                    //如果income属性为true，则加上一个加号，否则加一个减号
                    if (cursor.getString(5).equals("true")) {
                        bill_amount.add("+" + cursor.getString(2));
                    } else {
                        bill_amount.add("-" + cursor.getString(2));
                    }
                    bill_date.add(cursor.getString(3));
                    bill_note.add(cursor.getString(4));
                    billCount++;
                }
            }
            if (billCount == 0) {
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

//    右上角菜单内容

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAll) {
            confirmDialog();
        } else if (item.getItemId() == R.id.trashBin) {
            Intent intent = new Intent(MainActivity.this, TrashbinActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.passwordSetting) {
            Intent intent = new Intent(MainActivity.this, PasswordSettingActivity.class);
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
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //recreate();
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






















