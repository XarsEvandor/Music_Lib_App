package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Rating;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.final_project.Controllers.ImageDownloaderAsync;
import com.example.final_project.Database.AlbumHandler;
import com.example.final_project.Database.DBManager;
import com.example.final_project.Model.Album;
import com.example.final_project.Model.Song;
import com.google.gson.Gson;
import android.text.method.ScrollingMovementMethod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent i = getIntent();
        Album album = (new Gson().fromJson(i.getExtras().getString("Album"), Album.class));
        int _id = i.getExtras().getInt("_id");

        ImageView coverImage = findViewById(R.id.albumCoverImage);
        new ImageDownloaderAsync(coverImage).execute(album.getCover());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        TextView title = findViewById(R.id.albumTitle);
        TextView releaseDate = findViewById(R.id.releaseDate);
        RatingBar rating = findViewById(R.id.rating);
        TextView description = findViewById(R.id.desc);

        title.setText(album.getTitle());
        releaseDate.setText(getString(R.string.released, sdf.format(album.getReleaseDate())));
        rating.setRating(album.getRating());
        description.setText(album.getDescription());
        description.setMovementMethod(new ScrollingMovementMethod());

        ListView artists = findViewById(R.id.artistListView);
        ArrayAdapter<String> artistAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_details, R.id.artistListView, album.getArtists());

        artists.setAdapter(artistAdapter);

        DBManager dbManager = new DBManager(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = dbManager.openDB();
        AlbumHandler albumHandler = new AlbumHandler(sqLiteDatabase);

        ArrayList<Song> songArrayList = albumHandler.getSongList(_id);
        ArrayList<String> songNamesList = new ArrayList<>();

        for(Song song : songArrayList){
            songNamesList.add(song.getTitle());
        }

        ListView songs = findViewById(R.id.songListView);
        ArrayAdapter<String> songAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_details, R.id.songListView, songNamesList);

        songs.setAdapter(songAdapter);
    }
}