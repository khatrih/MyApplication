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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.to_do_list.Models.NotesModel;
import com.example.myapplication.to_do_list.interfaces.NotesInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToDoListHomeActivity extends AppCompatActivity implements View.OnClickListener, NotesInterface, DeleteItemBottomSheet.OnRemoveListener {
    private RecyclerView rvNotes;
    private TextView tvNoData;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private ArrayList<NotesModel> notesModelList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private NotesModel model;
    private FloatingActionButton floatingActionButton;
    private String userId;
    private static final String TAG = "TAG";
    private RetrieveDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_home);

        Toolbar toolbar = findViewById(R.id.to_do_list_toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = findViewById(R.id.floatingAction);
        tvNoData = findViewById(R.id.tv_no_data);
        rvNotes = findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        userId = mAuth.getCurrentUser().getUid();


        progressDialog = new ProgressDialog(ToDoListHomeActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DeleteItemBottomSheet.setRemoveListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        floatingActionButton.setOnClickListener(this);
        reference.child("users").child(userId).child("Notes")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressDialog.dismiss();
                        notesModelList.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                model = dataSnapshot.getValue(NotesModel.class);
                                assert model != null;
                                model.setNoteId(dataSnapshot.getKey());
                                notesModelList.add(model);
                            }
                        }
                        setAdapter(notesModelList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("TAG", "onCancelled: ");
                    }
                });
    }

    void setAdapter(ArrayList<NotesModel> noteList) {
        adapter = new RetrieveDataAdapter(ToDoListHomeActivity.this,
                noteList, ToDoListHomeActivity.this);
        rvNotes.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    @Override
    public void deleteNote(int position) {
        Bundle bundle = new Bundle();
        DeleteItemBottomSheet deleteItemBottomSheet = new DeleteItemBottomSheet();
        bundle.putString("ID", notesModelList.get(position).getNoteId());
        bundle.putString("title", notesModelList.get(position).getNoteTitle());
        bundle.putInt("position", position);
        deleteItemBottomSheet.setArguments(bundle);
        deleteItemBottomSheet.show(getSupportFragmentManager(), "ModelBottomSheet");//deleteItemBottomSheet.getTag()

    }

    @Override
    public void onRemove(int position) {
        notesModelList.remove(position);
    }


}
//                    if (notesModel.isEmpty()) {
//                        tvNoData.setText(R.string.no_data);
//                    } else {
//                        tvNoData.setVisibility(View.GONE);
//                        rvNotes.setVisibility(View.VISIBLE);
//                        rvNotes.setAdapter(adapter);
//                    }