package com.example.myapplication.newcontentprovider;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.contentproviders.ContactsAdapter;
import com.example.myapplication.contentproviders.ContactsModel;
import com.example.myapplication.contentproviders.SingleCallDetailActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class NewContactsAdapter extends RecyclerView.Adapter<NewContactsAdapter.viewHolder> {
    Context context;
    ArrayList<NewContactsModel> contactsList;

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
        NewContactsModel contactsModel = contactsList.get(position);
        holder.tvCallDisPlayName.setText(contactsModel.getCallName());
        holder.tvCallDisplayNumber.setText(contactsModel.getCallNumber());

        Bitmap image = null;
        if (!contactsModel.getCallImage().equals("")
                && contactsModel.getCallImage() != null) {
            image = BitmapFactory.decodeFile(contactsModel.getCallImage());
            if (image != null)
                holder.ivCallDisplayImage.setImageBitmap(image);
            else {
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_call_image);
                holder.ivCallDisplayImage.setImageBitmap(image);
            }
        } else {
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_call_image);
            holder.ivCallDisplayImage.setImageBitmap(image);
        }

        //Picasso.get().load(contactsModel.getCallImage()).placeholder(R.drawable.ic_call_image).into(holder.ivCallDisplayImage);
        holder.itemView.setOnClickListener(v -> {
            Intent in = new Intent(context, SingleCallDetailActivity.class);
            in.putExtra("name", contactsModel.getCallName());
            in.putExtra("number", contactsModel.getCallNumber());
            in.putExtra("callerId", contactsModel.getCallID());
            in.putExtra("callerImage", contactsModel.getCallImage());
            in.putExtra("callerEmail", contactsModel.getCallEmail());
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
            tvCallDisplayNumber = itemView.findViewById(R.id.call_number);
        }
    }
}
/*extends BaseAdapter{
    private Context context;
    private ArrayList<NewContactsModel> arrayList;

    public NewContactsAdapter(Context context, ArrayList<NewContactsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {

        return arrayList.size();
    }

    @Override
    public NewContactsModel getItem(int position) {

        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        NewContactsModel model = arrayList.get(position);
        ViewHodler holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_contacts_view, parent, false);
            holder = new ViewHodler();
            holder.contactImage = (ImageView) convertView
                    .findViewById(R.id.contactImage);
            holder.contactName = (TextView) convertView
                    .findViewById(R.id.contactName);
            holder.contactEmail = (TextView) convertView
                    .findViewById(R.id.contactEmail);
            holder.contactNumber = (TextView) convertView
                    .findViewById(R.id.contactNumber);
            holder.contactOtherDetails = (TextView) convertView
                    .findViewById(R.id.contactOtherDetails);

            convertView.setTag(holder);
        } else {
            holder = (ViewHodler) convertView.getTag();
        }

        // Set items to all view
        if (!model.getContactName().equals("")
                && model.getContactName() != null) {
            holder.contactName.setText(model.getContactName());
        } else {
            holder.contactName.setText("No Name");
        }
        if (!model.getContactEmail().equals("")
                && model.getContactEmail() != null) {
            holder.contactEmail.setText("EMAIL ID - n" + model.getContactEmail());
        } else {
            holder.contactEmail.setText("EMAIL ID - n" + "No EmailId");
        }

        if (!model.getContactNumber().equals("")
                && model.getContactNumber() != null) {
            holder.contactNumber.setText("CONTACT NUMBER - n" + model.getContactNumber());
        } else {
            holder.contactNumber.setText("CONTACT NUMBER - n" + "No Contact Number");
        }

        if (!model.getContactOtherDetails().equals("")
                && model.getContactOtherDetails() != null) {
            holder.contactOtherDetails.setText("OTHER DETAILS - n" + model.getContactOtherDetails());
        } else {
            holder.contactOtherDetails.setText("OTHER DETAILS - n" + "Other details are empty");
        }

        // Bitmap for imageview
        Bitmap image = null;
        if (!model.getContactPhoto().equals("")
                && model.getContactPhoto() != null) {
            image = BitmapFactory.decodeFile(model.getContactPhoto());

            if (image != null)
                holder.contactImage.setImageBitmap(image);
                // is not null
            else {
                image = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ic_call_image);
                holder.contactImage.setImageBitmap(image);
            }
        } else {
            image = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_call_image);
            holder.contactImage.setImageBitmap(image);
        }
        return convertView;
    }

    // View holder to hold views
    private class ViewHodler {
        ImageView contactImage;
        TextView contactName, contactNumber, contactEmail, contactOtherDetails;
    }
}*/

/*
        extends RecyclerView.Adapter<NewContactsAdapter.viewHolder>{

    private Context context;
    private ArrayList<NewContactsModel> arrayList;

    public NewContactsAdapter(Context context, ArrayList<NewContactsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public NewContactsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_contacts_view, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewContactsAdapter.viewHolder holder, int position) {
        NewContactsModel model = arrayList.get(position);

        holder.tvNewContactsName.setText(model.getNewContactsName());
        holder.tvNewContactsNumber.setText(model.getNewContactsNumber());

        Bitmap image = null;
        image = BitmapFactory.decodeFile(model.getNewContactsImage());
        if (image!=null){
            holder.ivNewContactsImage.setImageBitmap(image);
        }else {
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_call_image);
            holder.ivNewContactsImage.setImageBitmap(image);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private ImageView ivNewContactsImage;
        private TextView tvNewContactsName;
        private TextView tvNewContactsNumber;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            ivNewContactsImage = itemView.findViewById(R.id.new_contact_image);
            tvNewContactsName = itemView.findViewById(R.id.new_contact_name);
            tvNewContactsNumber = itemView.findViewById(R.id.new_contact_number);
        }
    }
}*/
