package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Palette;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    RecyclerView FavRecyclerView;
    Intent intent;
    ArrayList <Palette> palettesData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        TextView userName = findViewById(R.id.userProfileName);
        ImageView userImage = findViewById(R.id.userProfileImage);

        FavRecyclerView = findViewById(R.id.profileRV);
        FavRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FavRecyclerView.setAdapter(new PaletteAdapter(palettesData));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Profile.this);
        String paletteName = sharedPreferences.getString("palette", "palette");
        String imgName = sharedPreferences.getString("imgData", "imgData");




    }

}