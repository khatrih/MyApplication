package com.example.myapplication.databasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class DataBaseMainActivity extends AppCompatActivity {

    private EditText studentName;
    private EditText studentEmail;
    private EditText studentNumber;
    private EditText studentAddress;
    private EditText studentQualification;
    private AppCompatButton addCourseData;
    private AppCompatButton readCourseData;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_main);
        dbHandler = new DatabaseHandler(DataBaseMainActivity.this);

        studentName = findViewById(R.id.student_name);
        studentEmail = findViewById(R.id.student_email);
        studentNumber = findViewById(R.id.student_mobile_number);
        studentAddress = findViewById(R.id.student_address);
        studentQualification = findViewById(R.id.student_qualification);
        addCourseData = findViewById(R.id.add_data);
        readCourseData = findViewById(R.id.read_data);


        addCourseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = studentName.getText().toString().trim();
                String sEmail = studentEmail.getText().toString().trim();
                String sPhoneNumber = studentNumber.getText().toString().trim();
                String sAddress = studentAddress.getText().toString().trim();
                String sQualification = studentQualification.getText().toString().trim();

                if (sName.isEmpty() && sEmail.isEmpty() && sPhoneNumber.isEmpty()
                        && sAddress.isEmpty() && sQualification.isEmpty()) {
                    Toast.makeText(DataBaseMainActivity.this, "please fill the details",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHandler.addNewCourse(sName, sEmail, sAddress, sPhoneNumber, sQualification);
                Toast.makeText(DataBaseMainActivity.this, "Record Successfully added", Toast.LENGTH_SHORT).show();
                studentName.setText("");
                studentEmail.setText("");
                studentNumber.setText("");
                studentAddress.setText("");
                studentQualification.setText("");
            }
        });

        readCourseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataBaseMainActivity.this, ViewCourseActivity.class));
            }
        });
    }
}