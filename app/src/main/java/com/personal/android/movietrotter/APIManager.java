package com.personal.android.movietrotter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hemangini on 3/17/17.
 */

public class APIManager {

    // Static constants
    private static final String TAG = APIManager.class.getSimpleName();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String URL_POPULAR_MOVIES = "http://api.themoviedb.org/3/movie/popular?api_key=" + APIKeys.KEY_VER3_AUTH;
    private static final String URL_TOP_RATED_MOVIES = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + APIKeys.KEY_VER3_AUTH;
    private static final String RESPONSE_JSON_KEY_PAGE = "page";
    private static final String RESPONSE_JSON_KEY_RESULTS = "results";
    private static final String RESPONSE_JSON_KEY_TOTAL_RESULTS = "total_results";
    private static final String RESPONSE_JSON_KEY_TOTAL_PAGES = "total_pages";

    // Others
    private Context context;

    public APIManager(Context context) {
        this.context = context;
    }

    public void getPopularMoviesData() {
        if (Utils.isNetworkConnected(context)) {
            Request request = new Request.Builder()
                    .url(URL_POPULAR_MOVIES)
                    .get()
                    .build();
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.e(TAG, "getPopularMoviesData: onFailure");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Log.v(TAG, "getPopularMoviesData: onResponse");
                    parseAndSaveMoviesData(response);
                }
            });

        }
        else {
            Toast.makeText(context, context.getString(R.string.no_network_pull_down_refresh),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void getTopRatedMoviesData() {
        if (Utils.isNetworkConnected(context)) {
            Request request = new Request.Builder()
                    .url(URL_TOP_RATED_MOVIES)
                    .get()
                    .build();
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.e(TAG, "getPopularMoviesData: onFailure");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Log.v(TAG, "getPopularMoviesData: onResponse");
                    parseAndSaveMoviesData(response);
                }
            });

        }
        else {
            Toast.makeText(context, context.getString(R.string.no_network_pull_down_refresh),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void parseAndSaveMoviesData(Response response) {
        if (response != null) {
            try {
                JSONObject responseBody = new JSONObject(response.body().string());
                JSONArray moviesArray = responseBody.getJSONArray(RESPONSE_JSON_KEY_RESULTS);
                ArrayList<Movie> movies = new ArrayList<>();
                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movieObj = moviesArray.getJSONObject(i);
                    Movie movie = new Movie();
                    if (movieObj.has(Movie.JSON_KEY_ID))
                        movie.setId(movieObj.getInt(Movie.JSON_KEY_ID));
                    if (movieObj.has(Movie.JSON_KEY_ADULT))
                        movie.setAdult(movieObj.getBoolean(Movie.JSON_KEY_ADULT));
                    if (movieObj.has(Movie.JSON_KEY_BACKDROP_PATH))
                        movie.setBackdropPath(movieObj.getString(Movie.JSON_KEY_BACKDROP_PATH));
                    if (movieObj.has(Movie.JSON_KEY_GENRE_IDS))
                        movie.setGenreIDs(movieObj.getString(Movie.JSON_KEY_GENRE_IDS));
                    if (movieObj.has(Movie.JSON_KEY_ORIGINAL_LANGUAGE))
                        movie.setOriginalLang(movieObj.getString(Movie.JSON_KEY_ORIGINAL_LANGUAGE));
                    if (movieObj.has(Movie.JSON_KEY_ORIGINAL_TITLE))
                        movie.setOriginalTitle(movieObj.getString(Movie.JSON_KEY_ORIGINAL_TITLE));
                    if (movieObj.has(Movie.JSON_KEY_OVERVIEW))
                        movie.setOverview(movieObj.getString(Movie.JSON_KEY_OVERVIEW));
                    if (movieObj.has(Movie.JSON_KEY_POSTER_PATH))
                        movie.setPosterPath(movieObj.getString(Movie.JSON_KEY_POSTER_PATH));
                    if (movieObj.has(Movie.JSON_KEY_POPULARITY))
                        movie.setPopularity(movieObj.getDouble(Movie.JSON_KEY_POPULARITY));
                    if (movieObj.has(Movie.JSON_KEY_RELEASE_DATE))
                        movie.setReleaseDate(movieObj.getString(Movie.JSON_KEY_RELEASE_DATE));
                    if (movieObj.has(Movie.JSON_KEY_VIDEO))
                        movie.setVideo(movieObj.getBoolean(Movie.JSON_KEY_VIDEO));
                    if (movieObj.has(Movie.JSON_KEY_VOTE_AVERAGE))
                        movie.setVoteAverage(movieObj.getDouble(Movie.JSON_KEY_VOTE_AVERAGE));
                    if (movieObj.has(Movie.JSON_KEY_VOTE_COUNT))
                        movie.setVoteCount(movieObj.getInt(Movie.JSON_KEY_VOTE_COUNT));
                    if (movieObj.has(Movie.JSON_KEY_TITLE))
                        movie.setTitle(movieObj.getString(Movie.JSON_KEY_TITLE));
                    movies.add(movie);
                }

            } catch (JSONException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
