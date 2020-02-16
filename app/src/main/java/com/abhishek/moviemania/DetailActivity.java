package com.abhishek.moviemania;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.moviemania.API.ApiClient;
import com.abhishek.moviemania.API.ApiInterface;
import com.abhishek.moviemania.ViewModel.AppExecutors;
import com.abhishek.moviemania.data.FavoriteDbHelper;
import com.abhishek.moviemania.database.AppDatabase;
import com.abhishek.moviemania.database.FavoriteEntry;
import com.abhishek.moviemania.model.MyDataa;
import com.abhishek.moviemania.model.Review;
import com.abhishek.moviemania.model.ReviewAdapter;
import com.abhishek.moviemania.model.ReviewResponse;
import com.abhishek.moviemania.model.Trailer;
import com.abhishek.moviemania.model.TrailerAdapter;
import com.abhishek.moviemania.model.TrailerResponse;
import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.abhishek.moviemania.DashboardActivity.API_KEY;

public class DetailActivity extends AppCompatActivity {

    TextView movieTitle;
    TextView movieReleaseDate;
    TextView movieRating;
    TextView movieOverview;
    ImageView movieBackdrop;

    private RecyclerView recyclerView;
    private TrailerAdapter adapter;
    private List<Trailer> trailerList;

    private RecyclerView recyclerView1;
    private ReviewAdapter adapter1;
    private List<Review> reviewList;


    private FavoriteDbHelper favoriteDbHelper;
    private MyDataa favorite;
    private final AppCompatActivity activity = DetailActivity.this;

    private AppDatabase mDb;
    List<FavoriteEntry> entries = new ArrayList<>();
    boolean exists;

    MyDataa movie;
    String thumbnail, movieName, synopsis, rating, dateOfRelease;
    int movie_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FavoriteDbHelper dbHelper = new FavoriteDbHelper(this);
        mDb = AppDatabase.getInstance(getApplicationContext());

        movieBackdrop = findViewById(R.id.iv_moviePoster);
        movieTitle = findViewById(R.id.tv_movieNameValue);
        movieReleaseDate = findViewById(R.id.tv_movieReleaseValue);
        movieRating = findViewById(R.id.tv_movieRatingValue);
        movieOverview = findViewById(R.id.tv_plotValue);

        Intent intentThisActivity = getIntent();
        if (intentThisActivity.hasExtra("title")) {

            String backDrop = getIntent().getExtras().getString("backdrop_path");
            String title = getIntent().getExtras().getString("title");
            String overview = getIntent().getExtras().getString("overview");
            String vote = getIntent().getExtras().getString("vote_average");
            String release_date = getIntent().getExtras().getString("release_date");

            String poster = "https://image.tmdb.org/t/p/w500" + backDrop;

            Glide.with(DetailActivity.this)
                    .load(poster)
                    .into(movieBackdrop);


            movieTitle.setText(title);
            movieReleaseDate.setText(release_date);
            movieRating.setText(vote + "/10");
            movieOverview.setText(overview);
            
            /* Here is we are saving details for database*/
            movieName = title;
            thumbnail = poster;
            synopsis=overview;

        } else {
            Toast.makeText(this, "API data is missing!", Toast.LENGTH_SHORT).show();
        }

        initViews();
        initViews1();
        checkStatus(movieName);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        trailerList = new ArrayList<>();
        adapter = new TrailerAdapter(this, trailerList);
        recyclerView = findViewById(R.id.rv_trailer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();
    }

    private void initViews1() {
        Log.e("In IV1", "IV1");
        reviewList = new ArrayList<>();
        adapter1 = new ReviewAdapter(this, reviewList);
        recyclerView1 = findViewById(R.id.rv_reviews);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        loadReviews();
    }

    public void loadJSON() {
        int movie_id = getIntent().getExtras().getInt("id");
        ApiInterface apiInterface = ApiClient.getApiClient().create((ApiInterface.class));
        Call<TrailerResponse> call;
        call = apiInterface.getTrailers(movie_id, API_KEY);
        call.enqueue(new Callback<TrailerResponse>() {

            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                List<Trailer> trailer = response.body().getResults();
                recyclerView.setAdapter(new TrailerAdapter(getApplicationContext(), trailer));
                recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }

    public void loadReviews() {
        Log.e("In IR", "IR");
        int movie_id = getIntent().getExtras().getInt("id");
        ApiInterface apiInterface = ApiClient.getApiClient().create((ApiInterface.class));
        Call<ReviewResponse> call;
        call = apiInterface.getReviews(movie_id, API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {

            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                List<Review> reviews = response.body().getResults();
                recyclerView1.setAdapter(new ReviewAdapter(getApplicationContext(), reviews));
                recyclerView1.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }


    public void saveFavorite() {
        final FavoriteEntry favoriteEntry = new FavoriteEntry(movie_id, movieName, thumbnail, synopsis);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteDao().insertFavorite(favoriteEntry);
            }
        });
    }

    private void deleteFavorite(final int movie_id) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteDao().deleteFavoriteWithId(movie_id);
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    private void checkStatus(final String movieName) {
        final MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.fav_button);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                entries.clear();
                entries = mDb.favoriteDao().loadAll(movieName);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (entries.size() > 0) {
                    materialFavoriteButton.setFavorite(true);
                    materialFavoriteButton.setOnFavoriteChangeListener((buttonView, favorite) -> {
                        if (favorite) {
                            saveFavorite();
                            Snackbar snackbar = Snackbar
                                    .make(buttonView, "Added to Favorite", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(Color.parseColor("#8C251D"));
                            TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                            tv.setTextColor(Color.BLACK);
                            snackbar.show();

                        } else {
                            deleteFavorite(movie_id);

                            Snackbar snackbar = Snackbar
                                    .make(buttonView, "Removed from Favorite", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(Color.parseColor("#1A237E"));
                            TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                            tv.setTextColor(Color.WHITE);
                            snackbar.show();
                        }
                    });


                } else {
                    materialFavoriteButton.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite == true) {
                                        saveFavorite();
                                        Snackbar.make(buttonView, "Added to Favorite",
                                                Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        int movie_id = getIntent().getExtras().getInt("id");
                                        deleteFavorite(movie_id);
                                        Snackbar.make(buttonView, "Removed from Favorite",
                                                Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        }.execute();
    }


}