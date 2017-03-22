package com.personal.android.movietrotter.interfaces;

import com.personal.android.movietrotter.beans.Movie;
import com.personal.android.movietrotter.beans.Trailer;

import java.util.ArrayList;

/**
 * Created by Hemangini on 3/19/17.
 */

public interface MoviesInterface {

    void onMoviesAPISuccess(ArrayList<Movie> movies);
    void onMovieTrailersAPISuccess(ArrayList<Trailer> trailers);
    void onMovieReviewsAPISuccess(ArrayList<String> reviews);
}
