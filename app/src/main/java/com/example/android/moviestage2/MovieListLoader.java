package com.example.android.moviestage2;

/**
 * Created by Owner on 10/14/2017.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;
import android.util.Log;


public class MovieListLoader extends AsyncTaskLoader<List<MovieList>> {

    /** Query URL */
    private String mUrl;

    public MovieListLoader(Context context, String url) {
        super(context);
        Log.i("LOG MovieListLoader","mUrl is: " + mUrl);
        Log.i("LOG 10 MovieListLoader"," MovieListLoader constructor");


        mUrl = url;
        Log.i("LOG MovieListLoader","mUrl is: " + mUrl);

    }

    @Override
    protected void onStartLoading() {
        Log.i("LOG 10 MovieListLoader"," onStartLoading");

        forceLoad();
    }

    @Override
    public List<MovieList> loadInBackground() {
        Log.i("LOG 11 MovieListLoader"," loadInBackground");

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movies along with
        // the associated movie data i.e. title, posterpath, synopsis, etc.. .
        List<MovieList> movies = Utils.fetchMovieData(mUrl);
        Log.i("LOG loadInBackground","movies is: " + movies);
        Log.i("LOG loadInBackground","mUrl is: " + mUrl);


        return movies;
    }



}

