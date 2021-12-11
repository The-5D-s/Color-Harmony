package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    RecyclerView FavRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        TextView userName = findViewById(R.id.userProfileName);
        ImageView userImage = findViewById(R.id.userProfileImage);

        FavRecyclerView = findViewById(R.id.profileRV);
        

    }

}