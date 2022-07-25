package com.example.myapplication.databasedemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;

public class AddUpdateActivity extends AppCompatActivity {

    private EditText etStudentName;
    private EditText etStudentEmail;
    private EditText etStudentNumber;
    private EditText etStudentAddress;
    private ImageView ivStudentImage;
    private AppCompatButton btnAddCourseData;
    private AppCompatSpinner studentQualificationSpinner;
    private DatabaseHandler dbHandler;
    private Bitmap bitmapImage;
    private static final int SELECT_PICTURE = 100;
    private boolean isUpdate = false;
    private CourseDataModel courseDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);
        dbHandler = new DatabaseHandler(AddUpdateActivity.this);

        etStudentName = findViewById(R.id.student_name);
        etStudentEmail = findViewById(R.id.student_email);
        etStudentNumber = findViewById(R.id.student_mobile_number);
        etStudentAddress = findViewById(R.id.student_address);
        studentQualificationSpinner = findViewById(R.id.student_qualification);
        ivStudentImage = findViewById(R.id.img_student_image);
        btnAddCourseData = findViewById(R.id.add_data);
        ImageView ivCamera = findViewById(R.id.camera);

        ArrayList<String> mCourseList = new ArrayList<>();
        mCourseList.add("select course");
        mCourseList.add("MCA");
        mCourseList.add("MBA");

        ArrayAdapter<String> courseList = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mCourseList);
        studentQualificationSpinner.setAdapter(courseList);

        ivCamera.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(galleryIntent, "select picture"), SELECT_PICTURE);
        });

        isUpdate = getIntent().getBooleanExtra("isUpdated", false);
        if (isUpdate) {
            btnAddCourseData.setText(R.string.update_btn);
            courseDataModel = (CourseDataModel) getIntent().getSerializableExtra("CourseModel");
            etStudentName.setText(courseDataModel.getsName());
            etStudentEmail.setText(courseDataModel.getsEmail());
            etStudentNumber.setText(courseDataModel.getsPhoneNUmber());
            etStudentAddress.setText(courseDataModel.getSAddress());
            studentQualificationSpinner.setSelection(courseList.getPosition(courseDataModel.getsDegreeType()));
            bitmapImage = BitmapFactory.decodeByteArray(courseDataModel.getsImage(), 0, courseDataModel.getsImage().length);
//            Bitmap bitmap = ((BitmapDrawable) ivStudentImage.getDrawable()).getBitmap();
            ivStudentImage.setImageBitmap(bitmapImage);
        }

        btnAddCourseData.setOnClickListener(v -> {
            btnAddCourseData.setVisibility(View.VISIBLE);
            studentRecords();
        });

    }

    private void studentRecords() {
        String sName = etStudentName.getText().toString().trim();
        String sEmail = etStudentEmail.getText().toString().trim();
        String sPhoneNumber = etStudentNumber.getText().toString().trim();
        String sAddress = etStudentAddress.getText().toString().trim();
        String sQualification = studentQualificationSpinner.getSelectedItem().toString();

        if (sName.matches("")) {
            etStudentName.setError("enter valid name");
            etStudentName.requestFocus();
            return;
        }
        if (sEmail.matches("") || !Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            etStudentEmail.setError("please enter valid email");
            etStudentEmail.requestFocus();
            return;
        }
        if (sAddress.matches("")) {
            etStudentAddress.setError("please fill address field");
            etStudentAddress.requestFocus();
            return;
        }
        if (sPhoneNumber.length() < 10) {
            etStudentNumber.setError("please enter 10 digits number");
            etStudentNumber.requestFocus();
            return;
        }
        if (bitmapImage == null) {
            Toast.makeText(this, "image cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (isUpdate) {
            dbHandler.updateStudentDetails(courseDataModel.getsName(), sName, sEmail, sAddress, sPhoneNumber, sQualification, bitmapImage);
            Toast.makeText(AddUpdateActivity.this, "Record Successfully updated", Toast.LENGTH_SHORT).show();
        } else {

            dbHandler.addNewCourse(sName, sEmail, sAddress, sPhoneNumber, sQualification, bitmapImage);
            Toast.makeText(AddUpdateActivity.this, "Record Successfully added", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    ivStudentImage.setImageBitmap(bitmapImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}