package com.example.finalwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.finalwork.adapter.CustomAdapter;

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

        //滑动item需要的
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

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

    //左滑从回收站中放回主页
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            String  id;

//            bill_type.remove(position);
//            bill_amount.remove(position);
//            bill_date.remove(position);
//            bill_note.remove(position);

            id = bill_id.get(position);
            MyDatabaseHelper myDB = new MyDatabaseHelper(TrashbinActivity.this);
            myDB.recycleOneRow(id);

//            customAdapter.RemoveItem(position);
//            customAdapter.notifyItemRemoved(position);
//            customAdapter.notifyItemChanged(position);

            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);

//            recreate();
        }
    };

    //        创建右上角菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trashbin_menu_right, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cleanDeleteAll) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    //将数据放入Array，然后显示
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

    //弹出“全部删除”确认框
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除");
        builder.setMessage("您确认要永久清除全部账单吗？\n清除的账单不可找回");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(TrashbinActivity.this);
                myDB.cleanAllDeletedData();
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