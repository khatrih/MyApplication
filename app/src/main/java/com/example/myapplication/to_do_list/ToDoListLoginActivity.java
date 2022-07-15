package com.example.myapplication.to_do_list;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.to_do_list.Models.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ToDoListLoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 2;
    private TextInputEditText teUserEmail;
    private TextInputEditText teUserPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private GoogleSignInClient mSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_login);

        teUserEmail = findViewById(R.id.et_to_do_login_email);
        teUserPassword = findViewById(R.id.et_to_do_login_password);

        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvRegister = findViewById(R.id.txt_register);

        ImageView ivGoogle = findViewById(R.id.tv_google);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        dialog = new ProgressDialog(ToDoListLoginActivity.this);
        dialog.setMessage("please wait...");

        btnLogin.setOnClickListener(v -> checkValidationOnLogin());

        tvRegister.setOnClickListener(v -> {
            Intent in = new Intent(ToDoListLoginActivity.this, ToDoListRegistrationActivity.class);
            startActivity(in);
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        ivGoogle.setOnClickListener(v -> googleSignIn());
    }

    private void googleSignIn() {
        Intent signInIntent = mSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("TAG", "Google sign in failed", e);
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(this, authResult -> {
                    String gEmail = authResult.getUser().getEmail();
                    String gName = authResult.getUser().getDisplayName();
                    UserModel userModel = new UserModel(gName, gEmail);
                    String userId = authResult.getUser().getUid();
                    database.getReference().child("users").child(userId).setValue(userModel);
                    ToDoListLoginActivity.this.startActivity(new Intent(ToDoListLoginActivity.this, ToDoListHomeActivity.class));
                    ToDoListLoginActivity.this.finish();
                })
                .addOnFailureListener(this, e -> Toast.makeText(ToDoListLoginActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show());
    }

    private void checkValidationOnLogin() {
        String email = teUserEmail.getText().toString().trim();
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
        String mPassword = sha256(password);
        mAuth.signInWithEmailAndPassword(email, mPassword).addOnCompleteListener(task -> {
            dialog.dismiss();
            if (task.isSuccessful()) {
                startActivity(new Intent(ToDoListLoginActivity.this, ToDoListHomeActivity.class));
                finish();
            } else {
                Toast.makeText(ToDoListLoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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

