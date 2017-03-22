package com.personal.android.movietrotter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.personal.android.movietrotter.R;
import com.personal.android.movietrotter.beans.Movie;
import com.personal.android.movietrotter.beans.Trailer;
import com.personal.android.movietrotter.interfaces.MoviesInterface;
import com.personal.android.movietrotter.zextras.APIManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Hemangini on 3/20/17.
 */

public class DetailsActivity extends AppCompatActivity implements MoviesInterface{

    public static final String INTENT_EXTRAS_KEY_MOVIE = "KEY_MOVIE";

    private Movie movie;

    private TextView titleTextView;
    private TextView yearTextView;
    private TextView durationTextView;
    private TextView ratingsTextView;
    private TextView synopsisTextView;
    private ImageView posterImageView;
    private ImageView favoriteButton;
    private RecyclerView trailersRecyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        extractMovieData();
        initViews();
        initData();
    }

    private void extractMovieData() {
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_EXTRAS_KEY_MOVIE)) {
            movie = intent.getParcelableExtra(INTENT_EXTRAS_KEY_MOVIE);
        }
    }

    private void initViews() {
        titleTextView = (TextView) findViewById(R.id.movie_title);
        yearTextView = (TextView) findViewById(R.id.year);
        durationTextView = (TextView) findViewById(R.id.duration);
        ratingsTextView = (TextView) findViewById(R.id.rating);
        synopsisTextView = (TextView) findViewById(R.id.synopsis);
        posterImageView = (ImageView) findViewById(R.id.poster_image);
        favoriteButton = (ImageView) findViewById(R.id.fav_button);
        trailersRecyclerview = (RecyclerView) findViewById(R.id.trailers_recyclerview);
    }

    private void initData() {
        if (movie != null) {
            if (!TextUtils.isEmpty(movie.getTitle())) {
                titleTextView.setText(movie.getTitle());
            }
            if (!TextUtils.isEmpty(movie.getReleaseDate())) {
                yearTextView.setText(movie.getReleaseDate());
            }
            if (movie.getVoteAverage() > 0 && movie.getVoteCount() > 0) {
                String ratings = movie.getVoteAverage() + "/" + movie.getVoteCount();
                ratingsTextView.setText(ratings);
            }
            if (!TextUtils.isEmpty(movie.getOverview())) {
                synopsisTextView.setText(movie.getOverview());
            }
            if (!TextUtils.isEmpty(movie.getPosterPath())) {
                Picasso.with(this).load(movie.getPosterPath()).into(posterImageView);
            }
            if (movie.getId() > 0) {
                APIManager apiManager = new APIManager(this);
                apiManager.getMovieTrailersData(movie.getId(), this);
                //apiManager.getMovieReviewsData(movie.getId(), this);
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.actionbar_title_detail));
        }
    }

    @Override
    public void onMoviesAPISuccess(ArrayList<Movie> movies) {

    }

    @Override
    public void onMovieTrailersAPISuccess(ArrayList<Trailer> trailers) {

    }

    @Override
    public void onMovieReviewsAPISuccess(ArrayList<String> reviews) {

    }
}
