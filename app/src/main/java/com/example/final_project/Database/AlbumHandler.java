package com.example.final_project.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_project.Model.Album;
import com.example.final_project.Model.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AlbumHandler {

    SQLiteDatabase sqLiteDatabase;

    public AlbumHandler(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void addAlbum(Album album){
        ContentValues albumValues=new ContentValues(); //add data with contentValues
        albumValues.put("title", album.getTitle());
        albumValues.put("cover", album.getCover());
        albumValues.put("rating", album.getRating());
        albumValues.put("description", album.getDescription());

        // To use the date we need to transform it into the correct format.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdf.format(album.getReleaseDate());

        albumValues.put("releaseDate", date);
        sqLiteDatabase.insert("album",null,albumValues);
    }

    public void addSongToAlbum(int albumid, int songid){
        ContentValues albumSongValues=new ContentValues(); //add data with contentValues
        albumSongValues.put("songid",songid);
        albumSongValues.put("albumid",albumid);
        sqLiteDatabase.insert("song_album_join",null,albumSongValues);
    }

    public void addArtistToAlbum(int albumid, int artistid){
        ContentValues albumArtistValues=new ContentValues(); //add data with contentValues
        albumArtistValues.put("artistid",artistid);
        albumArtistValues.put("albumid",albumid);
        sqLiteDatabase.insert("artist_album_join",null,albumArtistValues);
    }

    public Cursor getAlbumArtists(int id){
        Cursor cursor=sqLiteDatabase.rawQuery("select * from artist  where _id in " +
                "(select artistid from artist_album_join where albumid="+id+")",null); //nested subquery
        return cursor;
    }

    public Cursor getAlbumSongs(int id){
        Cursor cursor=sqLiteDatabase.rawQuery("select * from song  where _id in " +
                "(select songid from song_album_join where albumid="+id+")",null); //nested subquery
        return cursor;
    }

    public ArrayList<Song> getSongList(int id){
        Cursor songCursor = this.getAlbumSongs(id);
        String title;
        int duration;
        ArrayList<Song> songList = new ArrayList<>();

        try {
            songCursor.moveToFirst();
            while(!songCursor.isAfterLast()) {
                title = songCursor.getString(songCursor.getColumnIndexOrThrow("title"));
                duration = songCursor.getInt(songCursor.getColumnIndexOrThrow("duration"));

                Song song = new Song(title, duration);
                songList.add(song);
                songCursor.moveToNext();
            }
        }
        finally {
            songCursor.close();
        }

        return songList;
    }

    // Two identifiers in case we have albums with the same titles
    public int getAlbumId(String title, String cover){
        Cursor cursor=sqLiteDatabase.rawQuery("select * from album where title='" + title +"' and cover='" + cover+"'",null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
    }
}

