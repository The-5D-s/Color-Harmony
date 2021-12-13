package com.example.color_harmony;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Palette;

import java.util.ArrayList;
import java.util.List;

public class PaletteAdapter extends RecyclerView.Adapter<PaletteAdapter.PaletteViewHolder>{
    List<Palette> allPalette = new ArrayList<Palette>();


    public PaletteAdapter(List<Palette> allPalette){
        this.allPalette = allPalette;
    }
    public static class PaletteViewHolder extends RecyclerView.ViewHolder {
        public Palette palette;
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
        holder.palette = allPalette.get(position);

       ImageView iv= holder.itemView.findViewById(R.id.imageFragmentProfile);
       RecyclerView rv = holder.itemView.findViewById(R.id.paletteRVProfile);

//       iv.setImageResource(holder.palette.getId());

//        TextView taskTitle  = holder.itemView.findViewById(R.id.titleInFregment);
//        taskTitle.setText(holder.task.getTitle());

    }



    @Override
    public int getItemCount() {
        return allPalette.size();
    }


}