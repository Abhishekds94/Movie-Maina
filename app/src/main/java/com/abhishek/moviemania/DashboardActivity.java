package com.abhishek.moviemania;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import com.abhishek.moviemania.API.ApiClient;
import com.abhishek.moviemania.API.ApiInterface;
import com.abhishek.moviemania.model.Adapter;
import com.abhishek.moviemania.model.MyDataa;
import com.abhishek.moviemania.model.Result;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String API_KEY = "b9227e455238e6a36dc7deddd582f0b2";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private List<MyDataa> myDataas;
    private int page_number=1;
    Boolean isScrolling = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.rv_dashboard);
        adapter = new Adapter(new ArrayList<MyDataa>(), this);
        Log.e("ada","adapter"+adapter);
        recyclerView.setAdapter(adapter);
        Log.e("RV","RV"+recyclerView);

        layoutManager = new LinearLayoutManager(DashboardActivity.this);
        recyclerView.setLayoutManager((new GridLayoutManager(this, 2)));
        recyclerView.setItemAnimator((new DefaultItemAnimator()));
        recyclerView.setNestedScrollingEnabled(false);

//        LoadJson();
//        checkSortOrder();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    final int visibleTreshold = 2;
                    GridLayoutManager layoutManager = (GridLayoutManager)recyclerView.getLayoutManager();
                    int lastItem = layoutManager.findFirstCompletelyVisibleItemPosition();
                    int currentTotalCount = layoutManager.getItemCount();

                    if (currentTotalCount <= lastItem + visibleTreshold){
                        page_number++;
                        isScrolling = false;
                        LoadJson();
                    }
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fetchData(){

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

    public void LoadJson1(){
        ApiInterface apiInterface = ApiClient.getApiClient().create((ApiInterface.class));
        Call<Result> call;
        call = apiInterface.getPopular(API_KEY);
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        checkSortOrder();
    }

    private void checkSortOrder() {
        Log.e("In","In CS");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_sort_popular)
        );
        if (sortOrder.equals(this.getString(R.string.pref_sort_popular))){
            Log.e("In","In PSP");
            Log.e("pop", "Sorting by most popular"+myDataas);
            if(myDataas != null){
                myDataas = null;
            }
            adapter.notifyDataSetChanged();
            LoadJson1();

        } else {
            Log.e("In","In PSP");
            Log.e("top", "Sorting by top"+myDataas);
            if(myDataas != null){
                myDataas = null;
            }
            adapter.notifyDataSetChanged();
            LoadJson();

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        checkSortOrder();
        myDataas=null;

    }

}