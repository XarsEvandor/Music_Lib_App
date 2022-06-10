package com.example.final_project.Database;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.final_project.Model.Album;
import com.example.final_project.Model.User;

import java.text.SimpleDateFormat;

public class DBManager extends SQLiteOpenHelper {
    public DBManager(Context context) {
        super(context, "musicLibDB", null, 1); //creates the db to work with
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //creates the tables. Is being called only once
        sqLiteDatabase.execSQL("create table user(" +
                "_id integer primary key autoincrement," +
                "username text, " +
                "password text);");

        sqLiteDatabase.execSQL("create table song(" +
                "_id integer primary key autoincrement," +
                "title text, " +
                "duration integer);");

        sqLiteDatabase.execSQL("create table artist(" +
                "_id integer primary key autoincrement," +
                "name text);");


        sqLiteDatabase.execSQL("create table album(" +
                "_id integer primary key autoincrement," +
                "title text, " +
                "cover text," +
                "rating integer," +
                "description text,"+
                "releaseDate date);");

        sqLiteDatabase.execSQL("create table user_album_join(" +
                "_id integer primary key autoincrement," +
                "albumid integer, " +
                "userid integer);");

        sqLiteDatabase.execSQL("create table song_album_join(" +
                "_id integer primary key autoincrement," +
                "albumid integer, " +
                "songid integer);");

        sqLiteDatabase.execSQL("create table artist_album_join(" +
                "_id integer primary key autoincrement," +
                "albumid integer, " +
                "artistid integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase openDB(){
        SQLiteDatabase sqLiteDatabase=null;
        try{
            sqLiteDatabase=getWritableDatabase();//connects to db. the first time also calls oncreate()
        }
        catch(Exception e){
        }
        return sqLiteDatabase;
    }

    @SuppressLint("Range")
    public int getLastInsertedId(SQLiteDatabase sqLiteDatabase){
        Cursor cursor=sqLiteDatabase.rawQuery("select * from user",null);
        cursor.moveToLast(); //move cursor to last position so as to retrive the last inserted id
        return cursor.getInt(cursor.getColumnIndex("_id"));
    }
}
