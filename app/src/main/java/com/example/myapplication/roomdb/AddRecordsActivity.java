package com.example.myapplication.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AddRecordsActivity extends AppCompatActivity {

    private EditText etStudentName;
    private EditText etStudentEmail;
    private EditText etStudentPhoneNo;
    private EditText etStudentAddress;
    private EditText etStudentQualification;
    private Button btnSaveData;
    private StudentDataBase dataBase;
    private StudentsModel studentsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_records);

        etStudentName = findViewById(R.id.et_name);
        etStudentEmail = findViewById(R.id.et_email);
        etStudentAddress = findViewById(R.id.et_address);
        etStudentPhoneNo = findViewById(R.id.et_phone_number);
        etStudentQualification = findViewById(R.id.et_qualification);
        btnSaveData = findViewById(R.id.btn_save);

        //dataBase = StudentDataBase.getInstance(AddRecordsActivity.this);

        btnSaveData.setOnClickListener(v -> {

            //studentsModel = new StudentsModel(name, email, address, phoneNo, qualified);
            new backGroundTaskThread().start();
            //new InsertTask(AddRecordsActivity.this, studentsModel).execute();

        });
    }

    /*private void setResult(StudentsModel studentsModel, int i) {
        setResult(i, new Intent().putExtra("studentsModel", studentsModel.toString()));
        finish();
    }

    public static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddRecordsActivity> activityReference;
        private StudentsModel studentsModel;

        InsertTask(AddRecordsActivity context, StudentsModel studentsModel) {
            activityReference = new WeakReference<>(context);
            this.studentsModel = studentsModel;
        }

        @Override
        protected Boolean doInBackground(Void... object) {
            activityReference.get().dataBase.getStudentDao().insertRecords(studentsModel);
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                activityReference.get().setResult(studentsModel, 1);
            }
        }
    }*/

    public class backGroundTaskThread extends Thread{
        @Override
        public void run() {
            super.run();
            dataBase = Room.databaseBuilder(AddRecordsActivity.this, StudentDataBase.class, "student_database")
                    .allowMainThreadQueries().build();
            String name = etStudentName.getText().toString();
            String email = etStudentEmail.getText().toString();
            String address = etStudentAddress.getText().toString();
            String phoneNo = etStudentPhoneNo.getText().toString();
            String qualified = etStudentQualification.getText().toString();

            StudentDao studentDao = dataBase.getStudentDao();
            studentsModel = new StudentsModel(name, email, address, phoneNo, qualified);
            studentDao.insertRecords(studentsModel);
            etStudentName.setText("");
            etStudentEmail.setText("");
            etStudentAddress.setText("");
            etStudentPhoneNo.setText("");
            etStudentQualification.setText("");
        }
    }

}