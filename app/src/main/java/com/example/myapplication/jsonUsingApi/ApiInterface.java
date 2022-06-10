package com.example.myapplication.jsonUsingApi;

import com.example.myapplication.model.CardItemModel;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("posts")
    Call<List<PostModel>> getPosts();

    //Call<List<CardItemModel>> getmodel();
}
