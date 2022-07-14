package com.example.myapplication.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.to_do_list.Models.NotesModel;
import com.example.myapplication.to_do_list.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ToDoListAddNotesActivity extends AppCompatActivity {

    private EditText etNotesTitle;
    private EditText etNotesBody;
    private Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference reference;
    NotesModel notesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_add_notes);

        toolbar = findViewById(R.id.to_do_list_mToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        etNotesTitle = findViewById(R.id.title_notes);
        etNotesBody = findViewById(R.id.body_notes);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();

        reference = database.getReference().child("users").child(userId).child("Notes")
                .push();
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.save_notes) {
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
                            reference.setValue(notesModel)
                                    .addOnSuccessListener(unused -> {
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