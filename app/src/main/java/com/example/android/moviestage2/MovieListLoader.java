package com.example.android.moviestage2;

/**
 * Created by Owner on 10/14/2017.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class MovieListLoader extends AsyncTaskLoader<List<MovieList>> {

    /** Query URL */
    private String mUrl;

    public MovieListLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieList> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movies along with
        // the associated movie data i.e. title, posterpath, synopsis, etc.. .
        List<MovieList> movies = Utils.fetchMovieData(mUrl);
        Log.i("MOVIELISTLOADER","movies is: " + movies);
        return movies;
    }
}

