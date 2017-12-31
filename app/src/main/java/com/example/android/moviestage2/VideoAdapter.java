package com.example.android.moviestage2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 10/29/2017.
 */

public class VideoAdapter extends ArrayAdapter<VideoList> {

    private Context context;
    private LayoutInflater inflater;
    private String urlImageBaseString = "https://image.tmdb.org/t/p/w185/";
    private String completeUrlString = "";
    private List<VideoList> imageUrls = new ArrayList<>(); // so far so good 9/25/17


    public VideoAdapter(Activity context, ArrayList<VideoList> videoListRecords) {
        super(context, R.layout.trailer_list_items, videoListRecords);
    }
}
