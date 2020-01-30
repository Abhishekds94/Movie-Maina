package com.abhishek.moviemania;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
        adapter.notifyDataSetChanged();

        if(myDataas != null) {
            myDataas.clear();
            Log.e("3rd","3rd-"+myDataas);
        }
        Log.e("RV","RV"+recyclerView);

        layoutManager = new LinearLayoutManager(DashboardActivity.this);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

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


    public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void clearData() {

        int size = myDataas.size();
        myDataas.clear();
        adapter.notifyItemRangeRemoved(0, size);
        adapter = new Adapter(new ArrayList<MyDataa>(), this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                if(myDataas != null) {
                    clearData();
                    Log.e("---", "=================================================" + myDataas);

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fetchData(){
        if(myDataas != null) {
            myDataas.clear();
            Log.e("3rd","3rd-"+myDataas);
        }
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
        if(myDataas != null) {
            myDataas.clear();
            Log.e("3rd","3rd-"+myDataas);
        }
        ApiInterface apiInterface = ApiClient.getApiClient().create((ApiInterface.class));
        Call<Result> call;
        call = apiInterface.getResult(API_KEY);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful() && response.body().getResultt() != null) {
                    if(myDataas != null) {
                        myDataas.clear();
                        Log.e("1st","1st-"+myDataas);
                    }
                    adapter.notifyDataSetChanged();
                    myDataas = response.body().getResultt();
                    Log.e("2nd","2nd-"+myDataas);
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
        if(myDataas != null) {
            myDataas.clear();
            Log.e("3rd","3rd-"+myDataas);
        }
        ApiInterface apiInterface = ApiClient.getApiClient().create((ApiInterface.class));
        Call<Result> call;
        call = apiInterface.getPopular(API_KEY);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful() && response.body().getResultt() != null) {
                    if(myDataas != null) {
                        myDataas.clear();
                        Log.e("3rd","3rd-"+myDataas);
                    }
                    adapter.notifyDataSetChanged();
                    myDataas = response.body().getResultt();
                    Log.e("4th","4th-"+myDataas);
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
        if(myDataas != null) {
            myDataas.clear();
            Log.e("1st","1st-"+myDataas);
        }

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
            if(myDataas != null) {
                myDataas.clear();
                Log.e("1st","1st-"+myDataas);
            }
            adapter.notifyDataSetChanged();
            LoadJson1();

        } else {
            Log.e("In","In PSP");
            Log.e("top", "Sorting by top"+myDataas);
            if(myDataas != null) {
                myDataas.clear();
                Log.e("1st","1st-"+myDataas);
            }
            adapter.notifyDataSetChanged();
            LoadJson();

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        checkSortOrder();
        if(myDataas != null) {
            myDataas.clear();
            Log.e("1st","1st-"+myDataas);
        }

    }

}