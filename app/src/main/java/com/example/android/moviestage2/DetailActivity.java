package com.example.android.moviestage2;

import android.app.Activity;
import android.app.LoaderManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.moviestage2.RoomData.AppExecutors;
import com.example.android.moviestage2.RoomData.MovieRecords;
import com.example.android.moviestage2.RoomData.MoviesDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private ToggleButton toggleFavorite;
    private TextView mMovieIDDisplay;
    private final Activity mActivity = this;
    private static final int VIDEOLIST_LOADER_ID = 2;
    private final static String TRAILERPREFIX = "https://www.youtube.com/watch?v=";
    private final static String MOVIEPREFIX = "https://api.themoviedb.org/3/movie/";
    private final static String MOVIESUFFIX = "/videos?api_key=02ff7187d940e5bd15cd5acd2b41b63e";

    /** Adapter for the list of trailers */
    private VideoAdapter vAdapter;

    public final static String VIDEOSTRING = "https://api.themoviedb.org/3/movie/335984/videos?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public String urlTrailerString = VIDEOSTRING;

       // Member variable for the MoviesDatabase
       private MoviesDatabase mDb;
       private static final int DEFAULT_ITEM_ID = -1;

       private int mItemID = DEFAULT_ITEM_ID;

       // Extra for the item ID to be received in the intent
       public static final String EXTRA_ITEM_ID = "extraItemId";
       // Extra for the item ID to be received after rotation
       public static final String INSTANCE_ITEM_ID = "instanceItemId";


       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieDisplay =  findViewById(R.id.txtTitle);
        mDateDisplay =  findViewById(R.id.txtReleaseDate);
        mVoteDisplay =  findViewById(R.id.txtVoteAverage);
        mSynopsisDisplay =  findViewById(R.id.txtSynopsis);
        mPosterDisplay =  findViewById(R.id.txtPoster);
        mMovieIDDisplay =  findViewById(R.id.txtMovieID);

           mDb = MoviesDatabase.getInstance(getApplicationContext());

           if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_ITEM_ID)) {
               mItemID = savedInstanceState.getInt(INSTANCE_ITEM_ID, DEFAULT_ITEM_ID);
           }

        Intent intentThatStartedThisActivity = getIntent();
        final Bundle mBundle = intentThatStartedThisActivity.getExtras();
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

        btnTrailer = findViewById(R.id.btnTrailer);
        toggleFavorite = findViewById(R.id.toggleFavorite);

        // Create a 2nd adapter that takes an empty list of trailers as input
        vAdapter = new VideoAdapter(DetailActivity.this, new ArrayList<VideoList>());

        ImageView imageView =  findViewById(R.id.imgPoster);
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

        toggleFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleFavorite.isChecked()){

                    onSaveButtonClicked(mBundle);
                }
                else {

                    Toast.makeText(DetailActivity.this,"My favorites deselected", Toast.LENGTH_SHORT).show();
                }
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
        * onSaveButtonClicked is called when the "star" button is clicked.
        * It retrieves user input and inserts that new item data into the movies database.
        */
       public void onSaveButtonClicked(Bundle bundle) {
           String movieID = bundle.getString("MBUNDLE_MOVIEID");
           String movieTitle = bundle.getString("MBUNDLE_TITLE");
           String releasedate = bundle.getString("MBUNDLE_DATE");
           String  voteaverage = bundle.getString("MBUNDLE_VOTE");
           String  synopsis = bundle.getString("MBUNDLE_SYNOPSIS");
           String  posterpath = bundle.getString("MBUNDLE_POSTER");

           Toast.makeText(DetailActivity.this,"star clicked " + movieID + " " + movieTitle, Toast.LENGTH_SHORT).show();

           final MovieRecords favorite = new MovieRecords(movieID, movieTitle, releasedate, voteaverage, synopsis, posterpath);

           AppExecutors.getInstance().diskIO().execute(new Runnable() {
               @Override
              public void run() {
                   if (mItemID == DEFAULT_ITEM_ID) {
                       // insert new task
                      mDb.movieDao().insertItem(favorite);
                   } else {
                       //Toast.makeText(DetailActivity.this,"Movie already added to favorites", Toast.LENGTH_SHORT).show();

                   }
               }
           });

       }

}

