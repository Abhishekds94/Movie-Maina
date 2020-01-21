package com.abhishek.moviemania.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    //Base URL
    public static String base_URL = "http://image.tmdb.org/t/p/";

    //Retrofit
    public static Retrofit getClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static APIInterface apiInterface(){
        return getClient().create(APIInterface.class);
    }

}
