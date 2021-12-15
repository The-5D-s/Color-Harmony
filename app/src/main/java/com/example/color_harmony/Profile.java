package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.ColorPalette;
import com.amplifyframework.datastore.generated.model.Palette;
import com.amplifyframework.datastore.generated.model.User;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
//    RecyclerView FavRecyclerView;
    Intent intent;
    ArrayList <ColorPalette> palettesData = new ArrayList<>();
    List<User> users = new ArrayList<>();

    PaletteAdapter adapter = new PaletteAdapter(palettesData);
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        RecyclerView FavRecyclerView = findViewById(R.id.profileRV);
        FavRecyclerView.setLayoutManager(new LinearLayoutManager(Profile.this));
        FavRecyclerView.setAdapter(adapter);


        System.out.println("-------------------xxxx");
        TextView userName = findViewById(R.id.userProfileName);
        ImageView userImage = findViewById(R.id.userProfileImage);



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Profile.this);
        String paletteName = sharedPreferences.getString("palette", "palette");
        String imgName = sharedPreferences.getString("imgData", "imgData");




    }

    @Override
    protected void onResume() {
        super.onResume();

        Amplify.DataStore.query(
                User.class,User.NAME.contains(Amplify.Auth.getCurrentUser().getUsername()),
                items -> {
                    while (items.hasNext()) {
                        User item = items.next();
                        Amplify.DataStore.query(
                                ColorPalette.class,ColorPalette.USER_ID.contains(item.getId()),
                                items2 -> {
                                    while (items2.hasNext()) {
                                        ColorPalette item2 = items2.next();
                                        palettesData.add(item2);
                                        Log.i("Amplify", "Id " + item2.getId());
                                    }
                                    handler.post(runnable);
                                    System.out.println(palettesData.toString() + " palette");
                                    System.out.println("-------------------");

                                    System.out.println("--------------------asdfasf");
                                },
                                failure -> Log.e("Amplify", "Could not query DataStore", failure)
                        );
                        Log.i("Amplify", "Id " + item.getId());
                    }
                    handler.post(runnable);
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

    }

}