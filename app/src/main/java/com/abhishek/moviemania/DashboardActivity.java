package com.abhishek.moviemania;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.abhishek.moviemania.API.ApiClient;
import com.abhishek.moviemania.API.ApiInterface;
import com.abhishek.moviemania.model.Adapter;
import com.abhishek.moviemania.model.MyDataa;
import com.abhishek.moviemania.model.Result;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    public static final String API_KEY = "b9227e455238e6a36dc7deddd582f0b2";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private List<MyDataa> myDataas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.rv_dashboard);
        adapter = new Adapter(new ArrayList<MyDataa>(), this);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(DashboardActivity.this);
        recyclerView.setLayoutManager((new GridLayoutManager(this, 2)));
        recyclerView.setItemAnimator((new DefaultItemAnimator()));
        recyclerView.setNestedScrollingEnabled(false);

        LoadJson();

    }

    public void LoadJson(){
        ApiInterface apiInterface = ApiClient.getApiClient().create((ApiInterface.class));
        Call<Result> call;
        call = apiInterface.getResult(API_KEY);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful() && response.body().getResultt() != null) {
                    if(myDataas != null) {
                        myDataas.clear();
                    }
                    myDataas = response.body().getResultt();
                    adapter.addAll(myDataas);
                } else {
                    Toast.makeText(DashboardActivity.this, "No Results Found!!", Toast.LENGTH_SHORT).show();
                    Log.i("Response!","Response!"+response);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

}