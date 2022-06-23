package com.example.myapplication.databasedemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;

public class DataBaseMainActivity extends AppCompatActivity {

    private EditText studentName;
    private EditText studentEmail;
    private EditText studentNumber;
    private EditText studentAddress;
    private EditText etStudentQualification;
    private ImageView ivStudentImage;
    private AppCompatButton addCourseData;
    private AppCompatButton readCourseData;
    private DatabaseHandler dbHandler;
    private static final int SELECT_PICTURE = 100;
    private Uri selectedImageUri;
    private Bitmap imageStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_main);
        dbHandler = new DatabaseHandler(DataBaseMainActivity.this);

        studentName = findViewById(R.id.student_name);
        studentEmail = findViewById(R.id.student_email);
        studentNumber = findViewById(R.id.student_mobile_number);
        studentAddress = findViewById(R.id.student_address);
        etStudentQualification = findViewById(R.id.student_qualification);
        addCourseData = findViewById(R.id.add_data);
        readCourseData = findViewById(R.id.read_data);
        ivStudentImage = findViewById(R.id.img_student_image);

        /*courseType = new ArrayList<>();
        courseType.add("none");
        courseType.add("MCA");
        courseType.add("MBA");

        ArrayAdapter adapter = new ArrayAdapter(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, courseType);
        spStudentQualification.setAdapter(adapter);*/

        ivStudentImage.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(galleryIntent, "select picture"), SELECT_PICTURE);
        });

        addCourseData.setOnClickListener(v -> {
            String sName = studentName.getText().toString().trim();
            String sEmail = studentEmail.getText().toString().trim();
            String sPhoneNumber = studentNumber.getText().toString().trim();
            String sAddress = studentAddress.getText().toString().trim();
            String sQualification = etStudentQualification.getText().toString();

            /*spStudentQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    type = view.toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

            if (sName.isEmpty() && sEmail.isEmpty() && sPhoneNumber.isEmpty()
                    && sAddress.isEmpty() && sQualification.isEmpty()) {
                Toast.makeText(DataBaseMainActivity.this, "please fill the details",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            dbHandler.addNewCourse(sName, sEmail, sAddress, sPhoneNumber, sQualification, imageStore);
            Toast.makeText(DataBaseMainActivity.this, "Record Successfully added", Toast.LENGTH_SHORT).show();
            studentName.setText("");
            studentEmail.setText("");
            studentNumber.setText("");
            studentAddress.setText("");
            etStudentQualification.setText("");
            ivStudentImage.setImageResource(R.drawable.ic_round_image);
        });

        readCourseData.setOnClickListener(v -> startActivity(new Intent(DataBaseMainActivity.this, ViewCourseActivity.class)));

        //addCourseData.setText("UPDATE DATA");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                if (data != null) {
                    selectedImageUri = data.getData();
//                    ivStudentImage.setImageURI(selectedImageUri);
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
}