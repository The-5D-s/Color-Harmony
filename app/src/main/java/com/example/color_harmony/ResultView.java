package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResultView extends AppCompatActivity {
List<Color> list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulte_view);

        String key = getIntent().getExtras().getString("palette");
        System.out.println(key + "--------------------");
        Amplify.DataStore.query(
                Color.class,Color.PALETTE_ID.contains(key),
                items -> {
                    while (items.hasNext()) {
                        Color item = items.next();
                        list.add(item);
                        Log.i("Amplify", "Id " + item.getId());
                    }
                    System.out.println(list.toString());
                    TextView color1 = findViewById(R.id.colorResult1);
                    color1.setBackgroundColor(android.graphics.Color.parseColor(list.get(0).getRgb()));
                    color1.setText(list.get(0).getRgb());
                    color1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            cm.setText(color1.getText());
                            Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });

                    TextView color2 = findViewById(R.id.colorResult2);
                    color2.setBackgroundColor(android.graphics.Color.parseColor(list.get(1).getRgb()));
                    color2.setText(list.get(1).getRgb());
                    color2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            cm.setText(color2.getText());
                            Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });

                    TextView color3 = findViewById(R.id.colorResult3);
                    color3.setBackgroundColor(android.graphics.Color.parseColor(list.get(2).getRgb()));
                    color3.setText(list.get(2).getRgb());
                    color3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            cm.setText(color3.getText());
                            Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });

                    TextView color4 = findViewById(R.id.colorResult4);
                    color4.setBackgroundColor(android.graphics.Color.parseColor(list.get(3).getRgb()));
                    color4.setText(list.get(3).getRgb());
                    color4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            cm.setText(color4.getText());
                            Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });

                    TextView color5 = findViewById(R.id.colorResult5);
                    color5.setBackgroundColor(android.graphics.Color.parseColor(list.get(4).getRgb()));
                    color5.setText(list.get(4).getRgb());
                    color5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            cm.setText(color5.getText());
                            Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });

                    TextView color6 = findViewById(R.id.colorResult6);
                    color6.setBackgroundColor(android.graphics.Color.parseColor(list.get(5).getRgb()));
                    color6.setText(list.get(5).getRgb());
                    color6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            cm.setText(color6.getText());
                            Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });
                    LinearLayout rootLayout = findViewById(R.id.rootLayout2);
                    rootLayout.setBackgroundColor(android.graphics.Color.parseColor(list.get(2).getRgb()));
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

        ImageView imageView = findViewById(R.id.imageHolder2);

        Amplify.Storage.downloadFile(
                key,
                new File(this.getFilesDir() + "/download.txt"),
                result -> {
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getAbsolutePath());
                    File imgFile = new File(result.getFile().getAbsolutePath());
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imageView.setImageBitmap(myBitmap);
                    }

                },
                error -> Log.e("MyAmplifyApp", "Download Failure", error)
        );




    }
}