package com.example.myapplication.newcontentprovider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class NewContactsAdapter extends RecyclerView.Adapter<NewContactsAdapter.viewHolder> {

    private Context context;
    private ArrayList<NewContactsModel> contactsList;


    public NewContactsAdapter(Context context, ArrayList<NewContactsModel> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_list_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NewContactsModel newContactsModel = contactsList.get(holder.getAdapterPosition());

        holder.tvCallDisPlayName.setText(newContactsModel.getContactName());
        holder.tvCallDisplayNumber.setText(newContactsModel.getContactNumber());

        Picasso.get().load(newContactsModel.getContactImage())
                .placeholder(R.drawable.ic_call_image).into(holder.ivCallDisplayImage);

        holder.itemView.setOnClickListener(v -> {
            Intent objIntent = new Intent(context, SingleCallDetailActivity.class);
            objIntent.putExtra("contactModel", newContactsModel);
            context.startActivity(objIntent);
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCallDisplayImage;
        private TextView tvCallDisPlayName;
        private TextView tvCallDisplayNumber;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ivCallDisplayImage = itemView.findViewById(R.id.iv_contact_image);
            tvCallDisPlayName = itemView.findViewById(R.id.tv_contact_name);
            tvCallDisplayNumber = itemView.findViewById(R.id.tv_contact_number);

        }
    }
}