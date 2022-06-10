package com.example.myapplication.databasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
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

    private AppCompatSpinner isSelected;
    private RecyclerView mList;
    private ArrayList<String> degreeList;
    private ArrayList<CourseDataModel> courseDataModels;
    private Button addingStudentData;
    private DatabaseHandler dbHandler;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetching_data);

        mList = findViewById(R.id.rv_mList);
        isSelected = findViewById(R.id.spin_field);
        addingStudentData = findViewById(R.id.adding_data);

        degreeList = new ArrayList<>();
        degreeList.add("All");
        degreeList.add("MCA");
        degreeList.add("MBA");

        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, degreeList);
        isSelected.setAdapter(adapter);

        dbHandler = new DatabaseHandler(FetchingDataActivity.this);


        isSelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object itemPosition = adapterView.getItemAtPosition(position);
                if (courseDataModels.get(position).getsDegreeType().equals("MCA")){
                    courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
                    mList.setAdapter(courseAdapter);
                }else if (courseDataModels.get(position).getsDegreeType().equals("MBA")){
                    courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
                    mList.setAdapter(courseAdapter);
                }else {
                    courseDataModels = dbHandler.fetchAllData();
                    courseAdapter = new CourseAdapter(courseDataModels, FetchingDataActivity.this);
                    mList.setAdapter(courseAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addingStudentData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goIntent = new Intent(FetchingDataActivity.this, DataBaseMainActivity.class);
                startActivity(goIntent);
            }
        });
    }
}