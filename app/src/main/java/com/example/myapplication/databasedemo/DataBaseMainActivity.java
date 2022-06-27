package com.example.myapplication.databasedemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.IOException;

public class DataBaseMainActivity extends AppCompatActivity {

    private EditText etStudentName;
    private EditText etStudentEmail;
    private EditText etStudentNumber;
    private EditText etStudentAddress;
    private EditText etStudentQualification;
    private ImageView ivStudentImage;
    private AppCompatButton btnAddCourseData;
    private AppCompatButton btnReadCourseData;
    private Uri selectedImageUri;
    private DatabaseHandler dbHandler;
    private Bitmap imageStore;
    private static final int SELECT_PICTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_main);
        dbHandler = new DatabaseHandler(DataBaseMainActivity.this);

        etStudentName = findViewById(R.id.student_name);
        etStudentEmail = findViewById(R.id.student_email);
        etStudentNumber = findViewById(R.id.student_mobile_number);
        etStudentAddress = findViewById(R.id.student_address);
        etStudentQualification = findViewById(R.id.student_qualification);
        btnAddCourseData = findViewById(R.id.add_data);
        btnReadCourseData = findViewById(R.id.read_data);
        ivStudentImage = findViewById(R.id.img_student_image);

        etStudentQualification.setHint("MCA/MBA");


        ivStudentImage.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(galleryIntent, "select picture"), SELECT_PICTURE);
        });

        btnAddCourseData.setOnClickListener(v -> {
            btnAddCourseData.setVisibility(View.VISIBLE);
            addStudentRecords();
        });

        btnReadCourseData.setOnClickListener(v -> {
            startActivity(new Intent(DataBaseMainActivity.this,
                    ViewCourseActivity.class));
        });

    }

    private void addStudentRecords() {
        String sName = etStudentName.getText().toString().trim();
        String sEmail = etStudentEmail.getText().toString().trim();
        String sPhoneNumber = etStudentNumber.getText().toString().trim();
        String sAddress = etStudentAddress.getText().toString().trim();
        String sQualification = etStudentQualification.getText().toString();

        if (sName.isEmpty() && sEmail.isEmpty() && sPhoneNumber.isEmpty()
                && sAddress.isEmpty() && sQualification.isEmpty()) {
            Toast.makeText(DataBaseMainActivity.this, "please fill the details",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etStudentQualification.getText().toString().equals("MBA") &&
                !etStudentQualification.getText().toString().equals("MCA")) {
            Toast.makeText(this, "please select anyone of \"MCA\" and \"MBA\"", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageStore == null) {
            Toast.makeText(this, "image cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }
        dbHandler.addNewCourse(sName, sEmail, sAddress, sPhoneNumber, sQualification, imageStore);
        Intent in = new Intent(DataBaseMainActivity.this, FetchingDataActivity.class);
        startActivity(in);
        Toast.makeText(DataBaseMainActivity.this, "Record Successfully added", Toast.LENGTH_SHORT).show();
        etStudentName.setText("");
        etStudentEmail.setText("");
        etStudentNumber.setText("");
        etStudentAddress.setText("");
        etStudentQualification.setText("");
        ivStudentImage.setImageResource(R.drawable.ic_round_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            if (data != null) {
                selectedImageUri = data.getData();
                //ivStudentImage.setImageURI(selectedImageUri);
                try {
                    imageStore = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    ivStudentImage.setImageBitmap(imageStore);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}