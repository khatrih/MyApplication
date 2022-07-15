package com.example.myapplication.jsonUsingApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.layouts.ReadingFile;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonLoadActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_load);

        recyclerView = findViewById(R.id.json_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(JsonLoadActivity.this);
        recyclerView.setLayoutManager(llm);

        progressBar = findViewById(R.id.progressBar);

        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        apiInterface.getPosts().enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                progressBar.setVisibility(View.VISIBLE);
                if (response.body() != null) {
                    if (response.body().size() > 0) {
                        progressBar.setVisibility(View.GONE);

                        List<PostModel> postModels = response.body();

                        postAdapter = new PostAdapter(JsonLoadActivity.this, postModels);
                        recyclerView.setAdapter(postAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Toast.makeText(JsonLoadActivity.this, "something wrong " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}