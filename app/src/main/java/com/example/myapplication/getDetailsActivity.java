package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

public class getDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerViewItem;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_details);

        recyclerViewItem = findViewById(R.id.recyclerItemView);

        modelView[] model = new modelView[]{
                new modelView("apple", "fruits", R.drawable.thumbnail),
                new modelView("banana", "fruits", R.drawable.thumbnail),
                new modelView("beetroot", "fruits", R.drawable.thumbnail),
                new modelView("watermelon", "fruits", R.drawable.thumbnail),
                new modelView("caret", "fruits", R.drawable.thumbnail),
        };

        AdapterList adapterList = new AdapterList(model, getDetailsActivity.this);
        recyclerViewItem.setAdapter(adapterList);
        recyclerViewItem.setElevation(2);

        if (getIntent().getStringExtra("type").equals("grid")) {
            gridLayoutManager = new GridLayoutManager(this, 2);
            recyclerViewItem.setLayoutManager(gridLayoutManager);
        } else if (getIntent().getStringExtra("type").equals("staggered")) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            recyclerViewItem.setLayoutManager(staggeredGridLayoutManager);
        } else {
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerViewItem.setLayoutManager(linearLayoutManager);
        }


    }
}