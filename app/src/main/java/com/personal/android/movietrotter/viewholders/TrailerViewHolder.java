package com.personal.android.movietrotter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.personal.android.movietrotter.R;

/**
 * Created by Hemangini on 3/22/17.
 */

public class TrailerViewHolder extends BaseViewHolder implements View.OnClickListener{

    public TextView titleTextView;
    public OnItemClickedListener onItemClickedListener;

    public TrailerViewHolder(View itemView, OnItemClickedListener onItemClickedListener) {
        super(itemView);
        this.onItemClickedListener = onItemClickedListener;
        itemView.setOnClickListener(this);

        titleTextView = (TextView) itemView.findViewById(R.id.trailer_title);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickedListener != null)
            onItemClickedListener.onItemClicked(getAdapterPosition());
    }
}
