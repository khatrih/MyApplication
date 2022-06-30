package com.example.myapplication.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class UpdateStudentRDBActivity extends AppCompatActivity {

    private EditText etStudentName;
    private EditText etStudentEmail;
    private EditText etStudentAddress;
    private EditText etStudentMobileNo;
    private EditText etStudentQualification;
    private Button btnUpdate;
    private int sId;
    private static final String STUDENT_ID = "id";
    private static final String STUDENT_NAME = "name";
    private static final String STUDENT_EMAIL = "email";
    private static final String STUDENT_ADDRESS = "address";
    private static final String STUDENT_PHONE_NO = "mobile_no";
    private static final String STUDENT_QUALIFICATION = "qualification";

    /*String name;
    String email;
    String address;
    String mobileNo;
    String qualification;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student_rdbactivity);

        etStudentName = findViewById(R.id.et_update_name);
        etStudentEmail = findViewById(R.id.et_update_email);
        etStudentAddress = findViewById(R.id.et_update_address);
        etStudentMobileNo = findViewById(R.id.et_update_phone_number);
        etStudentQualification = findViewById(R.id.et_update_qualification);
        btnUpdate = findViewById(R.id.btn_update);

        sId = Integer.parseInt(getIntent().getStringExtra(STUDENT_ID));
        etStudentName.setText(getIntent().getStringExtra(STUDENT_NAME));
        etStudentEmail.setText(getIntent().getStringExtra(STUDENT_EMAIL));
        etStudentAddress.setText(getIntent().getStringExtra(STUDENT_ADDRESS));
        etStudentMobileNo.setText(getIntent().getStringExtra(STUDENT_PHONE_NO));
        etStudentQualification.setText(getIntent().getStringExtra(STUDENT_QUALIFICATION));

        btnUpdate.setOnClickListener(v -> {

            /*
            if (name.matches("")) {
                etStudentName.setError("please fill name field");
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
            if (mobileNo.matches("") || !Patterns.PHONE.matcher(mobileNo).matches()) {
                etStudentMobileNo.setError("please enter valid number");
                etStudentMobileNo.requestFocus();
                return;
            }
            if (qualification.matches("")) {
                etStudentQualification.setError("please fill Qualification field");
                etStudentQualification.requestFocus();
                return;
            }*/

            /*String name = etStudentName.getText().toString();
            String email = etStudentEmail.getText().toString();
            String address = etStudentAddress.getText().toString();
            String mobileNo = etStudentMobileNo.getText().toString();
            String qualification = etStudentQualification.getText().toString();

            StudentDataBase dataBase = Room.databaseBuilder(UpdateStudentRDBActivity.this,
                    StudentDataBase.class, "student_database").allowMainThreadQueries().build();
            StudentDao studentDao = dataBase.getStudentDao();

            studentDao.updateStudentData(sId, name, email, address, mobileNo, qualification);

            Intent intent = new Intent(UpdateStudentRDBActivity.this, RoomDBMainActivity.class);
            startActivity(intent);
            finish();*/

            new bgThread().start();
            Intent intent = new Intent(UpdateStudentRDBActivity.this, RoomDBMainActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(UpdateStudentRDBActivity.this)
                .setTitle("Back")
                .setMessage("Are you sure you don't want to update?")
                .setCancelable(false)
                .setPositiveButton("yes", (dialog, which) -> finish())
                .setNegativeButton("no", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    class bgThread extends Thread {
        @Override
        public void run() {
            super.run();

            String name = etStudentName.getText().toString();
            String email = etStudentEmail.getText().toString();
            String address = etStudentAddress.getText().toString();
            String mobileNo = etStudentMobileNo.getText().toString();
            String qualification = etStudentQualification.getText().toString();

            StudentDataBase dataBase = StudentDataBase.getInstance(UpdateStudentRDBActivity.this);

            StudentDao dao = dataBase.getStudentDao();

            dao.updateStudentData(sId, name, email, address, mobileNo, qualification);
            finish();
        }
    }
}