package com.example.android.moviestage2;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviestage2.data.MovieContract.MovieEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.app.ShareCompat;



//public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<VideoList>>{
   public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<VideoList>>{

    private static final String MOVIES_SHARE_HASHTAG = " #MoviesStage1App";
    private String mMovies;
    private TextView mMovieDisplay;
    private TextView mDateDisplay;
    private TextView mVoteDisplay;
    private TextView mSynopsisDisplay;
    private TextView mPosterDisplay;
    private Context context;
    private Button btnTrailer;
    private TextView mMovieIDDisplay;
    private final Activity mActivity = this;
    private static final int VIDEOLIST_LOADER_ID = 2;
    private final static String TRAILERPREFIX = "https://www.youtube.com/watch?v=";
    private final static String MOVIEPREFIX = "https://api.themoviedb.org/3/movie/";
    private final static String MOVIESUFFIX = "/videos?api_key=02ff7187d940e5bd15cd5acd2b41b63e";

    /** Adapter for the list of trailers */
    private VideoAdapter vAdapter;
    private ArrayList arrayList;

    public final static String VIDEOSTRING = "https://api.themoviedb.org/3/movie/335984/videos?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public String urlTrailerString = VIDEOSTRING;

    private ImageButton imgbtnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieDisplay = (TextView) findViewById(R.id.txtTitle);
        mDateDisplay = (TextView) findViewById(R.id.txtReleaseDate);
        mVoteDisplay = (TextView) findViewById(R.id.txtVoteAverage);
        mSynopsisDisplay = (TextView) findViewById(R.id.txtSynopsis);
        mPosterDisplay = (TextView) findViewById(R.id.txtPoster);
        mMovieIDDisplay = (TextView) findViewById(R.id.txtMovieID);

        Intent intentThatStartedThisActivity = getIntent();
        Bundle mBundle = intentThatStartedThisActivity.getExtras();
        final String mTitle = mBundle.getString("MBUNDLE_TITLE");
        mMovieDisplay.setText(mTitle);
        String mDate = mBundle.getString("MBUNDLE_DATE");
        mDateDisplay.setText(mDate);
        String mVote = mBundle.getString("MBUNDLE_VOTE");
        mVoteDisplay.setText(mVote);
        String mSynopsis = mBundle.getString("MBUNDLE_SYNOPSIS");
        mSynopsisDisplay.setText(mSynopsis);
        String mPoster = mBundle.getString("MBUNDLE_POSTER");
        final String mMovieID = mBundle.getString("MBUNDLE_MOVIEID");

        urlTrailerString = MOVIEPREFIX + mMovieID + MOVIESUFFIX;

        btnTrailer = (Button) findViewById(R.id.btnTrailer);
        imgbtnFavorite = (ImageButton) findViewById(R.id.imgbtnFavorite);

        // Create a 2nd adapter that takes an empty list of trailers as input
        vAdapter = new VideoAdapter(DetailActivity.this, new ArrayList<VideoList>());

        //TextView txtPosterView = (TextView) convertView.findViewById(R.id.txtPoster);
        ImageView imageView = (ImageView) findViewById(R.id.imgPoster);
        imageView.setAdjustViewBounds(true);

        // Use the Picasso software tool to display URLs
        Picasso
                .with(context)
                .load(mPoster)
                .fit()
                .into(imageView);

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mMovies = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                mMovieDisplay.setText(mTitle);
            }
        }

        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Display the URL to get the trailer IDs for each movie
                vAdapter.clear();
                vAdapter.notifyDataSetChanged();
                getLoaderManager().restartLoader(VIDEOLIST_LOADER_ID, null, DetailActivity.this);
            }
        });

        imgbtnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this,"Movie title is: " + mTitle, Toast.LENGTH_SHORT).show();
                // Save product to database
                saveProduct();
            }
        });
    }


    private Intent createShareForecastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mMovies + MOVIES_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForecastIntent());
        return true;
    }

    @Override
    public Loader<List<VideoList>> onCreateLoader(int i, Bundle args) {
        Log.i("ONCREATELOADERVIDEO... ","urlPosterString: " );
        return new VideoListLoader(this, urlTrailerString);
    }

    @Override
    public void onLoadFinished(Loader<List<VideoList>> loader, List<VideoList> trailer) {

        Log.i("onLoadFinished... ","trailer: " + trailer);
        // Play youtube trailer for the movie
        String mKey = trailer.get(0).getmTrailerKey();
        Log.i("onLoadFinished... ","trailer key: " + TRAILERPREFIX + mKey);
        mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(TRAILERPREFIX + mKey)));
    }

    @Override
    public void onLoaderReset(Loader<List<VideoList>> loader) {    }

    /**
     * Once user clicks on star, saves all the particular movie info into database.
     */
    private void saveProduct() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String titleString = mMovieDisplay.getText().toString().trim();
        String dateString = mDateDisplay.getText().toString().trim();
        String voteString = mVoteDisplay.getText().toString().trim();
        String synopsisString = mSynopsisDisplay.getText().toString().trim();
//        String posterString = mPosterDisplay.getText().toString().trim();
//        String movieIDString = mMovieIDDisplay.getText().toString().trim();


        // Check if this is supposed to be a new favorite moview
        // and check if all the fields in the editor are blank
        if (
                TextUtils.isEmpty(titleString) && TextUtils.isEmpty(dateString) &&
                TextUtils.isEmpty(voteString)  && TextUtils.isEmpty(synopsisString)) {
            // Since no fields were modified, we can return early without creating a new favorite movie.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and the favorite movie attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(MovieEntry.COLUMN_MOVIE_TITLE, titleString);
        values.put(MovieEntry.COLUMN_MOVIE_RELEASE_DATE, dateString);
        values.put(MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, voteString);
        values.put(MovieEntry.COLUMN_MOVIE_SYNOPSIS, synopsisString);
       // values.put(MovieEntry.COLUMN_MOVIE_POSTER_IMAGE, posterString);
        //values.put(MovieEntry.COLUMN_MOVIE_ID, movieIDString);


        // Determine if this is a new or existing product by checking if mCurrentProductUri is null or not
//        if (mCurrentProductUri == null) {
//            // This is a NEW product, so insert a new product into the provider,
//            // returning the content URI for the new product.
//            Uri newUri = getContentResolver().insert(MovieEntry.CONTENT_URI, values);
//
//            // Show a toast message depending on whether or not the insertion was successful.
//            if (newUri == null) {
//                // If the new content URI is null, then there was an error with insertion.
//                Toast.makeText(this, getString(R.string.editor_insert_movie_failed),
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                // Otherwise, the insertion was successful and we can display a toast.
//                Toast.makeText(this, getString(R.string.editor_insert_movie_successful),
//                        Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            // Otherwise this is an EXISTING product, so update the product with content URI: mCurrentProductUri
//            // and pass in the new ContentValues. Pass in null for the selection and selection args
//            // because mCurrentProductUri will already identify the correct row in the database that
//            // we want to modify.
//            int rowsAffected = getContentResolver().update(mCurrentProductUri, values, null, null);
//
//            // Show a toast message depending on whether or not the update was successful.
//            if (rowsAffected == 0) {
//                // If no rows were affected, then there was an error with the update.
//                Toast.makeText(this, getString(R.string.editor_update_product_failed),
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                // Otherwise, the update was successful and we can display a toast.
//                Toast.makeText(this, getString(R.string.editor_update_product_successful),
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
    }


}

