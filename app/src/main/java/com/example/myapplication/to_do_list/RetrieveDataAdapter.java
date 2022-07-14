package com.example.myapplication.to_do_list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.to_do_list.Models.NotesModel;

import java.util.ArrayList;

public class RetrieveDataAdapter extends RecyclerView.Adapter<RetrieveDataAdapter.viewHolder> {
    Context context;
    ArrayList<NotesModel> notesModels;

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
            Intent intent = new Intent(context, SingleNoteActivity.class);
            intent.putExtra("title", model.getNoteTitle());
            intent.putExtra("content", model.getNoteContent());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notesModels.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titles_tv);
        }
    }
}
