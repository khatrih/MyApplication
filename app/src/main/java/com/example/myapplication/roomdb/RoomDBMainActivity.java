package com.example.myapplication.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class RoomDBMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_dbmain);

        RecyclerView rvStudentData = findViewById(R.id.rv_student_data);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rvStudentData.setLayoutManager(lm);
        Button btnAddRecords = findViewById(R.id.btn_add_records);

        btnAddRecords.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent in = new Intent(RoomDBMainActivity.this, AddRecordsActivity.class);
        startActivity(in);
    }
}