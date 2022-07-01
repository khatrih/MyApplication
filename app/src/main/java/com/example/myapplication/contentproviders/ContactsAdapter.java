package com.example.myapplication.contentproviders;

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

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.viewHolder> {
    Context context;
    ArrayList<ContactsModel> contactsList;

    public ContactsAdapter(Context context, ArrayList<ContactsModel> contactsList) {
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
        ContactsModel contactsModel = contactsList.get(position);
        holder.tvCallDisPlayName.setText(contactsModel.getCallName());
        //holder.tvCallDisplayNumber.setText(contactsModel.getCallNumber());
        Picasso.get().load(contactsModel.getImage()).placeholder(R.drawable.ic_call_image).into(holder.ivCallDisplayImage);


        holder.itemView.setOnClickListener(v -> {
            Intent in = new Intent(context, SingleCallDetailActivity.class);
            context.startActivity(in);
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCallDisplayImage;
        private TextView tvCallDisPlayName;
        private TextView tvCallDisplayNumber;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ivCallDisplayImage = itemView.findViewById(R.id.call_image);
            tvCallDisPlayName = itemView.findViewById(R.id.call_name);
            //tvCallDisplayNumber = itemView.findViewById(R.id.call_number);
        }
    }
}
