package com.example.qltc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLite extends SQLiteOpenHelper {

    public final static String tableName = "QLTC";
    public final static String Id = "Id";
    public final static String Name = "Name";
    public final static String Date = "Date";
    public final static String Cost = "Cost";
    public final static String ThuChi = "ThuChi";


    public SQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists "+ tableName+ " ("
                + Id +" Integer primary key autoincrement, "
                + Name +" Text,"
                + Date + " Text,"
                + Cost +" Integer,"
                + ThuChi +" Integer)";
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+ tableName);
        onCreate(db);
    }

    public void addContact(ThuChi item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, item.getName());
        values.put(Date, item.getDate());
        values.put(Cost, item.getCost());
        values.put(ThuChi, item.isThuChi());
        db.insert(tableName, null, values);
        db.close();
    }

    public void upgradeContact(int id, ThuChi item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, item.getId());
        values.put(Name, item.getName());
        values.put(Date, item.getDate());
        values.put(Cost, item.getCost());
        values.put(ThuChi, item.isThuChi());
        db.update(tableName, values, Id +"=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from "+ tableName + " Where Id= " +id;
        db.execSQL(sql);
        db.close();
    }

    public ArrayList<ThuChi> getAllContact(){
        ArrayList<ThuChi> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " ORDER BY Cost DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            while (cursor.moveToNext()){

                ThuChi item = new ThuChi(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                        cursor.getInt(4));
                list.add(item);
            }
        }
        return list;
    }

    public ArrayList<ThuChi> getContactBySearch(String searchString){
        ArrayList<ThuChi> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + tableName;

        if (searchString != null && !searchString.isEmpty()) {
            sql += " WHERE Name LIKE '%" + searchString + "%'";
        }

        sql += " ORDER BY Cost DESC";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ThuChi item = new ThuChi(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                        cursor.getInt(4));
                list.add(item);
            }
            cursor.close();
        }
        db.close();

        return list;
    }

}
