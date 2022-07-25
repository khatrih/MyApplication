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
import com.example.myapplication.to_do_list.interfaces.NotesInterface;

import java.util.ArrayList;

public class RetrieveDataAdapter extends RecyclerView.Adapter<RetrieveDataAdapter.viewHolder> {
    private Context context;
    private ArrayList<NotesModel> notesModels;
    NotesInterface notesInterface;

    public RetrieveDataAdapter(Context context, ArrayList<NotesModel> notesModels, NotesInterface notesInterface) {
        this.context = context;
        this.notesModels = notesModels;
        this.notesInterface = notesInterface;
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
            in.putExtra("Flag", true);
            in.putExtra("NotesModel", model);
            context.startActivity(in);
        });
    }

    @Override
    public int getItemCount() {
        return notesModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titles_tv);

            itemView.setOnLongClickListener(v -> {
                notesInterface.deleteNote(getAdapterPosition());
                return true;
            });
        }
    }
}
