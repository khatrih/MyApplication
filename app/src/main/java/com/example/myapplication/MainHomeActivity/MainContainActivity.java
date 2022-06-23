package com.example.myapplication.MainHomeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.contentproviders.ContactActivity;
import com.example.myapplication.databasedemo.FetchingDataActivity;
import com.example.myapplication.layouts.HomeProductActivity;


public class MainContainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton btnRecyclerViewDemo;
    private AppCompatButton btnContentProviderDemo;
    private AppCompatButton btnSQLiteDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contain);

        btnRecyclerViewDemo = findViewById(R.id.recyclerDemo);
        btnContentProviderDemo = findViewById(R.id.contentProviderDemo);
        btnSQLiteDemo = findViewById(R.id.sqlite_demo);

        btnRecyclerViewDemo.setOnClickListener(this);
        btnContentProviderDemo.setOnClickListener(this);
        btnSQLiteDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.recyclerDemo) {
            Intent in = new Intent(MainContainActivity.this, HomeProductActivity.class);
            startActivity(in);
        } else if (v.getId() == R.id.contentProviderDemo) {
            Intent inContentProvider = new Intent(MainContainActivity.this, ContactActivity.class);
            startActivity(inContentProvider);
        }else {
            Intent intent = new Intent(MainContainActivity.this, FetchingDataActivity.class);
            startActivity(intent);
        }
    }
}