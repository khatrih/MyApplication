package com.example.myapplication.MainHomeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.apidemo.RTFHomeActivity;
import com.example.myapplication.canvasdemo.PaintHomeActivity;
import com.example.myapplication.contentproviders.ContactActivity;
import com.example.myapplication.databasedemo.FetchingDataActivity;
import com.example.myapplication.layouts.HomeProductActivity;
import com.example.myapplication.newcontentprovider.NewContactsActivity;
import com.example.myapplication.roomdb.RoomDBMainActivity;
import com.example.myapplication.to_do_list.ToDoListHomeActivity;
import com.example.myapplication.to_do_list.ToDoListLoginActivity;

public class MainContainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton btnRecyclerViewDemo;
    private AppCompatButton btnApisDemo;
    private AppCompatButton btnSQLiteDemo;
    private AppCompatButton btnRoomDBDemo;
    private AppCompatButton btnNewCPDemo;
    private AppCompatButton btnToDoDemo;
    private AppCompatButton btnCanvasDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contain);

        btnRecyclerViewDemo = findViewById(R.id.recyclerDemo);
        btnApisDemo = findViewById(R.id.ApisDemo);
        btnSQLiteDemo = findViewById(R.id.sqlite_demo);
        btnRoomDBDemo = findViewById(R.id.room_db);
        btnNewCPDemo = findViewById(R.id.new_cp);
        btnToDoDemo = findViewById(R.id.to_do_list_demo);
        btnCanvasDemo = findViewById(R.id.canvas_demo);

        btnRecyclerViewDemo.setOnClickListener(this);
        btnApisDemo.setOnClickListener(this);
        btnSQLiteDemo.setOnClickListener(this);
        btnRoomDBDemo.setOnClickListener(this);
        btnNewCPDemo.setOnClickListener(this);
        btnToDoDemo.setOnClickListener(this);
        btnCanvasDemo.setOnClickListener(this);

        /*Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            Intent intentToDoList = new Intent(MainContainActivity.this, ToDoListHomeActivity.class);
            startActivity(intentToDoList);
        }*/
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.recyclerDemo) {
            startActivity(new Intent(MainContainActivity.this, HomeProductActivity.class));
        } else if (v.getId() == R.id.ApisDemo) {
            startActivity(new Intent(MainContainActivity.this, RTFHomeActivity.class));
        } else if (v.getId() == R.id.sqlite_demo) {
            startActivity(new Intent(MainContainActivity.this, FetchingDataActivity.class));
        } else if (v.getId() == R.id.room_db) {
            startActivity(new Intent(MainContainActivity.this, RoomDBMainActivity.class));
        } else if (v.getId() == R.id.new_cp) {
            startActivity(new Intent(MainContainActivity.this, NewContactsActivity.class));
        } else if (v.getId() == R.id.to_do_list_demo) {
            startActivity(new Intent(MainContainActivity.this, ToDoListLoginActivity.class));
        } else if (v.getId() == R.id.canvas_demo) {
            startActivity(new Intent(MainContainActivity.this, PaintHomeActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}