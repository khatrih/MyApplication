package com.example.myapplication.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ToDoListLoginActivity extends AppCompatActivity {
    private TextInputEditText teUserEmail;
    private TextInputEditText teUserPassword;
    private TextView tvRegister;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_login);

        teUserEmail = findViewById(R.id.et_to_do_login_email);
        teUserPassword = findViewById(R.id.et_to_do_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.txt_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        dialog = new ProgressDialog(ToDoListLoginActivity.this);
        dialog.setMessage("please wait...");

        btnLogin.setOnClickListener(v -> {
            checkValidationOnLogin();
        });
        tvRegister.setOnClickListener(v -> {
            Intent in = new Intent(ToDoListLoginActivity.this, ToDoListRegistrationActivity.class);
            startActivity(in);
        });
    }

    private void checkValidationOnLogin() {
        String email = teUserEmail.getText().toString();
        String password = teUserPassword.getText().toString();

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
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            dialog.dismiss();
            if (task.isSuccessful()) {
                startActivity(new Intent(ToDoListLoginActivity.this, ToDoListHomeActivity.class));
                finish();
            } else {
                Toast.makeText(ToDoListLoginActivity.this, "Something went wrong" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null) {
            startActivity(new Intent(ToDoListLoginActivity.this, ToDoListHomeActivity.class));
        }
    }
}