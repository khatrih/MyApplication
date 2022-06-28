package com.example.myapplication.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import java.util.List;

public class RoomDBMainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvStudentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_dbmain);

        rvStudentData = findViewById(R.id.rv_student_data);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rvStudentData.setLayoutManager(lm);
        Button btnAddRecords = findViewById(R.id.btn_add_records);

        /*StudentDataBase dataBase = Room.databaseBuilder(RoomDBMainActivity.this,
                StudentDataBase.class, "student_database").allowMainThreadQueries().build();

        StudentDao studentDao = dataBase.getStudentDao();
        List<StudentsModel> models = studentDao.getAllStudentData();
        RoomDBAdapter adapter = new RoomDBAdapter(models, RoomDBMainActivity.this);
        rvStudentData.setAdapter(adapter);*/

        new fetchDataFromBackGround().start();

        btnAddRecords.setOnClickListener(this);
    }

    class fetchDataFromBackGround extends Thread {
        @Override
        public void run() {
            super.run();
            StudentDataBase dataBase = Room.databaseBuilder(RoomDBMainActivity.this,
                    StudentDataBase.class, "student_database").build();

            StudentDao studentDao = dataBase.getStudentDao();
            List<StudentsModel> models = studentDao.getAllStudentData();
            RoomDBAdapter adapter = new RoomDBAdapter(models, RoomDBMainActivity.this);
            rvStudentData.setAdapter(adapter);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        Intent in = new Intent(RoomDBMainActivity.this, AddRecordsActivity.class);
        startActivity(in);
    }
}