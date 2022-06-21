package com.example.myapplication.contentproviders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

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

       /* Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        String Name = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        int Number = Integer.parseInt(ContactsContract.CommonDataKinds.Phone.NUMBER);
        holder.tvCallDisPlayName.setText(Name);
        holder.tvCallDisplayNumber.setText(Number);

*/
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvCallDisPlayName;
        private TextView tvCallDisplayNumber;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvCallDisPlayName = itemView.findViewById(R.id.tv_call_name);
            tvCallDisplayNumber = itemView.findViewById(R.id.tv_call_number);
        }
    }
}
