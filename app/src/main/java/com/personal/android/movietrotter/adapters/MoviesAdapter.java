package com.personal.android.movietrotter.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.personal.android.movietrotter.activities.DetailsActivity;
import com.personal.android.movietrotter.viewholders.BaseViewHolder;
import com.personal.android.movietrotter.zextras.APIManager;
import com.personal.android.movietrotter.viewholders.MovieViewHolder;
import com.personal.android.movietrotter.R;
import com.personal.android.movietrotter.beans.Movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Hemangini on 3/19/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> implements BaseViewHolder.OnItemClickedListener{

    private Context context;
    private ParentInterface parentInterface;
    private ArrayList<Movie> movies = new ArrayList<>();

    public MoviesAdapter(Context context, ParentInterface parentInterface) {
        this.context = context;
        this.parentInterface = parentInterface;
    }

    public void setData(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_movie, parent, false);
        return new MovieViewHolder(view, this);
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

    @Override
    public void onItemClicked(int position) {
        if (parentInterface != null)
            parentInterface.onItemClicked(position);

        Movie movie = movies.get(position);
        openMovieDetailsActivity(movie);
    }

    private void openMovieDetailsActivity(Movie movie) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(DetailsActivity.INTENT_EXTRAS_KEY_MOVIE, movie);
        context.startActivity(intent);
    }

    public interface ParentInterface {
        void onItemClicked(int position);
    }
}
