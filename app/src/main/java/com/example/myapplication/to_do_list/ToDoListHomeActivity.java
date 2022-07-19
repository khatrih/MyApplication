package com.example.myapplication.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.MainHomeActivity.MainContainActivity;
import com.example.myapplication.R;
import com.example.myapplication.to_do_list.Models.NotesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ToDoListHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvNotes;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private ArrayList<NotesModel> notesModel = new ArrayList<>();
    private ProgressDialog dialog;
    private RetrieveDataAdapter adapter;
    private NotesModel model;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_home);

        Toolbar toolbar = findViewById(R.id.to_do_list_toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = findViewById(R.id.floatingAction);
        rvNotes = findViewById(R.id.rv_notes);
        adapter = new RetrieveDataAdapter(ToDoListHomeActivity.this, notesModel);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        dialog = new ProgressDialog(ToDoListHomeActivity.this);
        dialog.setMessage("Loading...");
        dialog.show();
        dialog.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        floatingActionButton.setOnClickListener(this);
        String userId = mAuth.getCurrentUser().getUid();
        reference.child("users").child(userId).child("Notes").
                get().addOnSuccessListener(dataSnapshot -> {
                    dialog.dismiss();
                    notesModel.clear();
                    for (DataSnapshot ss : dataSnapshot.getChildren()) {
                        model = ss.getValue(NotesModel.class);
                        assert model != null;
                        model.setNoteId(ss.getKey());
                        notesModel.add(model);
                    }
                    rvNotes.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ToDoListHomeActivity.this, "network" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("yes", ((dialogInterface, i) -> {
                        mAuth.signOut();
                        startActivity(new Intent(ToDoListHomeActivity.this, ToDoListLoginActivity.class));
                    }))
                    .setNegativeButton("no", ((dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    }))
                    .create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floatingAction) {
            startActivity(new Intent(ToDoListHomeActivity.this, ToDoListAddNotesActivity.class));
            floatingActionButton.setOnClickListener(null);
        }
    }
}