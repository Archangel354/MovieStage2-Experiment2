package com.example.android.moviestage2.Reviews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.moviestage2.R;


public class ReviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Intent intent = getIntent();
        Bundle reviewsUrlBundle = intent.getExtras();
        TextView txtUrl = findViewById(R.id.txtMovieReviews);

        if (reviewsUrlBundle != null){
            String reviewsUrlString = (String) reviewsUrlBundle.get("REVIEWSURL");
            txtUrl.setText(reviewsUrlString);
        }
    }
}
