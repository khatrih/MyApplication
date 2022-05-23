package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AppCompatButton okBtn;
    EditText userName, email, password, confirmPassword;
    ImageView uploadImage;
    Button clickImageBtn;
    RadioGroup rGroup;
    AppCompatSpinner spinnerCountry;
    int selectedId;
    ArrayList<String> listOfCountry = new ArrayList<>();

    String gender;
    String countryName;

    private static final String IMAGE = "image";
    private static final int CAMERA_REQUEST = 100;
    private static final int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        confirmPassword = findViewById(R.id.etConfirmPW);

        rGroup = findViewById(R.id.radioBtn);

        okBtn = findViewById(R.id.btnOK);

        uploadImage = findViewById(R.id.PutImage);

        clickImageBtn = findViewById(R.id.clickImageBtn);

        spinnerCountry = findViewById(R.id.spin);

        listOfCountry.add("India");
        listOfCountry.add("South Africa");
        listOfCountry.add("England");
        listOfCountry.add("Pakistan");
        listOfCountry.add("Nepal");

        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listOfCountry);
        spinnerCountry.setAdapter(adapter);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryName = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        rGroup.clearCheck();
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                selectedId = rGroup.getCheckedRadioButtonId();
                if (checkedId == R.id.male) {
                    gender = getResources().getString(R.string.male);
                } else {
                    gender = getResources().getString(R.string.female);
                }
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ShowDetailsActivity.class);
                Bundle b = new Bundle();
                i.putExtra("i", countryName);
                i.putExtra("gender", gender);
                startActivity(i);
            }
        });

/*        clickImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                Intent galleryIntent = new Intent();
//                galleryIntent.setType("image/*");
//                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(galleryIntent, "select picture"), SELECT_PICTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });*/

        userName.setText("");
        email.setText("");
        password.setText("");
        confirmPassword.setText("");
    }

    private void checkDetails() {

        String name = userName.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String passcode = password.getText().toString().trim();
        String confirmationPW = confirmPassword.getText().toString().trim();

       /* if (name.isEmpty()) {
            etName.setError("please enter the name");
            etName.requestFocus();
            return;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("please enter the valid email");
            etEmail.requestFocus();
            return;
        }
        if (passcode.isEmpty()) {
            etPassword.setError("please enter the password between 6 to 16 characters");
            etPassword.requestFocus();
            return;
        }
        if (!confirmationPW.equals(passcode)) {
            etConfirmPW.setError("please check the passcode");
            etConfirmPW.requestFocus();
            return;
        }*/

        Intent in = new Intent(MainActivity.this, ShowDetailsActivity.class);
        /*in.putExtra("name", name);
        in.putExtra("email", email);
        in.putExtra("passcode", passcode);
        in.putExtra("confirmPassword", confirmationPW);*/

        /*Bundle extras = new Bundle();
        extras.putInt("rbtn",selectedId);
        in.putExtras(extras);*/

        uploadImage.buildDrawingCache();
        Bitmap image = uploadImage.getDrawingCache();
        ByteArrayOutputStream _bs = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 50, _bs);
        in.putExtra("byteArray", _bs.toByteArray());
//        Bundle extras = new Bundle();
//        extras.putParcelable("imagebitmap", image);
//        in.putExtras(extras);
        startActivity(in);

    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            puttingImage.setImageBitmap(imageBitmap);
        }
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                if (null != selectedImageUri) {
//                    puttingImage.setImageURI(selectedImageUri);
//                }
//            }
//        }
    }*/
}