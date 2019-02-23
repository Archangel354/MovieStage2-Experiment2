package com.example.android.moviestage2;

import android.app.LoaderManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.moviestage2.RoomData.AppExecutors;
import com.example.android.moviestage2.RoomData.MainViewModel;
import com.example.android.moviestage2.RoomData.MovieRecords;
import com.example.android.moviestage2.RoomData.MoviesDatabase;
import com.example.android.moviestage2.FavoritesAdapter;


import java.util.ArrayList;
import java.util.List;

import static com.example.android.moviestage2.Utils.movies;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieList>> {

    // Constant for logging
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MOVIELIST_LOADER_ID = 1;

    /** Adapter for the gridview of movies from the JSON data */
    private MoviesAdapter mAdapter;
    /** Adapter for the gridview of personal favorite movies from the Room database */
    private FavoritesAdapter dAdapter;

    public final static String POPULARSTRING = "https://api.themoviedb.org/3/movie/popular?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public final static String TOPRATEDSTRING = "https://api.themoviedb.org/3/movie/top_rated?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public String urlPosterString = POPULARSTRING;
    public final static String FAVORITESTRING = "";

    public final static String TRAILERSTRING = "https://api.themoviedb.org/3/movie/335984/videos?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public final static String VIDEOKEY ="dZOaI_Fn5o4";
    public final static String VIDEOURL ="https://www.youtube.com/watch?v=gCcx85zbxz4";

    // Find a reference to the {@link GridView} in the layout
    private RecyclerView mRecyclerView;
    private ArrayList<MovieList> mMovieList;
    /** Query URL */
    public String mUrl;
    public  static String spinnerSelection = POPULARSTRING;

    private MoviesDatabase mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView =  findViewById(R.id.recycler_view);

        // Create a new adapter that takes an empty list of movies as input
        mAdapter = new MoviesAdapter(MainActivity.this, mMovieList);
        mRecyclerView.setHasFixedSize(true);
        // Set the adapter on the {@link GridView} so the list can be populated in the user interface
        mRecyclerView.setAdapter(mAdapter);

        Spinner mSpinner = (Spinner) findViewById(R.id.spnPopOrRatedOrFavorite);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this,
                R.array.movie_choices, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinAdapter);
        Bundle bundleForLoader = null;

        /*
         * Ensures a loader is initialized and active. If the loader doesn't already exist, one is
         * created and (if the activity/fragment is currently started) starts the loader. Otherwise
         * the last created loader is re-used.
         */
        getLoaderManager().initLoader(MOVIELIST_LOADER_ID, bundleForLoader, this);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                if ( selected.contains("Most Popular")){
                    mUrl = POPULARSTRING;
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    mAdapter = new MoviesAdapter(MainActivity.this, new ArrayList<MovieList>());
                    mRecyclerView.setAdapter(mAdapter);
                    getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, MainActivity.this);
                    Log.i("LOG onItemSelected... ","POPULARSTRING: " + mUrl);

                } else if (selected.contains("Highest Rated")){
                    mUrl = TOPRATEDSTRING;
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    mAdapter = new MoviesAdapter(MainActivity.this, new ArrayList<MovieList>());
                    mRecyclerView.setAdapter(mAdapter);
                    getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, MainActivity.this);
                    Log.i("LOG onItemSelected... ","Highest Rated: " + mUrl);

                } else if (selected.contains("Personal Favorites")){
                    Log.i("LOG onItemSelected... ","Personal Favorites: " + urlPosterString);
                    mAdapter.clear();
                   // if (dAdapter != null){
                   //     dAdapter.clear();
                   // }
                    spinnerSelection = FAVORITESTRING;
                    mRecyclerView.setAdapter(null);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    dAdapter = new FavoritesAdapter(MainActivity.this,  new ArrayList<MovieRecords>());
                    Log.i("LOG Personal Favorites ","MovieRecords: ");

                    mRecyclerView.setAdapter(dAdapter);
                    mDb = MoviesDatabase.getInstance(getApplicationContext());
                    setupViewModel();

                } else {
                    Toast.makeText(MainActivity.this,"No spinner choice executed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }
        });
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
            Log.i("LOG onLoadFinished ","movies: " + movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieList>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<MovieRecords>>() {
            @Override
            public void onChanged(@Nullable List<MovieRecords> movieEntries) {
                Log.d(TAG, "Updating list of items from LiveData in ViewModel");
                dAdapter.setMovieFavorites(movieEntries);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu_favorites, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_all_entries) {


            deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void deleteAll(){

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    //int position = viewHolder.getAdapterPosition();
                    List<MovieRecords> favorites = dAdapter.getFavorites();
                    mDb.movieDao().nukeFavorites();
                }
            });
            Toast.makeText(MainActivity.this,"Favorites table nuked", Toast.LENGTH_SHORT).show();

    }


}