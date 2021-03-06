package com.example.color_harmony;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Color;
import com.amplifyframework.datastore.generated.model.ColorPalette;
import com.amplifyframework.datastore.generated.model.Palette;
import com.amplifyframework.datastore.generated.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PaletteAdapter extends RecyclerView.Adapter<PaletteAdapter.PaletteViewHolder>{
    List<ColorPalette> allPalette = new ArrayList<ColorPalette>();


    public PaletteAdapter(List<ColorPalette> allPalette){
        System.out.println("hi");
        this.allPalette = allPalette;
    }
    public static class PaletteViewHolder extends RecyclerView.ViewHolder {
        public ColorPalette palette;
        public View itemView;

        public PaletteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }


    @NonNull
    @Override
    public PaletteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_palette,parent,false);
        PaletteViewHolder paletteViewHolder = new PaletteViewHolder(view);


        return paletteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaletteViewHolder holder, int position) {
        System.out.println("--------------------------------------- obada");
        holder.palette = allPalette.get(position);
        List<Color> colorsArray = new ArrayList<>();

       ImageView iv= holder.itemView.findViewById(R.id.imageFragmentProfile);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(holder.itemView.getContext());
        String key = holder.palette.getId();
        Amplify.Storage.downloadFile(
                key,
                new File(holder.itemView.getContext().getFilesDir() + "/download.txt"),
                result -> {
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getAbsolutePath());
                    File imgFile = new File(result.getFile().getAbsolutePath());
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        iv.setImageBitmap(myBitmap);
                    }

                },
                error -> Log.e("MyAmplifyApp", "Download Failure", error)
        );


                Amplify.DataStore.query(
                        Color.class,Color.PALETTE_ID.contains(holder.palette.getId()),
                        colors -> {
                            while (colors.hasNext()) {
                                Color color = colors.next();
                                colorsArray.add(color);
                                Log.i("Amplify", "Id " + color.getId());
                            }
                            System.out.println("gkghjkkjjhggkk"+colorsArray.toString() +" colors");
                            TextView color1 = holder.itemView.findViewById(R.id.colorHex1);
                            color1.setBackgroundColor(android.graphics.Color.parseColor(colorsArray.get(0).getRgb()));

                            TextView color2 = holder.itemView.findViewById(R.id.colorHex2);
                            color2.setBackgroundColor(android.graphics.Color.parseColor(colorsArray.get(1).getRgb()));

                            TextView color3 = holder.itemView.findViewById(R.id.colorHex3);
                            color3.setBackgroundColor(android.graphics.Color.parseColor(colorsArray.get(2).getRgb()));


                            TextView color4 = holder.itemView.findViewById(R.id.colorHex4);
                            color4.setBackgroundColor(android.graphics.Color.parseColor(colorsArray.get(3).getRgb()));

                            TextView color5 = holder.itemView.findViewById(R.id.colorHex5);
                            color5.setBackgroundColor(android.graphics.Color.parseColor(colorsArray.get(4).getRgb()));


                            TextView color6 = holder.itemView.findViewById(R.id.colorHex6);
                            color6.setBackgroundColor(android.graphics.Color.parseColor(colorsArray.get(5).getRgb()));
                        },
                        failure -> Log.e("Amplify", "Could not query DataStore", failure)
                );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ResultView.class);
                System.out.println(holder.palette.getId() + " xxxxxxxxxx");
                i.putExtra("palette",holder.palette.getId());
                v.getContext().startActivity(i);
            }
        });



    }



    @Override
    public int getItemCount() {
        return allPalette.size();
    }


}