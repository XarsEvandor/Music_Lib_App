package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project.Database.DBManager;
import com.example.final_project.Database.UserHandler;
import com.example.final_project.Model.User;

public class RegisterActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register=findViewById(R.id.register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManager dbManager=new DBManager(getApplicationContext());
                SQLiteDatabase sqLiteDatabase=dbManager.openDB();
                UserHandler userHandler = new UserHandler(sqLiteDatabase);

                if(userHandler.checkUsernameExists(username.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Username already exists", Toast.LENGTH_LONG).show();
                }
                else
                {
                    userHandler.addUser(new User(username.getText().toString(),password.getText().toString()));
                    Intent intent=new Intent(RegisterActivity.this, LibraryActivity.class);
                    intent.putExtra("_id",dbManager.getLastInsertedId(sqLiteDatabase));

                    startActivity(intent);
                }

            }
        });


    }


}