package com.abhishek.moviemania;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import com.abhishek.moviemania.API.ApiClient;
import com.abhishek.moviemania.API.ApiInterface;
import com.abhishek.moviemania.ViewModel.MainViewModel;
import com.abhishek.moviemania.data.FavoriteDbHelper;
import com.abhishek.moviemania.database.FavoriteEntry;
import com.abhishek.moviemania.model.Adapter;
import com.abhishek.moviemania.model.MyDataa;
import com.abhishek.moviemania.model.Result;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.abhishek.moviemania.data.FavoriteDbHelper.getAllFavorite;

public class DashboardActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String API_KEY = "API-KEY";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter,favAdapter;
    private List<MyDataa> myDataas = new ArrayList<>();
    private int page_number=1;
    Boolean isScrolling = true;
    boolean isFav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.rv_dashboard);
        adapter = new Adapter(new ArrayList<MyDataa>(), this);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(myDataas != null) {
            myDataas.clear();

        }

        layoutManager = new LinearLayoutManager(DashboardActivity.this);


        //To set the layout based on orientation - Portrait and Landscape
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        recyclerView.setItemAnimator((new DefaultItemAnimator()));
        recyclerView.setNestedScrollingEnabled(false);

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
                if(dy > 0 & !isFav){
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
                    adapter.notifyDataSetChanged();
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
        if(myDataas != null) {
            myDataas.clear();
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
        }

        checkSortOrder();
    }

    private void initViews(){

        if (myDataas != null)myDataas.clear();
        isFav = true;
        recyclerView = (RecyclerView) findViewById(R.id.rv_dashboard);
        adapter = new Adapter(myDataas, this);
        getAllFavorite();
        recyclerView.setAdapter(adapter);

        // Log.e("Fav","Fav ="+getAllFavorite);


        layoutManager = new LinearLayoutManager(DashboardActivity.this);
    }

    private void checkSortOrder() {
        Log.e("In","In CS");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_sort_popular)
        );
        if (sortOrder.equals(this.getString(R.string.pref_sort_popular))){
            if(myDataas != null) {
                myDataas.clear();
            }
            isFav = false;
            adapter.notifyDataSetChanged();
            LoadJson1();

        } else if (sortOrder.equals(this.getString(R.string.pref_fav))){
            if(myDataas != null) {
                myDataas.clear();
            }
            initViews();
            Log.e("Here", "Heere0");

        }

        else {
            if(myDataas != null) {
                myDataas.clear();
            }
            isFav = false;
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
        }

    }

    @SuppressLint("StaticFieldLeak")
    private  void getAllFavorite(){
        MainViewModel viewModel = new MainViewModel(getApplication());
        viewModel.getFavorite().observe(this, new Observer<List<FavoriteEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntry> imageEntries) {
                Log.e("List Len",String.valueOf(imageEntries.size()));
                for (FavoriteEntry entry : imageEntries){
                    MyDataa myDataa = new MyDataa();
                    myDataa.setId(entry.getMovieid());
                    Log.e("Moie ID ",String.valueOf(entry.getMovieid()));
                    Log.e("Moie BacD ",String.valueOf(entry.getBackdrop_path()));
                    myDataa.setOverview(entry.getOverview());
                    myDataa.setTitle(entry.getTitle());
                    myDataa.setPoster_path(entry.getPosterpath());
                    myDataa.setRelease_date(entry.getRelease_date());
                    myDataa.setPoster_path("f"+entry.getPosterpath());
                    myDataa.setVote_average(entry.getVote_average());
                    myDataa.setBackdrop_path(entry.getBackdrop_path());
                    myDataas.add(myDataa);
                    adapter.notifyDataSetChanged();
                }

                //adapter.setMyDataas(myDataas);
            }
        });
    }
}
