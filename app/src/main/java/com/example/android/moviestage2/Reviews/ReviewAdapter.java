package com.example.android.moviestage2.Reviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView rTextViewAuthor;
        public TextView rTextViewContent;



        public ReviewViewHolder(View itemView) {
            super(itemView);
        }
    }
}
