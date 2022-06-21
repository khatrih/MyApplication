package com.example.myapplication.MainHomeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.contentproviders.ContactActivity;
import com.example.myapplication.layouts.HomeProductActivity;

public class MainContainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton btnRecyclerViewDemo;
    private AppCompatButton btnContentProviderDemo;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contain);

        btnRecyclerViewDemo = findViewById(R.id.recyclerDemo);
        btnContentProviderDemo = findViewById(R.id.contentProviderDemo);

        btnRecyclerViewDemo.setOnClickListener(this);
        btnContentProviderDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.recyclerDemo){
            Intent in = new Intent(MainContainActivity.this, HomeProductActivity.class);
            startActivity(in);
        }else {
            Intent in = new Intent(MainContainActivity.this, ContactActivity.class);
            startActivity(in);
        }
    }
}