package com.example.myapplication.to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class ToDoListLoginActivity extends AppCompatActivity {
    private EditText etUserEmail;
    private EditText etUserPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_login);

        etUserEmail = findViewById(R.id.et_to_do_login_email);
        etUserPassword = findViewById(R.id.et_to_do_login_password);
        btnLogin = findViewById(R.id.btn_login);
    }
}