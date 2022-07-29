package com.example.myapplication.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.Objects;

public class ToDoListAddNotesActivity extends AppCompatActivity {

    private EditText etNotesTitle;
    private EditText etNotesBody;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private boolean isUpdate = false;
    private static final String TAG = "TAG";
    String userId;
    NotesModel notesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_add_notes);

        Toolbar toolbar = findViewById(R.id.to_do_list_mToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etNotesTitle = findViewById(R.id.title_notes);
        etNotesBody = findViewById(R.id.body_notes);

        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        database = FirebaseDatabase.getInstance();

        isUpdate = getIntent().getBooleanExtra("Flag", false);
        notesModel = getIntent().getParcelableExtra("NotesModel");
        if (isUpdate) {
            etNotesTitle.setText(notesModel.getNoteTitle());
            etNotesBody.setText(notesModel.getNoteContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.save_notes:
                String title = etNotesTitle.getText().toString();
                String body = etNotesBody.getText().toString();
                if (isUpdate) {
                    reference = database.getReference().child("users").child(userId).child("Notes").child(notesModel.getNoteId());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            snapshot.getRef().setValue(new NotesModel(title, body));
                            reference.setValue(new NotesModel(title, body));
                            Log.d(TAG, "onDataChange: update " + snapshot.getKey());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled: " + error.getMessage());
                        }
                    });
                } else {
                    if (etNotesTitle.getText().toString().isEmpty() || etNotesBody.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Please add title and content here", Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        reference = database.getReference().child("users").child(userId).child("Notes").push();
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                reference.setValue(new NotesModel(title, body));
                                Log.d(TAG, "onDataChange: add " + snapshot.getKey());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d(TAG, "onCancelled: " + error.getMessage());
                            }
                        });
                    }
                }
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}