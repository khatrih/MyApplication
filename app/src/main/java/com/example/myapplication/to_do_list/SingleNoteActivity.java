package com.example.myapplication.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;

public class SingleNoteActivity extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvContent;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_note);

        mToolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        tvTitle = findViewById(R.id.tv_titles);
        tvContent = findViewById(R.id.tv_content);

        tvTitle.setText(getIntent().getStringExtra("title"));
        tvContent.setText(getIntent().getStringExtra("content"));
    }
}