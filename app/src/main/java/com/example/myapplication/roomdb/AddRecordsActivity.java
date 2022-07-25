package com.example.myapplication.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AddRecordsActivity extends AppCompatActivity {

    private EditText etStudentName;
    private EditText etStudentEmail;
    private EditText etStudentPhoneNo;
    private EditText etStudentAddress;
    private AppCompatSpinner studentQualificationSpinner;
    private Spinner studentGenderSpinner;
    private boolean isUpdate = false;
    private StudentsModel studentsModel;
    private StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_records);

        etStudentName = findViewById(R.id.et_name);
        etStudentEmail = findViewById(R.id.et_email);
        etStudentAddress = findViewById(R.id.et_address);
        etStudentPhoneNo = findViewById(R.id.et_phone_number);
        Button btnSaveData = findViewById(R.id.btn_save);
        studentQualificationSpinner = findViewById(R.id.spin_qualification);
        studentGenderSpinner = findViewById(R.id.spin_gender);

        StudentDataBase dataBase = StudentDataBase.getInstance(AddRecordsActivity.this);
        studentDao = dataBase.getStudentDao();

        ArrayList<String> mCourseList = new ArrayList<>();
        mCourseList.add("select course");
        mCourseList.add("MCA");
        mCourseList.add("MBA");

        ArrayList<String> sGenderList = new ArrayList<>();
        sGenderList.add("select gender");
        sGenderList.add("Male");
        sGenderList.add("Female");

        ArrayAdapter<String> courseList = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mCourseList);
        studentQualificationSpinner.setAdapter(courseList);

        ArrayAdapter<String> genderList = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sGenderList);
        studentGenderSpinner.setAdapter(genderList);

        isUpdate = getIntent().getBooleanExtra("isUpdate", false);

        if (isUpdate) {
            btnSaveData.setText(R.string.update_btn);
            btnSaveData.setTextColor(Color.WHITE);
            studentsModel = (StudentsModel) getIntent().getSerializableExtra("studentModel");
            etStudentName.setText(studentsModel.getsName());
            etStudentEmail.setText(studentsModel.getsEmail());
            etStudentAddress.setText(studentsModel.getsAddress());
            etStudentPhoneNo.setText(studentsModel.getsMobileNo());
            studentQualificationSpinner.setSelection(courseList.getPosition(studentsModel.sQualification));
            studentGenderSpinner.setSelection(genderList.getPosition(studentsModel.getsGender()));
        }
        btnSaveData.setOnClickListener(v -> {

            String name = etStudentName.getText().toString();
            String email = etStudentEmail.getText().toString();
            String address = etStudentAddress.getText().toString();
            String phoneNo = etStudentPhoneNo.getText().toString();
            String qualified = studentQualificationSpinner.getSelectedItem().toString();
            String gender = studentGenderSpinner.getSelectedItem().toString();

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
                studentQualificationSpinner.requestFocus();
                return;
            }
            if (gender.matches("select gender")) {
                studentGenderSpinner.requestFocus();
                return;
            }

            if (isUpdate) {
                btnSaveData.setText(R.string.update_btn);
                studentDao.updateStudentData(studentsModel.getsId(), name, email, address, phoneNo, qualified, gender);
            } else {
                studentsModel = new StudentsModel(name, email, address, phoneNo, qualified, gender);//
                studentDao.insertRecords(studentsModel);
            }
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddRecordsActivity.this);
        alertDialog.setTitle("Back");
        if (isUpdate) {
            alertDialog.setMessage("Are you sure you don't want to update?");
        } else {
            alertDialog.setMessage("Are you sure you don't want to save");
        }
        alertDialog.setCancelable(false)
                .setPositiveButton("yes", (dialog, which) -> finish())
                .setNegativeButton("no", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}