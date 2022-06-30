package com.example.myapplication.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class RoomDBMainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvStudentData;
    private TextView tvNoData;
    private StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_dbmain);

        rvStudentData = findViewById(R.id.rv_student_data);
        tvNoData = findViewById(R.id.tv_no_data);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rvStudentData.setLayoutManager(lm);
        Button btnAddRecords = findViewById(R.id.btn_add_records);

        StudentDataBase dataBase = StudentDataBase.getInstance(RoomDBMainActivity.this);

        studentDao = dataBase.getStudentDao();

        btnAddRecords.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<StudentsModel> models = studentDao.getAllStudentData();
        if (models.isEmpty()) {
            tvNoData.setText(R.string.no_data);
        } else {
            tvNoData.setVisibility(View.GONE);
            rvStudentData.setVisibility(View.VISIBLE);
            RoomDBAdapter adapter = new RoomDBAdapter(models, RoomDBMainActivity.this);
            rvStudentData.setAdapter(adapter);
        }
    }

    /*class fetchDataFromBackGround extends Thread {
        @Override
        public void run() {
            super.run();
            StudentDataBase dataBase = Room.databaseBuilder(RoomDBMainActivity.this,
                    StudentDataBase.class, "student_database").allowMainThreadQueries().build();

            StudentDao studentDao = dataBase.getStudentDao();
            List<StudentsModel> models = studentDao.getAllStudentData();
            RoomDBAdapter adapter = new RoomDBAdapter(models, RoomDBMainActivity.this);
            rvStudentData.setAdapter(adapter);
            finish();
        }
    }*/

    @Override
    public void onClick(View v) {
        Intent in = new Intent(RoomDBMainActivity.this, AddRecordsActivity.class);
        startActivity(in);
    }

}