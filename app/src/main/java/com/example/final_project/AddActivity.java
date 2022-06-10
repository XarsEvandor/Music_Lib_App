package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.final_project.API.RetrofitHandler;
import com.example.final_project.Controllers.ImageDownloaderAsync;
import com.example.final_project.Database.AlbumHandler;
import com.example.final_project.Database.DBManager;
import com.example.final_project.Database.UserHandler;
import com.example.final_project.Model.Album;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    Integer insertRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ImageView coverImage = findViewById(R.id.albumCoverImage);
        EditText title = findViewById(R.id.title);
        EditText artist = findViewById(R.id.artist);
        EditText releaseDate = findViewById(R.id.releaseDate);
        EditText desc = findViewById(R.id.desc);
        EditText coverURL = findViewById(R.id.cover);
        RatingBar ratingBar = findViewById(R.id.rating);
         insertRating = (int)ratingBar.getRating();


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 insertRating = (int) rating;
            }
        });


        // Set img url easily for testing
        coverURL.setText("https://images.rocking.gr/albumcovers/2019/500x500/tool.jpg");

        Button checkImage = findViewById(R.id.check_image_button);

        checkImage.setOnClickListener(view -> {
            try {
                String url = coverURL.getText().toString();
                new ImageDownloaderAsync(coverImage).execute(url);

            }
            catch (Exception e){
                Toast.makeText(AddActivity.this, "Failed to load image.", Toast.LENGTH_SHORT).show();
            }
        });


        Button autoFill = findViewById(R.id.auto_button);

        autoFill.setOnClickListener(v -> {
            RetrofitHandler retrofitHandler = new RetrofitHandler();
            String url = retrofitHandler.getImageURL(title.getText().toString(),
                    artist.getText().toString(), AddActivity.this);

            new ImageDownloaderAsync(coverImage).execute(url);
        });

        Button confirm = findViewById(R.id.confirm_button);

        //TODO: Make it possible to add multiple artists. Fab button with plus icon next to artist field.
        //TODO: Make it possible to add songs. Perhaps a new screen?
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String insertTitle = title.getText().toString();
                String insertArtist = artist.getText().toString();
                String insertReleaseDate = releaseDate.getText().toString();
                String insertDesc = desc.getText().toString();
                String url = coverURL.getText().toString();


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date date;
                try {
                    date = sdf.parse(insertReleaseDate);
                } catch (ParseException e) {
                    Toast.makeText(AddActivity.this, "Please use the correct format", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }

                ArrayList<String> artistArrayList = new ArrayList<>();
                artistArrayList.add(insertArtist);

                if(Objects.equals(insertTitle, "") || Objects.equals(insertArtist, "")
                    || date == null || Objects.equals(insertDesc, "") || Objects.equals(url, "")){
                    Toast.makeText(AddActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                Album album = new Album(insertTitle, insertDesc, date, artistArrayList, url, insertRating);

                int _id=getIntent().getExtras().getInt("_id");

                //TODO: make dbManager static
                DBManager dbManager = new DBManager(getApplicationContext());
                SQLiteDatabase sqLiteDatabase = dbManager.openDB();
                UserHandler userHandler = new UserHandler(sqLiteDatabase);
                AlbumHandler albumHandler = new AlbumHandler(sqLiteDatabase);

                albumHandler.addAlbum(album);
                userHandler.addAlbumToUser(_id, albumHandler.getAlbumId(album.getTitle(), album.getCover()));

                Intent i = new Intent(AddActivity.this, LibraryActivity.class);
                i.putExtra("_id", _id);
                startActivity(i);
            }
        });


    }
}