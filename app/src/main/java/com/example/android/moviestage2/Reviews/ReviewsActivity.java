package com.example.android.moviestage2.Reviews;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.moviestage2.MovieList;
import com.example.android.moviestage2.R;

import java.util.List;


public class ReviewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ReviewList>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Intent intent = getIntent();
        Bundle reviewsUrlBundle = intent.getExtras();
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
