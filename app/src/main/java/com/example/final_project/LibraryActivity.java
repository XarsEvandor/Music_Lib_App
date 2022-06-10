package com.example.final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.final_project.Controllers.LibraryAlbumAdapter;
import com.example.final_project.Controllers.OnSwipeTouchListener;
import com.example.final_project.Database.AlbumHandler;
import com.example.final_project.Database.DBManager;
import com.example.final_project.Database.UserHandler;
import com.example.final_project.Model.Album;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;

public class LibraryActivity extends AppCompatActivity {

    ArrayList<Album> albums = new ArrayList<>();
    int _id;
    DBManager dbManager;
    SQLiteDatabase sqLiteDatabase;
    UserHandler userHandler;

    FloatingActionButton sortButton;
    FloatingActionButton addButton;

    private SensorManager sensorManager;
    private Sensor sensorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        _id=getIntent().getExtras().getInt("_id");
        dbManager = new DBManager(getApplicationContext());
        sqLiteDatabase = dbManager.openDB();

        userHandler = new UserHandler(sqLiteDatabase);

        albums = userHandler.getAlbumArrayList(_id);
        GridView gridview = findViewById(R.id.gridView);
        LibraryAlbumAdapter albumAdapter = new LibraryAlbumAdapter(this, albums);
        gridview.setAdapter(albumAdapter);

        gridview.setOnItemClickListener((parent, v, position, id) -> {
            // Send intent to SingleViewActivity
            Intent i = new Intent(LibraryActivity.this, DetailsActivity.class);

            Album album = albums.get(position);
            String JsonAlbum = (new Gson()).toJson(album);
            i.putExtra("Album", JsonAlbum);
            i.putExtra("_id", _id);
            startActivity(i);
        });

        gridview.setOnItemLongClickListener((adapterView, view, position, l) -> {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            Album album = albums.get(position);
                            AlbumHandler albumHandler = new AlbumHandler(sqLiteDatabase);
                            int albumId = albumHandler.getAlbumId(album.getTitle(), album.getCover());

                            userHandler.deleteAlbumOfUser(albumId, _id);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(LibraryActivity.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            return false;
        });




        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        View libView = findViewById(R.id.libView);
        SensorEventListener sensorEventListenerLight = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float floatSensorValue = event.values[0]; // lux

                // It better be REALLY BRIGHT if you wanna see the color change
                if (floatSensorValue < sensorLight.getMaximumRange() - 50){
                    libView.setBackgroundColor(Color.parseColor("#2E3133"));
                }
                else {
                    libView.setBackgroundColor(Color.parseColor("#45139F"));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(sensorEventListenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);


        sortButton = findViewById(R.id.fabSort);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    albumAdapter.sort(Comparator.comparing(Album::getTitle));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Requires at least API level 24", Toast.LENGTH_LONG).show();
                }
            }
        });

        addButton = findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LibraryActivity.this, AddActivity.class);
                i.putExtra("_id", _id);
                startActivity(i);
            }
        });

        libView.setOnTouchListener(new OnSwipeTouchListener(LibraryActivity.this) {
            public void onSwipeRight() {
                Toast.makeText(LibraryActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
        });



    }

}