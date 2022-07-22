package com.example.myapplication.MainHomeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.contentproviders.ContactActivity;
import com.example.myapplication.databasedemo.FetchingDataActivity;
import com.example.myapplication.layouts.HomeProductActivity;
import com.example.myapplication.newcontentprovider.NewContactsActivity;
import com.example.myapplication.roomdb.RoomDBMainActivity;
import com.example.myapplication.to_do_list.ToDoListHomeActivity;
import com.example.myapplication.to_do_list.ToDoListLoginActivity;

public class MainContainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton btnRecyclerViewDemo;
    private AppCompatButton btnContentProviderDemo;
    private AppCompatButton btnSQLiteDemo;
    private AppCompatButton btnRoomDBDemo;
    private AppCompatButton btnNewCPDemo;
    private AppCompatButton btnToDoDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contain);

        btnRecyclerViewDemo = findViewById(R.id.recyclerDemo);
        btnContentProviderDemo = findViewById(R.id.contentProviderDemo);
        btnSQLiteDemo = findViewById(R.id.sqlite_demo);
        btnRoomDBDemo = findViewById(R.id.room_db);
        btnNewCPDemo = findViewById(R.id.new_cp);
        btnToDoDemo = findViewById(R.id.to_do_list_demo);

        btnRecyclerViewDemo.setOnClickListener(this);
        btnContentProviderDemo.setOnClickListener(this);
        btnSQLiteDemo.setOnClickListener(this);
        btnRoomDBDemo.setOnClickListener(this);
        btnNewCPDemo.setOnClickListener(this);
        btnToDoDemo.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Intent intentToDoList = new Intent(MainContainActivity.this, ToDoListHomeActivity.class);
            startActivity(intentToDoList);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.recyclerDemo) {
            Intent in = new Intent(MainContainActivity.this, HomeProductActivity.class);
            startActivity(in);
        } else if (v.getId() == R.id.contentProviderDemo) {
            Intent inContentProvider = new Intent(MainContainActivity.this, ContactActivity.class);
            startActivity(inContentProvider);
        } else if (v.getId() == R.id.sqlite_demo) {
            Intent intent = new Intent(MainContainActivity.this, FetchingDataActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.room_db) {
            Intent intentRoomDB = new Intent(MainContainActivity.this, RoomDBMainActivity.class);
            startActivity(intentRoomDB);
        } else if (v.getId() == R.id.new_cp) {
            Intent intentCP = new Intent(MainContainActivity.this, NewContactsActivity.class);
            startActivity(intentCP);
        } else {
            Intent intentToDoList = new Intent(MainContainActivity.this, ToDoListLoginActivity.class);
            startActivity(intentToDoList);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}