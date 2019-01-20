package com.example.android.moviestage2;

import android.app.LoaderManager;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.moviestage2.Utils.movies;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<MovieList>>, MoviesAdapter.OnItemClickListener {

    private static final int MOVIELIST_LOADER_ID = 1;

    /** Adapter for the gridview of movies */
    private MoviesAdapter mAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();

    public final static String POPULARSTRING = "https://api.themoviedb.org/3/movie/popular?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public final static String TOPRATEDSTRING = "https://api.themoviedb.org/3/movie/top_rated?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public String urlPosterString = POPULARSTRING;
    private boolean firstTimeRunFlag = true;

    public final static String TRAILERSTRING = "https://api.themoviedb.org/3/movie/335984/videos?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public final static String VIDEOKEY ="dZOaI_Fn5o4";
    public final static String VIDEOURL ="https://www.youtube.com/watch?v=gCcx85zbxz4";

    // Find a reference to the {@link GridView} in the layout
    private RecyclerView mRecyclerView;
    private String urlImageBaseString = "https://image.tmdb.org/t/p/w185/";
    private ArrayList<MovieList> mMovieList;
    /** Query URL */
    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView =  findViewById(R.id.recycler_view);

        // Create a new adapter that takes an empty list of movies as input
        mAdapter = new MoviesAdapter(MainActivity.this, mMovieList);
        // Set the adapter on the {@link GridView} so the list can be populated in the user interface
        mRecyclerView.setAdapter(mAdapter);

        Spinner mSpinner = (Spinner) findViewById(R.id.spnPopOrRatedOrFavorite);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this,
                R.array.movie_choices, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinAdapter);

        LoaderCallbacks<List<MovieList>> callbacks = MainActivity.this;

        Bundle bundleForLoader = null;

        /*
         * Ensures a loader is initialized and active. If the loader doesn't already exist, one is
         * created and (if the activity/fragment is currently started) starts the loader. Otherwise
         * the last created loader is re-used.
         */
        getSupportLoaderManager().initLoader(MOVIELIST_LOADER_ID, bundleForLoader, callbacks);

        //connectAndLoadMovies();

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                if ( selected.contains("Most Popular")){
                    urlPosterString = POPULARSTRING;
                    mAdapter = new MoviesAdapter(MainActivity.this, new ArrayList<MovieList>());
                    mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    mRecyclerView.setAdapter(mAdapter);

                    Log.i("LOG onItemSelected... ","POPULARSTRING: " + urlPosterString);
                    Log.i("LOG onItemSelected... ","movies: " + movies);



                } else if (selected.contains("Highest Rated")){
                    firstTimeRunFlag = false;
                    urlPosterString = TOPRATEDSTRING;
                    mAdapter = new MoviesAdapter(MainActivity.this, new ArrayList<MovieList>());
                    mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    mRecyclerView.setAdapter(mAdapter);
                    Log.i("LOG onItemSelected... ","Highest Rated: " + urlPosterString);
//                    Log.i("LOG onItemSelected... ","movies: " + movies);



                } else if (selected.contains("Personal Favorites")){
                    firstTimeRunFlag = false;
                    mAdapter = new MoviesAdapter(MainActivity.this, new ArrayList<MovieList>());
                    mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Toast.makeText(MainActivity.this,"No spinner choice executed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }
        });

        // Setup the setOnItemClickListener when a movie image is clicked
//        mAdapter.setOnItemClickListener((MoviesAdapter.OnItemClickListener) MainActivity.this);
    }

    @Override
    public Loader<List<MovieList>> onCreateLoader(int id, final Bundle args) {
        return  new AsyncTaskLoader<List<MovieList>>(this) {



            /* This List will hold and help cache our movie data */
            List<MovieList> mMovieList = null;

            @Override
            protected void onStartLoading() {
                if (mMovieList != null) {
                    deliverResult(mMovieList);
                } else {
                    forceLoad();
                }
            }



            @Override
            public List<MovieList> loadInBackground() {
                Log.i("loadInBackground","mUrl is: " + mUrl);

                if (mUrl == null) {
                    Log.i("loadInBackground","mUrl is: " + mUrl);
                    return null;
                }


                // Perform the network request, parse the response, and extract a list of movies along with
                // the associated movie data i.e. title, posterpath, synopsis, etc.. .
                List<MovieList> movies = Utils.fetchMovieData(mUrl);
                Log.i("loadInBackground","movies is: " + movies);
                return movies;
            }



            /**
             * Sends the result of the load to the registered listener.
             *
             * @param movies The result of the load
             */
            public void deliverResult(List<MovieList> movies) {
                mMovieList = movies;
                super.deliverResult(movies);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<MovieList>> loader, List<MovieList> movies) {
        // Clear the adapter of previous movie data
        //mAdapter.clear();
        // If there is a valid list of books, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        mAdapter.setMovieData(movies);
        if (movies != null && !movies.isEmpty()) {
            //mAdapter.clear();
            mAdapter.notifyDataSetChanged();
            mRecyclerView.setVisibility(View.VISIBLE);

            //mAdapter.UpdateMovies(movies);
            //mAdapter.addAll(movies);

            Log.i("LOG onLoadFinished ","movies: " + movies);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieList>> loader) {
        // Loader reset, so we can clear out our existing data.
        //mAdapter.clear();
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

            LoaderCallbacks<List<MovieList>> callbacks = MainActivity.this;

            Bundle bundleForLoader = null;

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            getSupportLoaderManager().initLoader(MOVIELIST_LOADER_ID, bundleForLoader, callbacks);
        } else {}
    }

    @Override
    public void onItemClick(int position) {

    }
}