package com.example.myapplication.to_do_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.to_do_list.Models.NotesModel;

import java.util.ArrayList;

public class RetrieveDataAdapter extends RecyclerView.Adapter<RetrieveDataAdapter.viewHolder> {
    private Context context;
    private ArrayList<NotesModel> notesModels;

    public RetrieveDataAdapter(Context context, ArrayList<NotesModel> notesModels) {
        this.context = context;
        this.notesModels = notesModels;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_list_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NotesModel model = notesModels.get(position);
        holder.tvTitle.setText(model.getNoteTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent in = new Intent(context, ToDoListAddNotesActivity.class);
            in.putExtra("title", model.getNoteTitle());
            in.putExtra("content", model.getNoteContent());
            in.putExtra("noteId", model.getNoteId());
            in.putExtra("Flag", true);
            context.startActivity(in);
        });

        holder.itemView.setOnLongClickListener(v -> {
            Bundle sendToBottomSheet = new Bundle();
            sendToBottomSheet.putString("noteId", model.getNoteId());
            DeleteItemBottomSheet deleteItemBottomSheet = new DeleteItemBottomSheet();
            deleteItemBottomSheet.setArguments(sendToBottomSheet);
            deleteItemBottomSheet.show(((FragmentActivity) context).
                    getSupportFragmentManager(), deleteItemBottomSheet.getTag());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notesModels.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titles_tv);
        }
    }
}
