package com.personal.android.movietrotter;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Hemangini on 3/17/17.
 */

public class APIManager {

    // Static constants
    private static final String URL_POPULAR_MOVIES = "http://api.themoviedb.org/3/movie/popular?api_key=" + APIKeys.AUTH_KEY_VER3;
    private static final String URL_TOP_RATED_MOVIES = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + APIKeys.AUTH_KEY_VER3;

    // Others
    private Context context;

    public APIManager(Context context) {
        this.context = context;
    }

    public void getMoviesData() {
        if (Utils.isNetworkConnected(context)) {

        }
        else {
            Toast.makeText(context, context.getString(R.string.no_network_pull_down_refresh),
                    Toast.LENGTH_LONG).show();
        }
    }
}
