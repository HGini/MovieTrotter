package com.personal.android.movietrotter.interfaces;

import com.personal.android.movietrotter.beans.Movie;
import com.personal.android.movietrotter.beans.Review;
import com.personal.android.movietrotter.beans.Trailer;

import java.util.ArrayList;

/**
 * Created by Hemangini on 3/19/17.
 */

public interface MoviesInterface {

    void onMoviesAPISuccess(ArrayList<Movie> movies);
    void onMovieReviewsAPISuccess(ArrayList<Review> reviews);
    void onMovieTrailersAPISuccess(ArrayList<Trailer> trailers);
}
