package com.example.android.moviestage2.Reviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.android.moviestage2.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    public static List<ReviewList> rReviewList;
    private static final String TAG = "ReviewAdapter";
    private Context rContext;

    public ReviewAdapter(Context context, ArrayList<ReviewList> reviewlist) {
        rContext = context;
        rReviewList = reviewlist;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(rContext).inflate(R.layout.review_list_items, parent, false);
        return new ReviewAdapter.ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        // Determine the values of the wanted data
        final ReviewList currentReview = rReviewList.get(position);
        final String author = currentReview.getrAuthor();
        final String content = currentReview.getrContent();

        //Set values
        holder.rTextViewAuthor.setText(author);
        holder.rTextViewContent.setText(content);
    }

    @Override
    public int getItemCount() {
        if (rReviewList == null) {
            return 0;
        }
        return rReviewList.size();
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView rTextViewAuthor;
        public TextView rTextViewContent;
        RelativeLayout rReviewLayout;

        public ReviewViewHolder(View reviewView) {
            super(reviewView);
            rTextViewAuthor = reviewView.findViewById(R.id.txtAuthor);
            rTextViewContent = reviewView.findViewById(R.id.txtContent);
            rReviewLayout = reviewView.findViewById(R.id.review_layout);
        }
    }

    public void setReviewData(List<ReviewList> reviewData){
        rReviewList = reviewData;
        notifyDataSetChanged();
    }

    public final void clear() {
        final int size = rReviewList.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        rReviewList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }
}
