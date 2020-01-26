package com.abhishek.moviemania;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class DetailActivity extends AppCompatActivity {

    TextView movieTitle;
    TextView movieReleaseDate;
    TextView movieRating;
    TextView movieOverview;
    ImageView movieBackdrop;

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
}