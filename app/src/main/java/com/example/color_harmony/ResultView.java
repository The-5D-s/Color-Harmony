package com.example.color_harmony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ResultView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulte_view);

        TextView color1 = findViewById(R.id.color1);
        color1.setBackgroundColor(list.get(0).getRgb());
        color1.setText(String.format("#%06X", (0xFFFFFF & list.get(0).getRgb())));
        color1.setTextColor(list.get(0).getTitleTextColor());
        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color1.getText());
                Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        TextView color2 = findViewById(R.id.color2);
        color2.setBackgroundColor(list.get(1).getRgb());
        color2.setText(String.format("#%06X", (0xFFFFFF & list.get(1).getRgb())));
        color2.setTextColor(list.get(1).getTitleTextColor());
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color2.getText());
                Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        TextView color3 = findViewById(R.id.color3);
        color3.setBackgroundColor(list.get(2).getRgb());
        color3.setText(String.format("#%06X", (0xFFFFFF & list.get(2).getRgb())));
        color3.setTextColor(list.get(2).getTitleTextColor());
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color3.getText());
                Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        TextView color4 = findViewById(R.id.color4);
        color4.setBackgroundColor(list.get(3).getRgb());
        color4.setText(String.format("#%06X", (0xFFFFFF & list.get(3).getRgb())));
        color4.setTextColor(list.get(3).getTitleTextColor());
        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color4.getText());
                Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        TextView color5 = findViewById(R.id.color5);
        color5.setBackgroundColor(list.get(4).getRgb());
        color5.setText(String.format("#%06X", (0xFFFFFF & list.get(4).getRgb())));
        color5.setTextColor(list.get(4).getTitleTextColor());
        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color5.getText());
                Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        TextView color6 = findViewById(R.id.color6);
        color6.setBackgroundColor(list.get(5).getRgb());
        color6.setText(String.format("#%06X", (0xFFFFFF & list.get(5).getRgb())));
        color6.setTextColor(list.get(5).getTitleTextColor());
        color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)ResultView.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(color6.getText());
                Toast.makeText(ResultView.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }
}