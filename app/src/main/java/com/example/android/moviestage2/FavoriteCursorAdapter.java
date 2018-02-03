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
import android.widget.TextView;

import com.example.android.moviestage2.data.MovieContract;

/**
 * Created by Owner on 9/27/2017.
 */

public class FavoriteCursorAdapter extends CursorAdapter {


    // The default constructor for this CursorAdapter
    public FavoriteCursorAdapter(Context context, Cursor c) { super(context, c, 0);  }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a grid item view using the layout specified in movie_list_items.xml
        return LayoutInflater.from(context).inflate(R.layout.favorite_list_items, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        final int newTitle = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE));

        // Find individual views that we want to modify or use in the list item layout
        TextView txtMovieTitle = (TextView) view.findViewById(R.id.txtMovieTitle);

        // Find the columns of movie attributes that we're interested in
        int titleColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE);

        // Read the movie attributes from the Cursor for the current product
        String titleName = cursor.getString(titleColumnIndex);

        // Update the TextViews and ImageViews with the attributes for the current movie
        txtMovieTitle.setText(titleName);
    }
}