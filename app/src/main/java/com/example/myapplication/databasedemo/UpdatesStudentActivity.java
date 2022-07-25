package com.example.myapplication.databasedemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.IOException;

public class UpdatesStudentActivity extends AppCompatActivity {

    private EditText etStudName;
    private EditText etStudEmail;
    private EditText etStudNumber;
    private EditText etStudAddress;
    private EditText etStudQualification;
    private ImageView ivStudentImage;
    private DatabaseHandler dbHandler;
    private Bitmap imageStore;
    private static final int SELECT_PICTURE = 200;

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
        AppCompatButton btnUpdateCourseData = findViewById(R.id.btn_update_data);

        ivStudentImage.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(galleryIntent, "select picture"), SELECT_PICTURE);
        });

        Intent i = getIntent();
        CourseDataModel courseDataModel = (CourseDataModel) i.getSerializableExtra("CourseModel");
        String name = courseDataModel.getsName();
        etStudName.setText(courseDataModel.getsName());
        etStudEmail.setText(courseDataModel.getsEmail());
        etStudNumber.setText(courseDataModel.getsPhoneNUmber());
        etStudAddress.setText(courseDataModel.getSAddress());
        etStudQualification.setText(courseDataModel.getsDegreeType());
        Bitmap bitImage = BitmapFactory.decodeByteArray(courseDataModel.getsImage(), 0, courseDataModel.getsImage().length);
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

            dbHandler.updateStudentDetails(name, nameStudent, emailStudent, addressStudent, phoneStudent, QuaStudent, imageStore);
            Log.d("TAG", "Updated done ");
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
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