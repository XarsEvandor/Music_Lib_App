package com.example.final_project;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.Database.AlbumHandler;
import com.example.final_project.Database.DBManager;
import com.example.final_project.Database.UserHandler;
import com.example.final_project.Model.Album;
import com.example.final_project.Model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    DBManager dbManager;
    SQLiteDatabase sqLiteDatabase;
    UserHandler userHandler;
    TextView signup;
    EditText username;
    EditText password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup=findViewById(R.id.signup);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login=findViewById(R.id.login);
        dbManager=new DBManager(getApplicationContext());
        sqLiteDatabase=dbManager.openDB();
        userHandler = new UserHandler(sqLiteDatabase);

        //TEMP CODE ================================================================================

        AlbumHandler albumHandler = new AlbumHandler(sqLiteDatabase);
        ArrayList<String> artists1 = new ArrayList<>();
        artists1.add("artist1");
        artists1.add("artist2");
        artists1.add("artist3");

        ArrayList<String> artists2 = new ArrayList<>();
        artists2.add("artist4");
        artists2.add("artist5");
        artists2.add("artist6");

        Album album1 = new Album("album1", "good album",
                new GregorianCalendar(2000, 8, 15).getTime(), artists1,
                "https://upload.wikimedia.org/wikipedia/en/0/08/Clutch_-_Earth_Rocker.png", 4);

        Album album2 = new Album("album3", "very good album",
                new GregorianCalendar(1999, 9, 25).getTime(), artists1,
                "https://upload.wikimedia.org/wikipedia/el/1/17/Dark_Side_of_the_Moon.jpg", 5);

        Album album3 = new Album("album2", "very good album",
                new GregorianCalendar(1999, 9, 25).getTime(), artists1,
                "https://upload.wikimedia.org/wikipedia/en/0/08/Clutch_-_Earth_Rocker.png", 5);

        Album album4 = new Album("album4", "very good album",
                new GregorianCalendar(1999, 9, 25).getTime(), artists1,
                "https://upload.wikimedia.org/wikipedia/el/1/17/Dark_Side_of_the_Moon.jpg", 5);

//        albumHandler.addAlbum(album1);
//
//        albumHandler.addAlbum(album2);
//
//        albumHandler.addAlbum(album3);
//
//        albumHandler.addAlbum(album4);
//
        if(userHandler.checkUsernameExists("mUsr"))
        {
            Toast.makeText(getApplicationContext(),"Username already exists", Toast.LENGTH_LONG).show();
        }
        else
        {
            userHandler.addUser(new User("mUsr","mock"));
            Intent intent=new Intent(MainActivity.this,LibraryActivity.class);
            int id = userHandler.getUserId("mUsr");

            userHandler.addAlbumToUser(1,1);
            userHandler.addAlbumToUser(2,1);
            userHandler.addAlbumToUser(3,1);
            userHandler.addAlbumToUser(4,1);

            intent.putExtra("_id", id);
            startActivity(intent);
        }

        Intent intent=new Intent(MainActivity.this,LibraryActivity.class);
        int id = userHandler.getUserId("mUsr");
        intent.putExtra("_id", id);
        startActivity(intent);

        //==========================================================================================



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                if (!userHandler.checkUsernameExists(username.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Username not found", Toast.LENGTH_LONG).show();
                }
                else{
                    if (!userHandler.validateUser(username.getText().toString(),password.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Incorrect password", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intent=new Intent(MainActivity.this,LibraryActivity.class);
                        int id = userHandler.getUserId(username.getText().toString());
                        intent.putExtra("_id", id);
                        startActivity(intent);
                    }
                }
            }

        });
    }
}