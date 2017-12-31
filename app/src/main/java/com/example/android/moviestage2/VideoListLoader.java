package com.example.android.moviestage2;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by e244194 on 10/30/2017.
 */

public class VideoListLoader extends AsyncTaskLoader<List<VideoList>> {

    /** Query URL */
    private String mUrl;

    public VideoListLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<VideoList> loadInBackground() {
        if (mUrl == null) {
            //Toast.makeText(VideoListLoader.this,"mUrl is null", Toast.LENGTH_SHORT).show();
            Log.i("VideoListLoader: ", "mUrl is null");

            return null;
        }

        // Perform the network request, parse the response, and extract a list of trailer keys.
        Log.i("VideoListLoader: ", "mUrl is: " + mUrl);


        List<VideoList> trailer = TrailerUtils.fetchTrailerData(mUrl);
        return trailer;
    }
}
