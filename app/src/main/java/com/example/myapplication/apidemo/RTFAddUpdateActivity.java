package com.example.myapplication.apidemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RTFAddUpdateActivity extends AppCompatActivity {

    private TextInputEditText teUserName;
    private TextInputEditText teUserEmail;
    private AppCompatSpinner genderSpinner;
    private ProgressDialog progressDialog;
    private static final String TAG = "TAG";
    private boolean isUpdate = false;
    private RTFUserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtfadd_update);

        teUserName = findViewById(R.id.et_users_name);
        teUserEmail = findViewById(R.id.et_users_email);
        genderSpinner = findViewById(R.id.sp_user_gender);
        Button btnSaveUser = findViewById(R.id.btn_save_user_data);

        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("select gender");
        genderList.add("Male");
        genderList.add("Female");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, genderList);
        genderSpinner.setAdapter(genderAdapter);

        isUpdate = getIntent().getBooleanExtra("isUpdated", false);

        if (isUpdate) {
            btnSaveUser.setText(R.string.update_btn);
            userModel = (RTFUserModel) getIntent().getSerializableExtra("RTFUserModel");
            teUserName.setText(userModel.getName());
            teUserEmail.setText(userModel.getEmail());
//            genderSpinner.setSelection(genderAdapter.getPosition(userModel.getGender()));
            genderSpinner.setSelection(genderAdapter.getPosition(userModel.getGender()));
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("saving data...");
        progressDialog.setCancelable(false);

        btnSaveUser.setOnClickListener(v -> {
            if (teUserName.getText().toString().isEmpty()) {
                teUserName.setError("please fill name field");
                teUserName.requestFocus();
                return;
            }
            if (teUserEmail.getText().toString().isEmpty()) {
                teUserEmail.setError("please fill email field");
                teUserEmail.requestFocus();
                return;
            }
            if (genderSpinner.equals("select gender")) {
                teUserName.setError("please select anyone");
                teUserName.requestFocus();
                return;
            }
            postUserData(teUserName.getText().toString(), teUserEmail.getText().toString(),
                    genderSpinner.getSelectedItem().toString());
        });
    }

    private void postUserData(String name, String email, String gender) {
        progressDialog.show();

        String Accept = "application/json";
        String Content_Type = "application/json";
        String Authorization = "Bearer 2d29e9b750eb0c822aa99f5bce491a2c18017a025b1dc0a01d86c6ec4015bee7";

        RTFUserModel model = new RTFUserModel(name, email, gender, "active");

        UserApiInterface userApiInterface = RetrofitApiInstance.getApiRetrofit().create(UserApiInterface.class);
        if (isUpdate) {
            userApiInterface.updateUsers(Accept, Content_Type, Authorization, userModel.getId(), model)
                    .enqueue(new Callback<RTFUserModel>() {
                        @Override
                        public void onResponse(Call<RTFUserModel> call, Response<RTFUserModel> response) {
                            userModel = response.body();
                            Log.d(TAG, "response code updated " + response.code());
                            Log.d(TAG, "response name updated " + userModel.getName());
                            Log.d(TAG, "response email updated " + userModel.getEmail());
                            Log.d(TAG, "response gender updated " + userModel.getGender());
                        }

                        @Override
                        public void onFailure(Call<RTFUserModel> call, Throwable t) {
                            t.printStackTrace();
                            Log.d(TAG, "onFailure: updated " + t.getLocalizedMessage());
                        }
                    });
        } else {
            userApiInterface.createUsers(Accept, Content_Type, Authorization, model)
                    .enqueue(new Callback<RTFUserModel>() {
                        @Override
                        public void onResponse(Call<RTFUserModel> call, Response<RTFUserModel> response) {
                            userModel = response.body();
                            Log.d(TAG, "response code add " + response.code());
                            Log.d(TAG, "response name add " + userModel.getName());
                            Log.d(TAG, "response email add " + userModel.getEmail());
                            Log.d(TAG, "response gender add " + userModel.getGender());
                        }

                        @Override
                        public void onFailure(Call<RTFUserModel> call, Throwable t) {
                            t.printStackTrace();
                            Log.d(TAG, "onFailure: add" + t.getLocalizedMessage());
                        }
                    });
        }
        progressDialog.dismiss();
        finish();
    }
}