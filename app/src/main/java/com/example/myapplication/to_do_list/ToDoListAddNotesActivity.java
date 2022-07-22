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

public class ToDoListAddNotesActivity extends AppCompatActivity {

    private EditText etNotesTitle;
    private EditText etNotesBody;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private NotesModel notesModel;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_add_notes);

        Toolbar toolbar = findViewById(R.id.to_do_list_mToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        etNotesTitle = findViewById(R.id.title_notes);
        etNotesBody = findViewById(R.id.body_notes);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();

        isUpdate = getIntent().getBooleanExtra("Flag", false);
        String Title = getIntent().getStringExtra("title");
        String Content = getIntent().getStringExtra("content");
        String Id = getIntent().getStringExtra("noteId");

        etNotesTitle.setText(Title);
        etNotesBody.setText(Content);
//        reference = database.getReference().child("Notes").push();
        reference = database.getReference().child("users").child(userId).child("Notes").push();
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.save_notes) {
                if (isUpdate) {
                    String updatedTitle = etNotesTitle.getText().toString();
                    String updatedContent = etNotesBody.getText().toString();
                    notesModel = new NotesModel(updatedTitle, updatedContent);
                    reference = database.getReference().child("users").child(userId).child("Notes").child(Id);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            notesModel = new NotesModel(updatedTitle, updatedContent);
                            reference.setValue(notesModel).addOnSuccessListener(unused -> {
                                reference.setValue(notesModel);
                                finish();
                            }).addOnFailureListener(e -> Toast.makeText(ToDoListAddNotesActivity.this, R.string.update_note, Toast.LENGTH_SHORT).show());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else {
                    if (etNotesTitle.getText().toString().isEmpty() || etNotesBody.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Please add title and content here", Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        String title = etNotesTitle.getText().toString();
                        String body = etNotesBody.getText().toString();
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                notesModel = new NotesModel(title, body);
                                reference.setValue(notesModel).addOnSuccessListener(unused -> {
                                    reference.setValue(notesModel);
                                    finish();
                                }).addOnFailureListener(e -> Toast.makeText(ToDoListAddNotesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
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