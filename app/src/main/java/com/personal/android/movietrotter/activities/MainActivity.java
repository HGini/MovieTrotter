package com.personal.android.movietrotter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.personal.android.movietrotter.beans.Trailer;
import com.personal.android.movietrotter.zextras.APIManager;
import com.personal.android.movietrotter.beans.Movie;
import com.personal.android.movietrotter.adapters.MoviesAdapter;
import com.personal.android.movietrotter.interfaces.MoviesInterface;
import com.personal.android.movietrotter.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesInterface{

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortByPopularity() {
        // Change the action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.actionbar_title_pop));
        }

        // Make the API call
        APIManager apiManager = new APIManager(this);
        apiManager.getPopularMoviesData(this);
    }

    private void sortByTopRated() {
        // Change the action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.actionbar_title_top_rated));
        }

        // Make the API call
        APIManager apiManager = new APIManager(this);
        apiManager.getTopRatedMoviesData(this);
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
    public void onMovieTrailersAPISuccess(ArrayList<Trailer> trailers) {

    }

    @Override
    public void onMovieReviewsAPISuccess(ArrayList<String> reviews) {

    }
}
