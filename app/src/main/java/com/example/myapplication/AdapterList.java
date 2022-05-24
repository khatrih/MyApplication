package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterList extends RecyclerView.Adapter<AdapterList.viewHolder> {

    private modelView[] model;
    Context mContext;

    public AdapterList(modelView[] model, Context context) {
        this.model = model;
        this.mContext = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sample_list, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.image.setImageResource(model[position].getImageId());
        holder.name.setText(model[position].getName());
        holder.description.setText(model[position].getDescription());
    }

    @Override
    public int getItemCount() {
        return model.length;
    }
    

    public static class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, description;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.imageName);
            description = itemView.findViewById(R.id.imageDesc);
        }
    }
}
