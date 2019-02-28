package com.example.android.moviestage2.Reviews;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.moviestage2.MainActivity;
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
        // Set the adapter on  so the list can be populated in the user interface
        rRecyclerView.setAdapter(rAdapter);
        Bundle bundleForReviewLoader = null;
        /*
         * Ensures a loader is initialized and active. If the loader doesn't already exist, one is
         * created and (if the activity/fragment is currently started) starts the loader. Otherwise
         * the last created loader is re-used.
         */
        getLoaderManager().initLoader(REVIEWLIST_LOADER_ID, bundleForReviewLoader, this);
        rRecyclerView.setLayoutManager(new LinearLayoutManager(ReviewsActivity.this));
        rAdapter = new ReviewAdapter(ReviewsActivity.this, new ArrayList<ReviewList>());
        rRecyclerView.setAdapter(rAdapter);
        getLoaderManager().restartLoader(REVIEWLIST_LOADER_ID, null, ReviewsActivity.this);
        Intent intent = getIntent();
        Bundle reviewsUrlBundle = intent.getExtras();
//        TextView txtUrl = findViewById(R.id.txtMovieReviews);

        if (reviewsUrlBundle != null){
            rUrl = (String) reviewsUrlBundle.get("REVIEWSURL");
            Log.i("LOG ReviewsActivity :" ,rUrl);
        }
    }

    @Override
    public Loader<List<ReviewList>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<List<ReviewList>>(this) {
            /* This List will hold and help cache our movie data */
            List<ReviewList> rReviewList = null;

            @Override
            protected void onStartLoading() {
                if (rReviewList != null) {
                    deliverResult(rReviewList);
                } else {
                    forceLoad();
                }
            }


            @Override
            public List<ReviewList> loadInBackground() {
                Log.i("loadInBackground","rUrl is: " + rUrl);

                // Perform the network request, parse the response, and extract a list of movies along with
                // the associated movie data i.e. title, posterpath, synopsis, etc.. .
                List<ReviewList> reviews = ReviewUtils.fetchMovieData(rUrl);
                Log.i("loadInBackground","reviews is: " + reviews);
                return reviews;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<ReviewList>> loader, List<ReviewList> reviewLists) {
        // If there is a valid list of reviews, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        rAdapter.setReviewData(reviewLists);
        if (reviewLists != null && !reviewLists.isEmpty()) {
            //mAdapter.clear();
            rAdapter.notifyDataSetChanged();
            rRecyclerView.setVisibility(View.VISIBLE);
            Log.i("LOG onLoadFinished ","movies: " + reviewLists);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ReviewList>> loader) {
        // Loader reset, so we can clear out our existing data.
        rAdapter.clear();

    }
}
