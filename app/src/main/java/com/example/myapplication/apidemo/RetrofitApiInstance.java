package com.example.myapplication.apidemo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiInstance {
    private static Retrofit retrofit = null;

    private static final String BASE_URL = "https://gorest.co.in/public/";

    public static Retrofit getApiRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
