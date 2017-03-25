package com.personal.android.movietrotter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.personal.android.movietrotter.R;
import com.personal.android.movietrotter.beans.Review;
import com.personal.android.movietrotter.viewholders.BaseViewHolder;
import com.personal.android.movietrotter.viewholders.ReviewViewHolder;

import java.util.ArrayList;

/**
 * Created by Hemangini on 3/23/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private Context context;
    private ArrayList<Review> reviews = new ArrayList<>();

    public ReviewsAdapter(Context context) {
        this.context = context;
    }

    public void setAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        if (!TextUtils.isEmpty(review.getAuthor()))
            holder.authorTextView.setText(review.getAuthor());
        if (!TextUtils.isEmpty(review.getContent()))
            holder.contentTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
