package com.example.mytask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactDb extends SQLiteOpenHelper {

    SQLiteDatabase db;
    Context context;

    public static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 3;

    public static final String CONTACT_TABLE = "_table";
    public static final String NAME = "_name";
    public static final String PHONE = "_phone";


    public ContactDb(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + CONTACT_TABLE + " ( " + NAME + " TEXT ," + PHONE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +CONTACT_TABLE);
        onCreate(db);
    }

   /* public void insertContact(String cname, String cnumber){
        db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME,cname);
        contentValues.put(PHONE,cnumber);
        db.insert(CONTACT_TABLE,null,contentValues);

    }*/

    //    Inserting data into the database
    public void insertData(String name, String number) {
        db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        try {
            content.put(NAME, name);
            content.put(PHONE, number);
            db.insert(CONTACT_TABLE, null, content);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Cursor getData(){
        db=this.getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        String query =" SELECT * FROM "+CONTACT_TABLE;
        Cursor data = db.rawQuery(query,null);
        return data;
        //String[] columns =new String[] {NAME,PHONE};
       // Cursor cursor = db.query("SELECT * ALL FROM"+CONTACT_TABLE,null,null,null,null,null,null);

       /*int iName = cursor.getColumnIndex(NAME);
        int iNumber = cursor.getColumnIndex(PHONE);
        String result="";
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            result=result+
                    "Name : " +cursor.getString(iName)+"\n"+
                    "Phone : " +cursor.getString(iNumber)+ "\n\n";
        }*/

    }

    public ArrayList<String> extractName(){
        ArrayList <String> arr = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + CONTACT_TABLE,null);
        if (cursor.getCount() != 0){
            if (cursor.moveToFirst()){
                do{
                    arr.add(cursor.getString(cursor.getColumnIndex(NAME)));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return arr;
    }

    public ArrayList<String> extractNumber(){
        ArrayList <String> arr = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + CONTACT_TABLE,null);
        if (cursor.getCount() != 0){
            if (cursor.moveToFirst()){
                do{
                    arr.add(cursor.getString(cursor.getColumnIndex(PHONE)));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return arr;
    }

}
