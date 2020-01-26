package com.abhishek.moviemania;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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

        movieBackdrop = (ImageView) findViewById(R.id.iv_movie_poster);
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
            Log.e("Rating - ", "Rating - "+vote);

//            Glide.with(this)
//                    .load(backDrop)
//                    .into(movieBackdrop);

            movieTitle.setText(title);
            movieReleaseDate.setText(release_date);
            movieRating.setText(vote+"/10");
            movieOverview.setText(overview);

        } else {
            Toast.makeText(this, "API data is missing!", Toast.LENGTH_SHORT).show();
        }
    }
}