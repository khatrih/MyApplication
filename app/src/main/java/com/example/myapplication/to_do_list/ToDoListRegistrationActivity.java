package com.example.myapplication.to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.to_do_list.Models.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


public class ToDoListRegistrationActivity extends AppCompatActivity {
    private TextInputEditText teUserName;
    private TextInputEditText teUserEmail;
    private TextInputEditText teUserPassword;
    private TextInputEditText teUserConfirmPassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_registration);

        getSupportActionBar().hide();

        teUserName = findViewById(R.id.et_to_do_name);
        teUserEmail = findViewById(R.id.et_to_do_email);
        teUserPassword = findViewById(R.id.et_to_do_password);
        teUserConfirmPassword = findViewById(R.id.et_to_do_confirm_password);

        Button btnRegistration = findViewById(R.id.btn_register);

        TextView tvLogin = findViewById(R.id.txt_login);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        dialog = new ProgressDialog(ToDoListRegistrationActivity.this);
        dialog.setTitle("creating account");
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);

        btnRegistration.setOnClickListener(v -> {
            sha256(teUserPassword.toString());
            checkValidation();
        });

        tvLogin.setOnClickListener(v -> {
            finish();
        });
    }

    private void checkValidation() {
        String name = teUserName.getText().toString();
        String email = teUserEmail.getText().toString();
        String password = teUserPassword.getText().toString();
        String confirmPassword = teUserConfirmPassword.getText().toString();
        String mPassword = sha256(password);
        String mConfirmPassword = sha256(confirmPassword);

        //String confirmPasswordHashKey =  sha256(confirmPassword);
        if (name.isEmpty()) {
            teUserName.setError("please enter name");
            teUserName.requestFocus();
            return;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            teUserEmail.setError("please enter the valid email");
            teUserEmail.requestFocus();
            return;
        }
        if (password.isEmpty() || password.length() < 6) {
            teUserPassword.setError("please enter the password between 6 to 16 characters");
            teUserPassword.requestFocus();
            return;
        }
        if (!confirmPassword.equals(password)) {
            if (confirmPassword.isEmpty()) {
                teUserConfirmPassword.setError("please enter the password");
            }
            teUserConfirmPassword.setError("please check your password");
            teUserConfirmPassword.requestFocus();
            return;
        }

        dialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        UserModel userModel = new UserModel(name, email, mPassword, mConfirmPassword);
                        String userId = task.getResult().getUser().getUid();
                        database.getReference().child("users").child(userId).setValue(userModel);
                        finish();
                    } else {
                        Toast.makeText(ToDoListRegistrationActivity.this, "Something went wrong" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(ToDoListRegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show());
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}