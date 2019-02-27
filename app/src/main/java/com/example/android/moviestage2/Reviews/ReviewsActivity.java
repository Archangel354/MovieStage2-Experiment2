package com.example.android.moviestage2.Reviews;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.moviestage2.MovieList;
import com.example.android.moviestage2.R;

import java.util.ArrayList;
import java.util.List;


public class ReviewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ReviewList>> {

    // Constant for logging
    private static final String TAG = ReviewsActivity.class.getSimpleName();
    private static final int REVIEWLIST_LOADER_ID = 2;

    /** Adapter for the list of reviews from the JSON data */
    private ReviewAdapter rAdapter;

    // Find a reference to the in the layout
    private RecyclerView rRecyclerView;
    private ArrayList<ReviewList> rReviewList;

    /** Query URL */
    public String rUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        rRecyclerView =  findViewById(R.id.recycler_reviewView);

        // Create a new adapter that takes an empty list of movies as input
        rAdapter = new ReviewAdapter(ReviewsActivity.this, rReviewList);
        rRecyclerView.setHasFixedSize(true);
        // Set the adapter on the {@link GridView} so the list can be populated in the user interface
        rRecyclerView.setAdapter(rAdapter);








//        Intent intent = getIntent();
//        Bundle reviewsUrlBundle = intent.getExtras();
//        TextView txtUrl = findViewById(R.id.txtMovieReviews);
//
//        if (reviewsUrlBundle != null){
//            String reviewsUrlString = (String) reviewsUrlBundle.get("REVIEWSURL");
//            txtUrl.setText(reviewsUrlString);
//            Log.i("LOG ReviewsActivity: " ,reviewsUrlString);
//        }
    }

    @Override
    public Loader<List<ReviewList>> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<ReviewList>> loader, List<ReviewList> reviewLists) {

    }

    @Override
    public void onLoaderReset(Loader<List<ReviewList>> loader) {

    }
}
