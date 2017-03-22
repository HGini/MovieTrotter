package com.personal.android.movietrotter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.personal.android.movietrotter.zextras.APIManager;
import com.personal.android.movietrotter.viewholders.MovieViewHolder;
import com.personal.android.movietrotter.R;
import com.personal.android.movietrotter.beans.Movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Hemangini on 3/19/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movies = new ArrayList<>();

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.with(context).load(APIManager.URL_BASE_MOVIE_IMAGE + movie.getPosterPath()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
