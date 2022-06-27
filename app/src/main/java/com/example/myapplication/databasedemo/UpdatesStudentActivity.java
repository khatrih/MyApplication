package com.example.myapplication.databasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.contentproviders.ContactsModel;

import java.util.ArrayList;

public class UpdatesStudentActivity extends AppCompatActivity {

    private EditText etStudName;
    private EditText etStudEmail;
    private EditText etStudNumber;
    private EditText etStudAddress;
    private EditText etStudQualification;
    private ImageView ivStudentImage;
    private AppCompatButton btnUpdateCourseData;
    private DatabaseHandler dbHandler;
    private static final String STUDENT_ID = "id";
    private static final String STUDENT_NAME = "NAME";
    private static final String STUDENT_EMAIL = "EMAIL";
    private static final String STUDENT_ADDRESS = "ADDRESS";
    private static final String STUDENT_CONTACT_NO = "CONTACT";
    private static final String STUDENT_COURSE_NAME = "COURSE_NAME";
    private static final String STUDENT_IMAGE = "IMAGE";
    ArrayList<CourseDataModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates_student);
        dbHandler = new DatabaseHandler(UpdatesStudentActivity.this);

        etStudName = findViewById(R.id.et_student_name);
        etStudEmail = findViewById(R.id.et_student_email);
        etStudNumber = findViewById(R.id.et_student_mobile_number);
        etStudAddress = findViewById(R.id.et_student_address);
        etStudQualification = findViewById(R.id.et_student_qualification);
        ivStudentImage = findViewById(R.id.img_update_student_image);
        btnUpdateCourseData = findViewById(R.id.btn_update_data);

        String name = getIntent().getStringExtra(STUDENT_NAME);
        String email = getIntent().getStringExtra(STUDENT_EMAIL);
        String contactsNo = getIntent().getStringExtra(STUDENT_CONTACT_NO);
        String address = getIntent().getStringExtra(STUDENT_ADDRESS);
        String courseName = getIntent().getStringExtra(STUDENT_COURSE_NAME);
        byte[] image = getIntent().getByteArrayExtra(STUDENT_IMAGE);
        int ids = getIntent().getIntExtra(STUDENT_ID, 1);
        Bitmap bitImage = BitmapFactory.decodeByteArray(image, 0, image.length);

        etStudName.setText(name);
        etStudEmail.setText(email);
        etStudNumber.setText(contactsNo);
        etStudAddress.setText(address);
        etStudQualification.setText(courseName);
        ivStudentImage.setImageBitmap(bitImage);

        btnUpdateCourseData.setOnClickListener(v -> {
            String nameStudent = etStudName.getText().toString();
            String emailStudent = etStudEmail.getText().toString();
            String addressStudent = etStudAddress.getText().toString();
            String phoneStudent = etStudNumber.getText().toString();
            String QuaStudent = etStudQualification.getText().toString();

            if (nameStudent.isEmpty() && emailStudent.isEmpty() && phoneStudent.isEmpty()
                    && addressStudent.isEmpty() && QuaStudent.isEmpty()) {
                Toast.makeText(UpdatesStudentActivity.this, "please fill the details",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!etStudName.getText().toString().equals("MBA") &&
                    !etStudName.getText().toString().equals("MCA")) {
                Toast.makeText(this, "please select anyone of \"MCA\" and \"MBA\"", Toast.LENGTH_SHORT).show();
                return;
            }
            if (bitImage == null) {
                Toast.makeText(this, "image cannot be empty", Toast.LENGTH_LONG).show();
                return;
            }

            dbHandler.updateStudentDetails(name, nameStudent, emailStudent, addressStudent, phoneStudent, QuaStudent, bitImage);

            Log.d("TAG", "Updated done ");
            etStudName.setText("");
            etStudEmail.setText("");
            etStudNumber.setText("");
            etStudAddress.setText("");
            etStudQualification.setText("");
            ivStudentImage.setImageResource(R.drawable.ic_round_image);
        });
    }
}