package com.example.android.moviestage2;

/**
 * Created by Owner on 10/14/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Owner on 9/27/2017.
 */

public class MovieCursorAdapter extends CursorAdapter {


    // The default constructor for this CursorAdapter
    public MovieCursorAdapter(Context context, Cursor c) { super(context, c, 0);  }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a grid item view using the layout specified in movie_list_items.xml
        return LayoutInflater.from(context).inflate(R.layout.movie_list_items, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}

