package com.example.myapplication.loginflow;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapplication.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView login = findViewById(R.id.login_txt);

        Button signUp = findViewById(R.id.signUp_button);

        signUp.setOnClickListener(v -> openLoginScreen());

        login.setOnClickListener(v -> openLoginScreen());


    }

    private void openLoginScreen() {
        Intent loginIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "onStart_SecondActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "onResume_SecondActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG", "onRestart_SecondActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "onPause_SecondActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "onStop_SecondActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy_SecondActivity");
    }

   @Override
    public void onBackPressed() {
        onBackPressed();
    }
}