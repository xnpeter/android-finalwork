package com.example.finalwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

//一个帮助类，用来辅助建立、更新和打开数据库
//有onCreate和onUpgrade两个必须重载的函数
public class MyDatabaseHelper extends SQLiteOpenHelper {

    //    声明数据库的基本信息，比如数据库名、表名与版本号
    private Context context;
    private static final String DB_NAME = "BillData.db";
    private static final int DB_VERSION = 1;

    private static final String DB_TABLE = "bill";
    private static final String DB_TABLE2 = "passwordTable";
    //    各个属性名称：id、金额、日期、便签、类型
    private static final String KEY_ID = "_id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_DATE = "date";
    private static final String KEY_NOTE = "note";
    private static final String KEY_INCOME = "is_income";
    private static final String KEY_DELETED = "deleted";

    private static final String KEY_PASSWORD = "password";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //        这一段是创建SQL表的命令，就要这个格式
        String DB_CREATE = "CREATE TABLE " + DB_TABLE +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TYPE + " TEXT, " +
                KEY_AMOUNT + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_NOTE + " TEXT, " +
                KEY_INCOME + " TEXT, " +
                KEY_DELETED + " TEXT);";
        db.execSQL(DB_CREATE);//创建表

        String DB_CREATE2 = "CREATE TABLE " + DB_TABLE2 +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_PASSWORD + " TEXT);";
        db.execSQL(DB_CREATE2);//创建表

//        默认密码为空
        ContentValues cv = new ContentValues();
        cv.put(KEY_PASSWORD,"password");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        没做数据转移，仅仅是删除原表后建立新表
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    //    增添数据
    public void addData(String type, String amount, String date, String note, String is_income) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TYPE,type);
        cv.put(KEY_AMOUNT,amount);
        cv.put(KEY_DATE,date);
        cv.put(KEY_NOTE,note);
        cv.put(KEY_INCOME,is_income);
        cv.put(KEY_DELETED,"false");
        long result = db.insert(DB_TABLE,null,cv);
//        如果成功则显示相应Toast，失败亦然
        if (result == -1) {
            Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
        }
    }

//    更新数据

    public void updateData(String row_id, String type, String amount, String date, String note, String is_income) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TYPE,type);
        cv.put(KEY_AMOUNT,amount);
        cv.put(KEY_DATE,date);
        cv.put(KEY_NOTE,note);
        cv.put(KEY_INCOME,is_income);
        long result = db.update(DB_TABLE, cv, "_id=?", new String[]{row_id});
//        如果成功则显示相应Toast，失败亦然
        if (result == -1) {
            Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
        }
    }

    //读取全部数据
    public Cursor readAllData(){
        String query = "SELECT * FROM " + DB_TABLE;
        //String params = "deleted=?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //将数据放入回收站，标为删除状态
    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_DELETED,"true");
        long result = db.update(DB_TABLE, cv, "_id=?", new String[]{row_id});
//        如果成功则显示相应Toast，失败亦然
        if (result == -1) {
            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "已删除", Toast.LENGTH_SHORT).show();
        }
    }


    //将数据从回收站收回，标为not deleted状态
    void recycleOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_DELETED,"false");
        long result = db.update(DB_TABLE, cv, "_id=?", new String[]{row_id});
//        如果成功则显示相应Toast，失败亦然
        if (result == -1) {
            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "已找回", Toast.LENGTH_SHORT).show();
        }
    }

    //删除主页上的全部数据（放入回收站）
    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_DELETED,"true");
        long result = db.update(DB_TABLE, cv, null, null);
//        如果成功则显示相应Toast，失败亦然
        if (result == -1) {
            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "已全部删除", Toast.LENGTH_SHORT).show();
        }
    }

    //删除主页上的全部数据（放入回收站）
    void cleanAllDeletedData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        long result = db.delete(DB_TABLE, "deleted=?", new String[]{"true"});
//        如果成功则显示相应Toast，失败亦然
        if (result == -1) {
            Toast.makeText(context, "清除失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "已全部清除", Toast.LENGTH_SHORT).show();
        }
    }

}





















