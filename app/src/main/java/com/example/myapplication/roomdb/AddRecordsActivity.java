package com.example.myapplication.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AddRecordsActivity extends AppCompatActivity {

    private int sId;
    private EditText etStudentName;
    private EditText etStudentEmail;
    private EditText etStudentPhoneNo;
    private EditText etStudentAddress;
    private Button btnSaveData;
    private ArrayList<String> mCourseList;
    private ArrayList<String> sGenderList;
    private AppCompatSpinner isSelectQualification;
    private Spinner isSelectGender;
    private String getQualificationText;
    private String getGenderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_records);

        etStudentName = findViewById(R.id.et_name);
        etStudentEmail = findViewById(R.id.et_email);
        etStudentAddress = findViewById(R.id.et_address);
        etStudentPhoneNo = findViewById(R.id.et_phone_number);
        btnSaveData = findViewById(R.id.btn_save);
        isSelectQualification = findViewById(R.id.spin_qualification);
        isSelectGender = findViewById(R.id.spin_gender);

        mCourseList = new ArrayList<>();
        mCourseList.add("select course");
        mCourseList.add("MCA");
        mCourseList.add("MBA");

        sGenderList = new ArrayList<>();
        sGenderList.add("select gender");
        sGenderList.add("Male");
        sGenderList.add("Female");

        ArrayAdapter<String> courseList = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mCourseList);
        isSelectQualification.setAdapter(courseList);

        ArrayAdapter<String> genderList = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sGenderList);
        isSelectGender.setAdapter(genderList);


        /*isSelectQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getQualificationText = isSelectQualification.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        /*isSelectGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getGenderText = isSelectGender.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        btnSaveData.setOnClickListener(v -> {
            String name = etStudentName.getText().toString();
            String email = etStudentEmail.getText().toString();
            String address = etStudentAddress.getText().toString();
            String phoneNo = etStudentPhoneNo.getText().toString();
            String qualified = isSelectQualification.getSelectedItem().toString();
            String gender = isSelectGender.getSelectedItem().toString();

//            String qualified = getQualificationText;
//            String gender = getGenderText;

            if (name.matches("")) {
                etStudentName.setError("enter valid name");
                etStudentName.requestFocus();
                return;
            }
            if (email.matches("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etStudentEmail.setError("please enter valid email");
                etStudentEmail.requestFocus();
                return;
            }
            if (address.matches("")) {
                etStudentAddress.setError("please fill address field");
                etStudentAddress.requestFocus();
                return;
            }
            if (phoneNo.length() < 10) {
                etStudentPhoneNo.setError("please enter 10 digits number");
                etStudentPhoneNo.requestFocus();
                return;
            }
            if (qualified.matches("select course")) {
                isSelectQualification.requestFocus();
                return;
            }
            if (gender.matches("select gender")) {
                isSelectGender.requestFocus();
                return;
            }
            StudentDataBase dataBase = StudentDataBase.getInstance(AddRecordsActivity.this);

            StudentDao studentDao = dataBase.getStudentDao();
            StudentsModel studentsModel = new StudentsModel(sId, name, email, address, phoneNo, qualified, gender);//
            studentDao.insertRecords(studentsModel);
            finish();

        });
    }
}