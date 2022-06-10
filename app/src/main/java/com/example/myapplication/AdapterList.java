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
<<<<<<< HEAD
    Context mContext;

    public AdapterList(modelView[] model, Context context) {
        this.model = model;
        this.mContext = context;
=======
    Context cntx;

    public AdapterList(modelView[] model, Context context) {
        this.model = model;
        this.cntx = context;
>>>>>>> e3349de0a07ea6cb0e8844f9692d11653d382ec1
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
<<<<<<< HEAD
        View view = LayoutInflater.from(mContext).inflate(R.layout.sample_list, parent, false);
=======
        View view = LayoutInflater.from(cntx).inflate(R.layout.sample_list, parent, false);
>>>>>>> e3349de0a07ea6cb0e8844f9692d11653d382ec1
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
