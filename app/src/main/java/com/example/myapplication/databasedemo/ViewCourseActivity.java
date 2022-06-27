package com.example.myapplication.databasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewCourseActivity extends AppCompatActivity {

    private RecyclerView mCourseList;
    private ArrayList<CourseDataModel> courseDataModelArrayList;
    private DatabaseHandler dbhandler;
    private CourseAdapter mCourseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        mCourseList = findViewById(R.id.course_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mCourseList.setLayoutManager(layoutManager);

        courseDataModelArrayList = new ArrayList<>();

        dbhandler = new DatabaseHandler(ViewCourseActivity.this);

        courseDataModelArrayList = dbhandler.readCourse();

        mCourseAdapter = new CourseAdapter(courseDataModelArrayList, ViewCourseActivity.this);
        mCourseList.setAdapter(mCourseAdapter);
    }
}