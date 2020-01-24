package com.abhishek.moviemania.API;

import com.abhishek.moviemania.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface{

    @GET("top_rated")
    Call<Result> getResult(
      @Query("api_key") String api_key,
      @Query("page") int pages
    );
}
