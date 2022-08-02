package com.example.myapplication.apidemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiInterface {

    @GET("v2/users")
    Call<List<RTFUserModel>> getApiUsers(
            @Header("Accept") String Accept,
            @Header("Content-Type") String Content_Type,
            @Header("Authorization") String Authorization
    );

    @POST("v2/users")
    Call<RTFUserModel> createUsers(
            @Header("Accept") String Accept,
            @Header("Content-Type") String Content_Type,
            @Header("Authorization") String Authorization,
            @Body RTFUserModel model
    );

    @PUT("v2/users/{id}")
    Call<RTFUserModel> updateUsers(
            @Header("Accept") String Accept,
            @Header("Content-Type") String Content_Type,
            @Header("Authorization") String Authorization,
            @Path("id") int id,
            @Body RTFUserModel model
    );

    @DELETE("v2/users/{id}")
    Call<RTFUserModel> deleteUser(
            @Header("Accept") String Accept,
            @Header("Content-Type") String Content_Type,
            @Header("Authorization") String Authorization,
            @Path("id") int id
    );
}
