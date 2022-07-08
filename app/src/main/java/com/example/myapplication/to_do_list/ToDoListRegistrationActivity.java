package com.example.myapplication.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.to_do_list.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ToDoListRegistrationActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etUserEmail;
    private EditText etUeerPassword;
    private EditText etUSerConfirmPassword;
    private Button btnRegistration;
    private FirebaseAuth mAuth;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_registration);

        initializeId();
        mAuth = FirebaseAuth.getInstance();

        createNewUser();
    }

    private void createNewUser() {

        String userName = etUserName.getText().toString().trim();
        String userEmail = etUserEmail.getText().toString().trim();
        String userPassword = etUeerPassword.getText().toString().trim();
        String userConfirmPassword = etUSerConfirmPassword.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        UserModel userModel = new UserModel()
                        Log.d(TAG, "createNewUser: "+user);
                    } else {
                        Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initializeId() {
        etUserName = findViewById(R.id.et_to_do_name);
        etUserEmail = findViewById(R.id.et_to_do_email);
        etUeerPassword = findViewById(R.id.et_to_do_password);
        etUSerConfirmPassword = findViewById(R.id.et_to_do_confirm_password);
        btnRegistration = findViewById(R.id.btn_register);
    }
}