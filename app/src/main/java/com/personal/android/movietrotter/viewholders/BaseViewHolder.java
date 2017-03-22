package com.personal.android.movietrotter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Hemangini on 3/22/17.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public interface OnItemClickedListener {
        void onItemClicked(int position);
    }
}
