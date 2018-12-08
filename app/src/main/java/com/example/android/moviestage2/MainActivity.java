package com.example.android.moviestage2;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.moviestage2.Utils.movies;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MovieList>> {

    private static final int MOVIELIST_LOADER_ID = 1;

    /** Adapter for the gridview of movies */
    private MovieAdapter mAdapter;
    private ArrayList arrayList;

    // New recyclerView stuff
    private RecyclerView mRecyclerView;
    private MoviesAdapter sAdapter;
    private static final String TAG = MainActivity.class.getSimpleName();

    public final static String POPULARSTRING = "https://api.themoviedb.org/3/movie/popular?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public final static String TOPRATEDSTRING = "https://api.themoviedb.org/3/movie/top_rated?api_key=02ff7187d940e5bd15cd5acd2b41b63e";
    public String urlPosterString = POPULARSTRING;
    private boolean firstTimeRunFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        Log.i("LOG before new MvieAdpr","Most Popular: " + urlPosterString);



        sAdapter = new MoviesAdapter(this, new ArrayList<MovieList>());
        mRecyclerView.setAdapter(sAdapter);

        Spinner mSpinner = (Spinner) findViewById(R.id.spnPopOrRatedOrFavorite);

        // For the spinner, Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this,
                R.array.movie_choices, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinAdapter);

        getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, this);
        connectAndLoadMovies();

        Log.i("LOG onCreate... ","movies: " + movies);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                if ( selected.contains("Most Popular")){
                    urlPosterString = POPULARSTRING;
                    // REcyclerViews stuff  mAdapter.clear();

                    getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, MainActivity.this);
                    Log.i("LOG onItemSelected... ","Most Popular: " + urlPosterString);
                    sAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(sAdapter);
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("LOG onItemSelected ","movies: " + movies);

                } else if (selected.contains("Highest Rated")){
                    firstTimeRunFlag = false;
                    urlPosterString = TOPRATEDSTRING;

                    getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, MainActivity.this);
                    Log.i("LOG onItemSelected... ","Highest Rated: " + urlPosterString);
                    sAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(sAdapter);
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("LOG onItemSelected ","movies: " + movies);

                } else if (selected.contains("Personal Favorites")){
                    firstTimeRunFlag = false;
                    mRecyclerView.setAdapter(sAdapter);
                    sAdapter.notifyDataSetChanged();
                   // getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this,"No spinner choice executed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }
        });

        // Setup the setOnItemClickListener when a movie image is clicked
//        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent mIntent = new Intent(MainActivity.this, DetailActivity.class);
//                Bundle mBundle = new Bundle();
//                mBundle.putString("MBUNDLE_TITLE", movies.get(position).getmMovieTitle());
//                mBundle.putString("MBUNDLE_DATE", movies.get(position).getmReleaseDate());
//                mBundle.putString("MBUNDLE_VOTE", movies.get(position).getmVoteAverage());
//                mBundle.putString("MBUNDLE_SYNOPSIS", movies.get(position).getmSynopsis());
//                mBundle.putString("MBUNDLE_POSTER", movies.get(position).getmPosterPath());
//                mBundle.putString("MBUNDLE_MOVIEID", movies.get(position).getmMovieID());
//                mIntent.putExtras(mBundle);
//                startActivity(mIntent);
//            }
//        });


    }

    @Override
    public Loader<List<MovieList>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        //sAdapter.clear();
        sAdapter.notifyDataSetChanged();
        Log.i("LOG ONCREATELOADER... ","urlPosterString: " + urlPosterString);
        return new MovieListLoader(this, urlPosterString);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieList>> loader, List<MovieList> movies) {
        // Clear the adapter of previous movie data
        // mAdapter.clear();
        // If there is a valid list of books, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            //mAdapter.clear();
            sAdapter.notifyDataSetChanged();
            sAdapter.UpdateMovies(movies);
            // 11-9-18 mAdapter.addAll(movies);
            Log.i("LOG onLoadFinished ","movies size: " + movies.size());
            Log.i("LOG onLoadFinished... ","movies: " + movies);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieList>> loader) {
        // Loader reset, so we can clear out our existing data.
        // 11-9-18 sAdapter.clear();
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

    public static class MovieViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public MovieViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgPosterPath);
        }
    }


    public static class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder>
    {
        private List<MovieList> mMovieList;
        private LayoutInflater mInflater;
        private Context mContext;
        private List<MovieList> imageUrls = new ArrayList<>(); // I will see if this works 11-9-2018





        public MoviesAdapter(Context context, ArrayList<MovieList> movieListRecords)
        {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
            this.mMovieList = movieListRecords;
            Log.i("LOG MoviesAdapter","The mMovieList is: " + mMovieList);
            Log.i("LOG MoviesAdapter","movies: " + movies);



        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = mInflater.inflate(R.layout.movie_list_items, parent, false);
            MovieViewHolder viewHolder = new MovieViewHolder(view);
            Log.i("LOG onCreateViewHolder","The viewHolder is: " + viewHolder);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, int position)
        {
            MovieList movie = mMovieList.get(position);

            // This is how we use Picasso to load images from the internet.
            Picasso.with(mContext)
                    .load(movie.getmPosterPath())
                    .placeholder(R.color.colorAccent)
                    .into(holder.imageView);
            Log.i("LOG onBindViewHolder","The imageView is: " + holder.imageView);

        }

        @Override
        public int getItemCount()
        {
            return (mMovieList == null) ? 0 : mMovieList.size();
        }

        public void setMovieList(List<MovieList> movieList)
        {
            this.mMovieList.clear();
            this.mMovieList.addAll(movieList);
            // The adapter needs to know that the data has changed. If we don't call this, app will crash.
            notifyDataSetChanged();
        }

        // so far so good 9/25/17
        public void UpdateMovies(List<MovieList> newList){
            this.imageUrls = newList;
            notifyDataSetChanged();
        }


    }
}