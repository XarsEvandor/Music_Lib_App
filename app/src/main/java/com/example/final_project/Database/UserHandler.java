package com.example.final_project.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_project.Model.Album;
import com.example.final_project.Model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class UserHandler {

    SQLiteDatabase sqLiteDatabase;

    public UserHandler(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public  void addUser(User user){
        ContentValues userValues=new ContentValues(); //add data with contentValues
        userValues.put("username",user.getUsername());
        userValues.put("password",user.getPassword());
        sqLiteDatabase.insert("user",null,userValues);
    }

    public void addAlbumToUser(int albumid, int userid){
        ContentValues userAlbumvalues=new ContentValues(); //add data with contentValues
        userAlbumvalues.put("albumid",albumid);
        userAlbumvalues.put("userid",userid);
        sqLiteDatabase.insert("user_album_join",null,userAlbumvalues);
    }

    public void deleteAlbumOfUser (int albumid, int userid){
        Cursor cursor=sqLiteDatabase.rawQuery
                ("select * from user_album_join where userid='" + userid +"'" +"' and albumid='" + albumid+"'",null);
        cursor.moveToFirst();
        int _id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

        sqLiteDatabase.delete("user_album_join", "_id=?", new String[]{String.valueOf(_id)});
    }

    public Cursor getUserAlbums(int id){
        Cursor cursor=sqLiteDatabase.rawQuery("select * from album  where _id in " +
                "(select albumid from user_album_join where userid="+id+")",null); //nested subquery
        return cursor;
    }

    public ArrayList<Album> getAlbumArrayList(int id){
        AlbumHandler albumHandler;
        Cursor albumCursor;
        Cursor artistCursor;
        ArrayList<Album> albums = new ArrayList<>();

        albumCursor = this.getUserAlbums(id);

        albumHandler = new AlbumHandler(sqLiteDatabase);

        try{
            albumCursor.moveToFirst();
            while(!albumCursor.isAfterLast()){
                String title = albumCursor.getString(albumCursor.getColumnIndexOrThrow("title"));
                String desc = albumCursor.getString(albumCursor.getColumnIndexOrThrow("description"));
                String cover = albumCursor.getString(albumCursor.getColumnIndexOrThrow("cover"));
                int rating = albumCursor.getInt(albumCursor.getColumnIndexOrThrow("rating"));
                ArrayList <String> artistList = new ArrayList<>();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = sdf.parse(albumCursor.getString(albumCursor.getColumnIndexOrThrow("releaseDate")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Retrieve artists
                artistCursor = albumHandler.getAlbumArtists(albumCursor.getInt(albumCursor.getColumnIndexOrThrow("_id")));

                try {
                    artistCursor.moveToFirst();
                    while(!artistCursor.isAfterLast()) {
                        artistList.add(artistCursor.getString(artistCursor.getColumnIndexOrThrow("name")));
                        artistCursor.moveToNext();
                    }
                }
                finally {
                    artistCursor.close();
                }

                Album album = new Album(title, desc, date, artistList, cover, rating);
                albums.add(album);
                albumCursor.moveToNext();
            }
        }
        finally {
            albumCursor.close();
        }

        return albums;
    }

    public boolean checkUsernameExists(String username){
        Cursor cursor=sqLiteDatabase.rawQuery("select * from user where username='" + username +"'",null);
        return cursor.moveToFirst();
    }

    public boolean validateUser(String username, String password){
        Cursor cursor=sqLiteDatabase.rawQuery("select * from user where username='" + username +"' and password='" + password+"'",null);
        return cursor.moveToFirst();
    }

    public int getUserId(String username){
        Cursor cursor=sqLiteDatabase.rawQuery("select * from user where username='" + username +"'",null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
    }

}
