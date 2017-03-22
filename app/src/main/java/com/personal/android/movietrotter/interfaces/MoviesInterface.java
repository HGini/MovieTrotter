package com.personal.android.movietrotter.interfaces;

import com.personal.android.movietrotter.beans.Movie;

import java.util.ArrayList;

/**
 * Created by Hemangini on 3/19/17.
 */

public interface MoviesInterface {

    void onAPISuccess(ArrayList<Movie> movies);
}
