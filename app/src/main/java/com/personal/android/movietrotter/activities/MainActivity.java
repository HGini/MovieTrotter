package com.personal.android.movietrotter.activities;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.personal.android.movietrotter.beans.Review;
import com.personal.android.movietrotter.beans.Trailer;
import com.personal.android.movietrotter.data.MoviesDBContract;
import com.personal.android.movietrotter.zextras.APIManager;
import com.personal.android.movietrotter.beans.Movie;
import com.personal.android.movietrotter.adapters.MoviesAdapter;
import com.personal.android.movietrotter.interfaces.MoviesInterface;
import com.personal.android.movietrotter.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesInterface, LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_ID_FAVORITES = 0;

    private static final int SORT_MODE_POPULARITY = 0;
    private static final int SORT_MODE_TOP_RATED = 1;
    private static final int SORT_MODE_FAVORITES = 2;

    private Menu menu;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private int sortMode = SORT_MODE_POPULARITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        sortByPopularity();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new MoviesAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * Check if any favorites exist in db.
         * If yes, show menu option 'Sort by Favorites' and refresh the screen if sorted by favorites,
         * else, hide menu option 'Sort by Favorites' and sort by popularity (if sorted by favorites) */
        if (isFavoritesTableEmpty()) {
            disableSortByFavorites();
        } else {
            enableSortByFavorites();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.sort_by_pop:
                sortByPopularity();
                return true;

            case R.id.sort_by_top_rated:
                sortByTopRated();
                return true;

            case R.id.sort_by_fav:
                sortByFavorite();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortByPopularity() {
        // Set the sort mode
        sortMode = SORT_MODE_POPULARITY;

        // Change the action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.actionbar_title_pop));
        }

        // Make the API call
        APIManager apiManager = new APIManager(this);
        apiManager.getPopularMoviesData(this);
    }

    private void sortByTopRated() {
        // Set the sort mode
        sortMode = SORT_MODE_TOP_RATED;

        // Change the action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.actionbar_title_top_rated));
        }

        // Make the API call
        APIManager apiManager = new APIManager(this);
        apiManager.getTopRatedMoviesData(this);
    }

    private void sortByFavorite() {
        // Set the sort mode
        sortMode = SORT_MODE_FAVORITES;

        // Change the action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.actionbar_title_favorite));
        }

        // Load from db
        initLoader();
    }

    private void disableSortByFavorites() {
        if (menu != null) {
            menu.findItem(R.id.sort_by_fav).setVisible(false);
            if (sortMode == SORT_MODE_FAVORITES) {
                sortByPopularity();
            }
        }
    }

    private void enableSortByFavorites() {
        if (menu != null) {
            menu.findItem(R.id.sort_by_fav).setVisible(true);
            if (sortMode == SORT_MODE_FAVORITES) {
                sortByFavorite();
            }
        }
    }

    @Override
    public void onMoviesAPISuccess(final ArrayList<Movie> movies) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setData(movies);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onMovieReviewsAPISuccess(ArrayList<Review> reviews) {

    }

    private void initLoader() {
        if (getSupportLoaderManager().getLoader(LOADER_ID_FAVORITES) == null)
            getSupportLoaderManager().initLoader(LOADER_ID_FAVORITES, null, MainActivity.this);
        else
            getSupportLoaderManager().restartLoader(LOADER_ID_FAVORITES, null, MainActivity.this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case LOADER_ID_FAVORITES: {
                return new CursorLoader(this, MoviesDBContract.CONTENT_URI, null, null, null, null);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {

            case LOADER_ID_FAVORITES: {
                if (data != null && data.getCount() > 0) {
                    adapter.setData(parseCursor(data));
                    adapter.notifyDataSetChanged();
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private ArrayList<Movie> parseCursor(Cursor data) {
        ArrayList<Movie> movies = new ArrayList<>();
        if (data != null) {
            while (data.moveToNext()) {
                Movie movie = new Movie();
                movie.setId(data.getInt(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_MOVIE_ID)));
                movie.setVoteCount(data.getInt(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_VOTE_COUNT)));
                movie.setVoteAverage(data.getDouble(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_VOTE_AVG)));
                movie.setPopularity(data.getDouble(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_POPULARITY)));
                movie.setAdult(data.getInt(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_IS_ADULT)) == 1);
                movie.setVideo(data.getInt(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_IS_VIDEO)) == 1);
                movie.setPosterPath(data.getString(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_POSTER_PATH)));
                movie.setOverview(data.getString(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_OVERVIEW)));
                movie.setReleaseDate(data.getString(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_RELEASE_DATE)));
                movie.setGenreIDs(data.getString(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_GENRE_IDS)));
                movie.setOriginalTitle(data.getString(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_ORIGINAL_TITLE)));
                movie.setOriginalLang(data.getString(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_ORIGINAL_LANG)));
                movie.setTitle(data.getString(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_TITLE)));
                movie.setBackdropPath(data.getString(data.getColumnIndex(MoviesDBContract.MoviesEntry.COLUMN_BACKDROP_PATH)));
                movies.add(movie);
            }
        }
        return movies;
    }

    private boolean isFavoritesTableEmpty() {
        boolean isEmpty = true;
        Cursor cursor = getContentResolver().query(MoviesDBContract.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) isEmpty = false;
            cursor.close();
        }

        return isEmpty;
    }
}
