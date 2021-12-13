package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class PaletteGenerator extends AppCompatActivity {
    // Generate palette synchronously and return it




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

        Uri imageUri=getIntent().getData();
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Palette.Swatch> list = createPaletteSync(bitmap).getSwatches();

        ImageView color1 = findViewById(R.id.color1);
        color1.setBackgroundColor(list.get(0).getRgb());
        ImageView color2 = findViewById(R.id.color2);
        color2.setBackgroundColor(list.get(1).getRgb());
        ImageView color3 = findViewById(R.id.color3);
        color3.setBackgroundColor(list.get(2).getRgb());
        ImageView color4 = findViewById(R.id.color4);
        color4.setBackgroundColor(list.get(3).getRgb());
        ImageView color5 = findViewById(R.id.color5);
        color5.setBackgroundColor(list.get(4).getRgb());

        ImageView image = findViewById(R.id.imageHolder);
        image.setImageBitmap(bitmap);
    }
}
