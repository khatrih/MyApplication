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

    private AppCompatSpinner isSelectedItem;
    private RecyclerView mStudentList;
    private ArrayList<String> courseList;
    private ArrayList<CourseDataModel> courseDataModels;
    private Button addStudentData;
    private DatabaseHandler dbHandler;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetching_data);

        mStudentList = findViewById(R.id.rv_mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mStudentList.setLayoutManager(layoutManager);
        isSelectedItem = findViewById(R.id.spin_field);
        addStudentData = findViewById(R.id.adding_data);

        courseList = new ArrayList<>();
        courseList.add("All");
        courseList.add("MCA");
        courseList.add("MBA");

        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, courseList);
        isSelectedItem.setAdapter(adapter);

        dbHandler = new DatabaseHandler(FetchingDataActivity.this);

        isSelectedItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String degreeType = (String) adapterView.getItemAtPosition(position);

                courseDataModels = dbHandler.fetchData(degreeType);
                courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
                mStudentList.setAdapter(courseAdapter);

                /*if (courseDataModels.get(position).getsDegreeType().equals("MCA")) {
                    courseDataModels = dbHandler.fetchMCAData();
                    courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
                    mStudentList.setAdapter(courseAdapter);
                } else if (courseDataModels.get(position).getsDegreeType().equals("MBA")) {
                    courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
                    mStudentList.setAdapter(courseAdapter);
                } else {
                    courseDataModels = dbHandler.fetchAllData();
                    courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
                    mStudentList.setAdapter(courseAdapter);
                }

                courseDataModels = dbHandler.fetchMCAData();
                courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
                mStudentList.setAdapter(courseAdapter);

                if (itemPosition.equals("MCA") == courseDataModels.get(position).getsDegreeType().equals("MCA")) {
                    courseDataModels = dbHandler.fetchAllData();
                    courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
                    mStudentList.setAdapter(courseAdapter);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addStudentData.setOnClickListener(v -> {
            Intent goIntent = new Intent(FetchingDataActivity.this, DataBaseMainActivity.class);
            startActivity(goIntent);
        });
    }
}