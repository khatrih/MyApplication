package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewDemo extends AppCompatActivity {

    AppCompatButton name, linearViewBtn, gridViewBtn, staggeredViewBtn;
    modelView model = new modelView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);

        linearViewBtn = findViewById(R.id.linearViewBtn);
        gridViewBtn = findViewById(R.id.gridViewBtn);
        staggeredViewBtn = findViewById(R.id.staggeredViewBtn);

        getData();

        name = findViewById(R.id.name);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RecyclerViewDemo.this, BaseActivity.class);
                startActivity(in);
            }
        });

        linearViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RecyclerViewDemo.this, getDetailsActivity.class);
                in.putExtra("type", "linear");
                startActivity(in);
            }
        });

        gridViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RecyclerViewDemo.this, getDetailsActivity.class);
                in.putExtra("type", "grid");
                startActivity(in);
            }
        });

        staggeredViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RecyclerViewDemo.this, getDetailsActivity.class);
                in.putExtra("type", "staggered");
                startActivity(in);
            }
        });

    }

    public void getData() {
        model.setName("Android");
    }


}