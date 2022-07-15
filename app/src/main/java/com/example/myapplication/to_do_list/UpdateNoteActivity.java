package com.example.myapplication.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.to_do_list.Models.NotesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateNoteActivity extends AppCompatActivity {
    private EditText etUpdateNotesTitle;
    private EditText etUpdateNotesBody;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private NotesModel notesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        Toolbar toolbar = findViewById(R.id.to_do_list_myToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        etUpdateNotesTitle = findViewById(R.id.update_title_notes);
        etUpdateNotesBody = findViewById(R.id.update_body_notes);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();

        String Title = getIntent().getStringExtra("title");
        String Content = getIntent().getStringExtra("content");
        String Id = getIntent().getStringExtra("noteId");

        etUpdateNotesTitle.setText(Title);
        etUpdateNotesBody.setText(Content);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.save_notes) {

                String updatedTitle = etUpdateNotesTitle.getText().toString();
                String updatedContent = etUpdateNotesBody.getText().toString();
                notesModel = new NotesModel(updatedTitle, updatedContent);
                reference = database.getReference().child("users").child(userId).child("Notes")
                        .child(Id);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        notesModel = new NotesModel(updatedTitle, updatedContent);
                        reference.setValue(notesModel).addOnSuccessListener(unused -> {
                            reference.setValue(notesModel);
                            finish();
                        }).addOnFailureListener(e -> Toast.makeText(UpdateNoteActivity.this, R.string.update_note, Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_save_menu, menu);
        return true;
    }
}


//.setValue(notesModel1).addOnSuccessListener(unused -> {})
//        .addOnFailureListener(e ->
//        Toast.makeText(ToDoListAddNotesActivity.this,e.getMessage(), Toast.LENGTH_LONG).show());