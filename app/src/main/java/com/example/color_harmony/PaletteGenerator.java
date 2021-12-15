package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Color;
import com.amplifyframework.datastore.generated.model.ColorPalette;
import com.amplifyframework.datastore.generated.model.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaletteGenerator extends AppCompatActivity {
    // Generate palette synchronously and return it
    Uri imageUri;
    Bitmap bitmap;
    List<User> users = new ArrayList<>();

    //    File file = new File("/Users/macbookpro/projects/401/test/Color-Harmony/app/src/main/res/drawable/image1.jpg");
//
//    Bitmap image = BitmapFactory.decodeFile(file.getAbsolutePath());
    public Palette createPaletteSync(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }

    // Generate palette asynchronously and use it on a different
    // thread using onGenerated()
    public void createPaletteAsync(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                // Use generated instance

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_generator);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);

        Intent intent = getIntent();


        if (getIntent().getData() == null) {
            bitmap = (Bitmap) intent.getParcelableExtra("image");
        } else {
            imageUri = getIntent().getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<Palette.Swatch> list = createPaletteSync(bitmap).getSwatches();

        Button save = findViewById(R.id.save);
        save.setBackgroundColor(createPaletteSync(bitmap).getDominantSwatch().getBodyTextColor());
        save.setTextColor(createPaletteSync(bitmap).getDominantSwatch().getTitleTextColor());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Amplify.Auth.fetchAuthSession(
                        result -> {
                            Log.i("AmplifyQuickstart", result.toString());
                            if (!result.isSignedIn()) {
                                PaletteGenerator.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(PaletteGenerator.this, "Please SignIn", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                Amplify.DataStore.query(
                                        User.class, User.NAME.contains(Amplify.Auth.getCurrentUser().getUsername()),
                                        items -> {
                                            while (items.hasNext()) {
                                                User item = items.next();
                                                users.add(item);
                                                Log.i("Amplify", "Id " + item.getName());
                                            }
                                            System.out.println(users.toString());
                                            System.out.println(Amplify.Auth.getCurrentUser().getUsername() + "before if");
                                            User user = users.get(0);
                                            System.out.println(user.getName() + "user name");
                                            System.out.println(Amplify.Auth.getCurrentUser().getUsername() + "after if");

                                            ColorPalette item = ColorPalette.builder()
                                                    .userId(user.getId())
                                                    .build();
                                            Amplify.DataStore.save(
                                                    item,
                                                    success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
                                                    error -> Log.e("Amplify", "Could not save item to DataStore", error)
                                            );

                                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                                            byte[] bitmapdata = bos.toByteArray();
                                            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);


                                            String key = item.getId();

                                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PaletteGenerator.this);
                                            sharedPreferences.edit().putString("key", key).apply();

                                            Amplify.Storage.uploadInputStream(
                                                    key,
                                                    bs,
                                                    imageResult -> Log.i("UPLOAD", "Successfully uploaded: " + imageResult.getKey()),
                                                    storageFailure -> Log.e("UPLOAD", "Upload failed", storageFailure)
                                            );


                                            for (int i = 0; i < 6; i++) {
                                                Color color = Color.builder()
                                                        .rgb(String.format("#%06X", (0xFFFFFF & list.get(i).getRgb())))
                                                        .paletteId(item.getId())
                                                        .build();
                                                Amplify.DataStore.save(
                                                        color,
                                                        success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
                                                        error -> Log.e("Amplify", "Could not save item to DataStore", error)
                                                );
                                            }
                                        },
                                        failure -> Log.e("Amplify", "Could not query DataStore", failure)
                                );

                            }
                        },
                        error -> Log.e("AmplifyQuickstart", error.toString())
                );
            }
        });
        TextView color1 = findViewById(R.id.color1);
        color1.setBackgroundColor(list.get(0).getRgb());
        color1.setText(String.format("#%06X", (0xFFFFFF & list.get(0).getRgb())));
        color1.setTextColor(list.get(0).getTitleTextColor());
        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) PaletteGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color1.getText());
                Toast.makeText(PaletteGenerator.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        TextView color2 = findViewById(R.id.color2);
        color2.setBackgroundColor(list.get(1).getRgb());
        color2.setText(String.format("#%06X", (0xFFFFFF & list.get(1).getRgb())));
        color2.setTextColor(list.get(1).getTitleTextColor());
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) PaletteGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color2.getText());
                Toast.makeText(PaletteGenerator.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        TextView color3 = findViewById(R.id.color3);
        color3.setBackgroundColor(list.get(2).getRgb());
        color3.setText(String.format("#%06X", (0xFFFFFF & list.get(2).getRgb())));
        color3.setTextColor(list.get(2).getTitleTextColor());
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) PaletteGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color3.getText());
                Toast.makeText(PaletteGenerator.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        TextView color4 = findViewById(R.id.color4);
        color4.setBackgroundColor(list.get(3).getRgb());
        color4.setText(String.format("#%06X", (0xFFFFFF & list.get(3).getRgb())));
        color4.setTextColor(list.get(3).getTitleTextColor());
        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) PaletteGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color4.getText());
                Toast.makeText(PaletteGenerator.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        TextView color5 = findViewById(R.id.color5);
        color5.setBackgroundColor(list.get(4).getRgb());
        color5.setText(String.format("#%06X", (0xFFFFFF & list.get(4).getRgb())));
        color5.setTextColor(list.get(4).getTitleTextColor());
        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) PaletteGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color5.getText());
                Toast.makeText(PaletteGenerator.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        TextView color6 = findViewById(R.id.color6);
        color6.setBackgroundColor(list.get(5).getRgb());
        color6.setText(String.format("#%06X", (0xFFFFFF & list.get(5).getRgb())));
        color6.setTextColor(list.get(5).getTitleTextColor());
        color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) PaletteGenerator.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color6.getText());
                Toast.makeText(PaletteGenerator.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });


        LinearLayout rootLayout = findViewById(R.id.rootLayout2);

        rootLayout.setBackgroundColor(createPaletteSync(bitmap).getDominantSwatch().getRgb());
        ImageView image = findViewById(R.id.imageHolder2);
        image.setImageBitmap(bitmap);
    }


}
