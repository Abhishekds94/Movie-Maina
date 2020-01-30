package com.abhishek.moviemania.API;

import com.abhishek.moviemania.model.Result;
import com.abhishek.moviemania.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface{

    @GET("top_rated")
    Call<Result> getResult(
      @Query("api_key") String api_key
    );

    @GET("popular")
    Call<Result> getPopular(
            @Query("api_key") String api_key
    );

    @GET("{movie_id}/videos")
    Call<TrailerResponse> getTrailers(
            @Path("movie_id") int id,
            @Query("api_key") String api_key
    );
}
