package com.personal.android.movietrotter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.personal.android.movietrotter.R;
import com.personal.android.movietrotter.adapters.ReviewsAdapter;
import com.personal.android.movietrotter.adapters.TrailersAdapter;
import com.personal.android.movietrotter.beans.Movie;
import com.personal.android.movietrotter.beans.Review;
import com.personal.android.movietrotter.beans.Trailer;
import com.personal.android.movietrotter.interfaces.MoviesInterface;
import com.personal.android.movietrotter.zextras.APIManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Hemangini on 3/20/17.
 */

public class DetailsActivity extends AppCompatActivity {

    public static final String INTENT_EXTRAS_KEY_MOVIE = "KEY_MOVIE";

    private TextView titleTextView;
    private TextView yearTextView;
    private TextView durationTextView;
    private TextView ratingsTextView;
    private TextView synopsisTextView;
    private ImageView posterImageView;
    private TextView favoriteButton;
    private RecyclerView reviewsRecyclerview;
    private RecyclerView trailersRecyclerview;

    private Movie movie;
    private ReviewsAdapter reviewsAdapter;
    private TrailersAdapter trailersAdapter;

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
        favoriteButton = (TextView) findViewById(R.id.fav_button);
        trailersRecyclerview = (RecyclerView) findViewById(R.id.trailers_recyclerview);
        trailersRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        reviewsRecyclerview = (RecyclerView) findViewById(R.id.reviews_recyclerview);
        reviewsRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    private void initData() {
        if (movie != null) {
            if (!TextUtils.isEmpty(movie.getTitle())) {
                titleTextView.setText(movie.getTitle());
            }
            if (!TextUtils.isEmpty(movie.getReleaseDate())) {
                String[] array = movie.getReleaseDate().split("-");
                yearTextView.setText(array[0]);
            }
            if (movie.getVoteAverage() > 0) {
                String ratings = movie.getVoteAverage() + "/10";
                ratingsTextView.setText(ratings);
            }
            if (!TextUtils.isEmpty(movie.getOverview())) {
                synopsisTextView.setText(movie.getOverview());
            }
            if (!TextUtils.isEmpty(movie.getPosterPath())) {
                Picasso.with(this).load(APIManager.URL_BASE_MOVIE_IMAGE + movie.getPosterPath()).into(posterImageView);
            }
            if (movie.getId() > 0) {
                APIManager apiManager = new APIManager(this);
                apiManager.getMovieTrailersData(movie.getId(), trailersInterface);
                apiManager.getMovieReviewsData(movie.getId(), reviewsInterface);
            }
        }

        if (getActionBar() != null) {
            getActionBar().setTitle(getString(R.string.actionbar_title_detail));
        }

        trailersAdapter = new TrailersAdapter(this);
        trailersRecyclerview.setAdapter(trailersAdapter);
        reviewsAdapter = new ReviewsAdapter(this);
        reviewsRecyclerview.setAdapter(reviewsAdapter);
    }

    private MoviesInterface reviewsInterface = new MoviesInterface() {
        @Override
        public void onMoviesAPISuccess(ArrayList<Movie> movies) {

        }

        @Override
        public void onMovieTrailersAPISuccess(ArrayList<Trailer> trailers) {

        }

        @Override
        public void onMovieReviewsAPISuccess(final ArrayList<Review> reviews) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (reviews != null) {
                        reviewsAdapter.setAdapter(reviews);
                        reviewsAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    };

    private MoviesInterface trailersInterface = new MoviesInterface() {
        @Override
        public void onMoviesAPISuccess(ArrayList<Movie> movies) {

        }

        @Override
        public void onMovieTrailersAPISuccess(final ArrayList<Trailer> trailers) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (trailers != null) {
                        trailersAdapter.setData(trailers);
                        trailersAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public void onMovieReviewsAPISuccess(ArrayList<Review> reviews) {

        }
    };
}
