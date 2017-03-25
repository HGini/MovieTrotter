package com.personal.android.movietrotter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.personal.android.movietrotter.R;

/**
 * Created by Hemangini on 3/23/17.
 */

public class ReviewViewHolder extends BaseViewHolder {

    public TextView authorTextView;
    public TextView contentTextView;

    public ReviewViewHolder(View itemView) {
        super(itemView);

        authorTextView = (TextView) itemView.findViewById(R.id.author_name);
        contentTextView = (TextView) itemView.findViewById(R.id.content);
    }
}
