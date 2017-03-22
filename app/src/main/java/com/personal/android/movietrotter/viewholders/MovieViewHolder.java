package com.personal.android.movietrotter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.personal.android.movietrotter.R;

/**
 * Created by Hemangini on 3/19/17.
 */

public class MovieViewHolder extends BaseViewHolder implements View.OnClickListener{

    public ImageView imageView;
    public OnItemClickedListener onItemClickedListener;

    public MovieViewHolder(View itemView, OnItemClickedListener onItemClickedListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.onItemClickedListener = onItemClickedListener;

        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickedListener != null)
            onItemClickedListener.onItemClicked(getAdapterPosition());
    }
}
