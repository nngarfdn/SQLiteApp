package com.android.sqliteapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "student_db";
    private static final int DATABASE_VERSION = 1 ;
    private static final String TABLE_STUDENT = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_ALAMAT = "alamat";
    private static final String CREATE_TABLE_STUDENTS =
            "CREATE TABLE " + TABLE_STUDENT
                    +"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + KEY_NAMA + " TEXT, " + KEY_ALAMAT + " TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_STUDENT +"'");
        onCreate(db);
    }

    public long addStudent(String nama, String alamat){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAMA,nama);
        values.put(KEY_ALAMAT,alamat);
        return database.insert(TABLE_STUDENT, null, values);
    }

    public ArrayList<Map<String,String>> getAllStudents(){
        ArrayList<Map<String,String>> arrayList = new ArrayList<>();
        String nama;
        String alamat;
        int id;
        String selectQuery = "SELECT * FROM "+TABLE_STUDENT;
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                nama = cursor.getString(cursor.getColumnIndex(KEY_NAMA));
                alamat = cursor.getString(cursor.getColumnIndex(KEY_ALAMAT));
                Map<String,String> itemMap = new HashMap<>();
                itemMap.put(KEY_ID,id+"");
                itemMap.put(KEY_NAMA,nama);
                itemMap.put(KEY_ALAMAT,alamat);
                arrayList.add(itemMap);
            }while (cursor.moveToNext());
        } return arrayList;
    }


    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_STUDENT + " WHERE " +
                KEY_ID + " = '" + id +"'";
        db.execSQL(deleteQuery);
        db.close();
    }


}