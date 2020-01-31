package com.abhishek.moviemania;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.moviemania.API.ApiClient;
import com.abhishek.moviemania.API.ApiInterface;
import com.abhishek.moviemania.model.Result;
import com.abhishek.moviemania.model.Trailer;
import com.abhishek.moviemania.model.TrailerAdapter;
import com.abhishek.moviemania.model.TrailerResponse;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieBackdrop = (ImageView) findViewById(R.id.iv_moviePoster);
        movieTitle = (TextView) findViewById(R.id.tv_movieNameValue);
        movieReleaseDate = (TextView) findViewById(R.id.tv_movieReleaseValue);
        movieRating = (TextView) findViewById(R.id.tv_movieRatingValue);
        movieOverview = (TextView) findViewById(R.id.tv_plotValue);

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
            movieRating.setText(vote+"/10");
            movieOverview.setText(overview);

        } else {
            Toast.makeText(this, "API data is missing!", Toast.LENGTH_SHORT).show();
        }

        initViews();

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

    private void initViews(){
        trailerList = new ArrayList<>();
        adapter = new TrailerAdapter(this, trailerList);

        recyclerView = (RecyclerView) findViewById(R.id.rv_trailer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();

    }

    public void loadJSON(){
        int movie_id = getIntent().getExtras().getInt("id");
        ApiInterface apiInterface = ApiClient.getApiClient().create((ApiInterface.class));
        Call<TrailerResponse> call;
        call = apiInterface.getTrailers(movie_id,API_KEY);
        call.enqueue(new Callback<TrailerResponse>() {

            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                List<Trailer> trailer = response.body().getResults();
                recyclerView.setAdapter(new TrailerAdapter(getApplicationContext(), trailer));
                recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

}