package com.example.color_harmony;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Color;
import com.amplifyframework.datastore.generated.model.Palette;

import java.util.ArrayList;
import java.util.List;

public class colorAdapter extends RecyclerView.Adapter<colorAdapter.colorViewHolder> {
    List<Color> allColor = new ArrayList<Color>();


    public colorAdapter(List<Color> allColor){
        this.allColor = allColor;
    }

    public static class colorViewHolder extends RecyclerView.ViewHolder {
        public Color color;
        public View itemView;

        public colorViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    @NonNull
    @Override
    public colorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_color,parent,false);
        colorAdapter.colorViewHolder colorViewHolder = new colorAdapter.colorViewHolder(view);

        return colorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull colorViewHolder holder, int position) {
        holder.color = allColor.get(position);
        TextView color = holder.itemView.findViewById(R.id.hexColor);
        color.setBackgroundColor(android.graphics.Color.parseColor(holder.color.getRgb()));
        System.out.println(holder.color.getRgb() + " color adapter");

    }

    @Override
    public int getItemCount() {
        return allColor.size();
    }



}
