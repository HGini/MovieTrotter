package com.personal.android.movietrotter.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.personal.android.movietrotter.R;
import com.personal.android.movietrotter.viewholders.TrailerViewHolder;
import com.personal.android.movietrotter.beans.Trailer;

import java.util.ArrayList;

/**
 * Created by Hemangini on 3/22/17.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailerViewHolder> implements TrailerViewHolder.OnItemClickedListener{

    private ArrayList<Trailer> trailers;
    private Context context;

    public TrailersAdapter(Context context) {
        this.context = context;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_trailer, parent, false);
        return new TrailerViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.titleTextView.setText(trailer.getName());
    }

    @Override
    public void onItemClicked(int position) {
        launchVideo(trailers.get(position).getVideoUrl());
    }

    private void launchVideo(String videoUrl) {
        if (!TextUtils.isEmpty(videoUrl)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
