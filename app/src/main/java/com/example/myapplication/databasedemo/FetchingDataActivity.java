package com.example.myapplication.databasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.myapplication.R;

import java.util.ArrayList;

public class FetchingDataActivity extends AppCompatActivity {

    private RecyclerView rvStudentList;
    private DatabaseHandler dbHandler;
    private AppCompatSpinner filterCourseSpinner;
    ArrayAdapter<String> adapter;
    ArrayList<CourseDataModel> courseDataModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetching_data);

        rvStudentList = findViewById(R.id.rv_mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvStudentList.setLayoutManager(layoutManager);
        filterCourseSpinner = findViewById(R.id.spin_field);
        Button btnAddStudentData = findViewById(R.id.adding_data);

        ArrayList<String> mCourseList = new ArrayList<>();
        mCourseList.add("All");
        mCourseList.add("MCA");
        mCourseList.add("MBA");

        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mCourseList);
        filterCourseSpinner.setAdapter(adapter);

        dbHandler = new DatabaseHandler(FetchingDataActivity.this);

        btnAddStudentData.setOnClickListener(v -> {
            Intent intent = new Intent(FetchingDataActivity.this, AddUpdateActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String degreeType = (String) filterCourseSpinner.getSelectedItem();
        courseDataModels = dbHandler.fetchData(degreeType);
        CourseAdapter courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
        rvStudentList.setAdapter(courseAdapter);
        adapter.notifyDataSetChanged();
        studentData();
    }

    private void studentData() {
        filterCourseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String degreeType = (String) adapterView.getItemAtPosition(position);
                courseDataModels = dbHandler.fetchData(degreeType);
                CourseAdapter courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
                rvStudentList.setAdapter(courseAdapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}