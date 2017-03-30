package com.personal.android.movietrotter.zextras;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.personal.android.movietrotter.R;
import com.personal.android.movietrotter.beans.Movie;
import com.personal.android.movietrotter.beans.Review;
import com.personal.android.movietrotter.beans.Trailer;
import com.personal.android.movietrotter.interfaces.MoviesInterface;
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
    public static final String URL_BASE_MOVIE_IMAGE = "http://image.tmdb.org/t/p/w185";
    public static final String URL_BASE_MOVIE_DETAIL =  "http://api.themoviedb.org/3/movie/";
    public static final String URL_SUFFIX_MOVIE_DETAIL_REVIEW = "/reviews";
    public static final String URL_SUFFIX_API_KEY = "?api_key=" + APIKeys.KEY_VER3_AUTH;
    private static final String RESPONSE_JSON_KEY_RESULTS = "results";

    // Others
    private Context context;

    public APIManager(Context context) {
        this.context = context;
    }

    public void getPopularMoviesData(final MoviesInterface moviesInterface) {
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
                    moviesInterface.onMoviesAPISuccess(parseMoviesData(response));
                }
            });

        }
        else {
            Toast.makeText(context, context.getString(R.string.no_network_pull_down_refresh),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void getTopRatedMoviesData(final MoviesInterface moviesInterface) {
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
                    moviesInterface.onMoviesAPISuccess(parseMoviesData(response));
                }
            });

        }
        else {
            Toast.makeText(context, context.getString(R.string.no_network_pull_down_refresh),
                    Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<Movie> parseMoviesData(Response response) {
        ArrayList<Movie> movies = new ArrayList<>();
        if (response != null) {
            try {
                JSONObject responseBody = new JSONObject(response.body().string());
                JSONArray moviesArray = responseBody.getJSONArray(RESPONSE_JSON_KEY_RESULTS);
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
        return movies;
    }

    public void getMovieReviewsData(final int movieID, final MoviesInterface moviesInterface) {
        if (Utils.isNetworkConnected(context)) {
            Request request = new Request.Builder()
                    .url(URL_BASE_MOVIE_DETAIL + movieID + URL_SUFFIX_MOVIE_DETAIL_REVIEW + URL_SUFFIX_API_KEY)
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
                    moviesInterface.onMovieReviewsAPISuccess(parseMovieReviewsData(response));
                }
            });

        }
        else {
            Toast.makeText(context, context.getString(R.string.no_network_pull_down_refresh),
                    Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<Review> parseMovieReviewsData(Response response) {
        ArrayList<Review> reviews = new ArrayList<>();
        if (response != null) {
            try {
                JSONObject body = new JSONObject(response.body().string());
                if (body.has(RESPONSE_JSON_KEY_RESULTS)) {
                    JSONArray array = body.getJSONArray(RESPONSE_JSON_KEY_RESULTS);
                    for (int i = 0; i < array.length(); i++) {
                        Review review = new Review();
                        JSONObject reviewObj = array.getJSONObject(i);
                        if (reviewObj.has(Review.API_KEY_ID))
                            review.setId(reviewObj.getString(Review.API_KEY_ID));
                        if (reviewObj.has(Review.API_KEY_AUTHOR))
                            review.setAuthor(reviewObj.getString(Review.API_KEY_AUTHOR));
                        if (reviewObj.has(Review.API_KEY_CONTENT))
                            review.setContent(reviewObj.getString(Review.API_KEY_CONTENT));
                        if (reviewObj.has(Review.API_KEY_URL))
                            review.setUrl(reviewObj.getString(Review.API_KEY_URL));
                        reviews.add(review);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return reviews;
    }
}
