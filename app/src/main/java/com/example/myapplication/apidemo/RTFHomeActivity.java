package com.example.myapplication.apidemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RTFHomeActivity extends AppCompatActivity {

    private static final String Accept = "application/json";
    private static final String Content_Type = "application/json";
    private static final String Authorization = "Bearer 2d29e9b750eb0c822aa99f5bce491a2c18017a025b1dc0a01d86c6ec4015bee7";
    private RecyclerView rvUsers;
    private TextView textView;
    private static final String TAG = "TAG";
    private List<RTFUserModel> rtfUserModelList;
    private UserApiAdapter adapter;
    private ProgressDialog progressDialog;
    private UserApiInterface userApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtfhome);

        textView = findViewById(R.id.txt_data);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingAction);
        rvUsers = findViewById(R.id.rv_item_list);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton.setOnClickListener(v ->
                startActivity(new Intent(this, RTFAddUpdateActivity.class)));

        progressDialog = new ProgressDialog(RTFHomeActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        rtfUserModelList = new ArrayList<>();
        userApiInterface = RetrofitApiInstance.getApiRetrofit().create(UserApiInterface.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsers();
    }

    private void getUsers() {
        userApiInterface.getApiUsers(Accept, Content_Type, Authorization).enqueue(new Callback<List<RTFUserModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<RTFUserModel>> call, @NonNull Response<List<RTFUserModel>> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    rtfUserModelList = response.body();
                    if (rtfUserModelList.isEmpty()) {
                        textView.setText(R.string.no_data);
                    } else {
                        rvUsers.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.GONE);
                        adapter = new UserApiAdapter(rtfUserModelList, RTFHomeActivity.this);
                        rvUsers.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RTFUserModel>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    /*public class GetUsers extends AsyncTask<String, Void, List<RTFUserModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected List<RTFUserModel> doInBackground(String... strings) {
            rtfUserModelList = new ArrayList<>();
            userApiInterface.getApiUsers().enqueue(new Callback<List<RTFUserModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<RTFUserModel>> call, @NonNull Response<List<RTFUserModel>> response) {
                    if (response.body() != null) {
                        rtfUserModelList = response.body();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<RTFUserModel>> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
            return rtfUserModelList;
        }

        @Override
        protected void onPostExecute(List<RTFUserModel> rtfUserModels) {
            super.onPostExecute(rtfUserModels);
            progressDialog.dismiss();
            if (rtfUserModels.isEmpty()) {
                textView.setText(R.string.no_data);
            } else {
                rvUsers.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                UserApiAdapter adapter = new UserApiAdapter(rtfUserModels, RTFHomeActivity.this);
                rvUsers.setAdapter(adapter);
            }
        }
    }*/
}