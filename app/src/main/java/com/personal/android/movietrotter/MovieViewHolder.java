package com.personal.android.movietrotter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Hemangini on 3/19/17.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public MovieViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
