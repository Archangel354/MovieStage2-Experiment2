package com.example.android.moviestage2;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.moviestage2.Utils.movies;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieList>> {

    private static final int MOVIELIST_LOADER_ID = 1;

    /** Adapter for the gridview of movies */
    private MovieAdapter mAdapter;
    private ArrayList arrayList;

    /** Adapter for the gridview of personal favorite movies */
    private FavoriteCursorAdapter favoriteCursorAdapter;
    private ArrayList favoritesList;

    private static final String TAG = MainActivity.class.getSimpleName();

    public final static String POPULARSTRING = "https://api.themoviedb.org/3/movie/popular?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public final static String TOPRATEDSTRING = "https://api.themoviedb.org/3/movie/top_rated?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public String urlPosterString = POPULARSTRING;
    private boolean firstTimeRunFlag = true;

    public final static String TRAILERSTRING = "https://api.themoviedb.org/3/movie/335984/videos?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public final static String VIDEOKEY ="dZOaI_Fn5o4";
    public final static String VIDEOURL ="https://www.youtube.com/watch?v=gCcx85zbxz4";

    // Find a reference to the {@link GridView} in the layout
    public GridView movieGridView;
    public GridView favoriteGridView;
    private String urlImageBaseString = "https://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieGridView = (GridView) findViewById(R.id.movieGrid);
        favoriteGridView = (GridView) findViewById(R.id.movieGrid);
// Find and set empty view on the GridView, so that it only shows when the grid has 0 movies.
        View emptyView = findViewById(R.id.empty_view);
        favoriteGridView.setEmptyView(emptyView);

        // Create a new adapter that takes an empty list of movies as input
        mAdapter = new MovieAdapter(this, new ArrayList<MovieList>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        movieGridView.setAdapter(mAdapter);

// Nullpointer exception 11-5-17        favoriteGridView.setAdapter(favoriteCursorAdapter);
        favoriteCursorAdapter = new FavoriteCursorAdapter(this, null);
        favoriteGridView.setAdapter(favoriteCursorAdapter);

        Spinner mSpinner = (Spinner) findViewById(R.id.spnPopOrRatedOrFavorite);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this,
                R.array.movie_choices, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinAdapter);

        getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, this);
        connectAndLoadMovies();

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                if ( selected.contains("Most Popular")){
                    urlPosterString = POPULARSTRING;
                    mAdapter.clear();
                    movieGridView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, MainActivity.this);

                } else if (selected.contains("Highest Rated")){
                    firstTimeRunFlag = false;
                    urlPosterString = TOPRATEDSTRING;
                    mAdapter.clear();
                    movieGridView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, MainActivity.this);

                } else if (selected.contains("Personal Favorites")){
                    firstTimeRunFlag = false;
                    Intent favoriteIntent = new Intent(MainActivity.this, FavoritesActivity.class);

                    startActivity(favoriteIntent);


//                    favoriteCursorAdapter.isEmpty();
//                    movieGridView.setAdapter(mAdapter);
//                    mAdapter.notifyDataSetChanged();
//                    getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this,"No spinner choice executed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }
        });

        // Setup the setOnItemClickListener when a movie image is clicked
        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("MBUNDLE_TITLE", movies.get(position).getmMovieTitle());
                mBundle.putString("MBUNDLE_DATE", movies.get(position).getmReleaseDate());
                mBundle.putString("MBUNDLE_VOTE", movies.get(position).getmVoteAverage());
                mBundle.putString("MBUNDLE_SYNOPSIS", movies.get(position).getmSynopsis());
                mBundle.putString("MBUNDLE_POSTER", movies.get(position).getmPosterPath());
                mBundle.putString("MBUNDLE_MOVIEID", movies.get(position).getmMovieID());
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public Loader<List<MovieList>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
        Log.i("ONCREATELOADER... ", "urlPosterString: " + urlPosterString);

            return new MovieListLoader(this, urlPosterString);
        }



    @Override
    public void onLoadFinished(Loader<List<MovieList>> loader, List<MovieList> movies) {
        // Clear the adapter of previous movie data
        mAdapter.clear();
        // If there is a valid list of books, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
            mAdapter.UpdateMovies(movies);
            mAdapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieList>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    public void connectAndLoadMovies(){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MOVIELIST_LOADER_ID, null, this);
        } else {}
    }
}

