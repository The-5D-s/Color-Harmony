package com.example.color_harmony;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Color;
import com.amplifyframework.datastore.generated.model.ColorPalette;
import com.amplifyframework.datastore.generated.model.Palette;
import com.amplifyframework.datastore.generated.model.Test;
import com.amplifyframework.datastore.generated.model.User;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.color_harmony.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private static final int CAMERA_REQUEST = 13;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Amplify", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("Amplify", "Could not initialize Amplify", error);
        }

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );


        TextView username = findViewById(R.id.username);
        TextView email = findViewById(R.id.email);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleShared(intent);
            }
        }


        Button log = findViewById(R.id.log);

//        Amplify.Auth.fetchAuthSession(
//                user -> {
//                    if (user.isSignedIn()) {
//
//                        log.setText("Log out");
//                    } else {
//
//                        log.setText("Log in");
//                    }
//                },
//                failure -> Log.e("Amplify", "Could not query DataStore", failure)
//        );


//        username.setText(Amplify.Auth.getCurrentUser().getUsername());
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain2.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void profile(MenuItem item) {

        Intent i = new Intent(MainActivity.this, Profile.class);
        MainActivity.this.startActivity(i);


    }


    public void openProfile(MenuItem item) {
        Amplify.Auth.fetchAuthSession(
                user -> {
                    if (user.isSignedIn()) {
                        Intent i = new Intent(MainActivity.this, Profile.class);
                        MainActivity.this.startActivity(i);

                    } else {
                        Intent i = new Intent(MainActivity.this, Login.class);
                        MainActivity.this.startActivity(i);

                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }

    public void openSettings(MenuItem item) {
        Intent i = new Intent(MainActivity.this, Preference.class);
        MainActivity.this.startActivity(i);

    }

    public void login(View v) {
        Intent i = new Intent(MainActivity.this, Login.class);
        MainActivity.this.startActivity(i);

    }


    public void signout(MenuItem item) {
        Amplify.Auth.signOut(
                () -> Log.i("AuthQuickstart", "Signed out successfully"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
        finish();

    }

    public void openFiles(View v) {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a File");
        startActivityForResult(chooseFile, 12);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void openCamera(View v) {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    Uri uri;

    @SuppressLint("SetTextI18n")
    void handleShared(Intent intent) {
         uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        Intent otherIntent = new Intent(MainActivity.this, PaletteGenerator.class);
        TextView btn = findViewById(R.id.fab);
        ImageView taskimage = findViewById(R.id.myImage);
        otherIntent.setData(uri);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            taskimage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(otherIntent);
            }
        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        ImageView taskimage = findViewById(R.id.myImage);
        Intent i = new Intent(MainActivity.this, PaletteGenerator.class);
        TextView btn = findViewById(R.id.fab);
        if (requestCode == 12 && resultCode == Activity.RESULT_OK) {
             uri = resultData.getData();
            i.setData(uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                taskimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.startActivity(i);
                }
            });



        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) resultData.getExtras().get("data");
            i.putExtra("image", photo);
            taskimage.setImageBitmap(photo);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.startActivity(i);
                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        Button log = findViewById(R.id.log);

//        Amplify.Auth.fetchAuthSession(
//                user -> {
//                    if (user.isSignedIn()) {
//
//                        log.setText("Log out");
//                    } else {
//
//                        log.setText("Log in");
//                    }
//                },
//                failure -> Log.e("Amplify", "Could not query DataStore", failure)
//        );
    }

}