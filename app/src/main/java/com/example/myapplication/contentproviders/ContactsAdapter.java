package com.example.myapplication.contentproviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.newcontentprovider.SingleCallDetailActivity;

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
        holder.tvCallDisplayNumber.setText(contactsModel.getCallNumber());

        Bitmap image = null;
        if (!contactsModel.getCallImage().equals("")
                && contactsModel.getCallImage() != null) {
            image = BitmapFactory.decodeFile(contactsModel.getCallImage());
            if (image != null)
                holder.ivCallDisplayImage.setImageBitmap(image);
            else {
                image = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ic_call_image);
                holder.ivCallDisplayImage.setImageBitmap(image);
            }
        } else {
            image = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_call_image);
            holder.ivCallDisplayImage.setImageBitmap(image);
        }

        //Picasso.get().load().placeholder(R.drawable.ic_call_image).into(holder.ivCallDisplayImage);

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
            ivCallDisplayImage = itemView.findViewById(R.id.iv_contact_image);
            tvCallDisPlayName = itemView.findViewById(R.id.tv_contact_name);
            tvCallDisplayNumber = itemView.findViewById(R.id.tv_contact_number);
        }
    }
}
