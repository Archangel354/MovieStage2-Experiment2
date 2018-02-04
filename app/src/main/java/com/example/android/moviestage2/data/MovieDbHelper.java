package com.example.android.moviestage2.data;

/**
 * Created by Owner on 10/14/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = MovieDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "movie.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_MOVIES_TABLE =  "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " ("
                + MovieContract.MovieEntry.MY_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_TITLE + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_IMAGE + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_SYNOPSIS + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " TEXT );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.

    }
}

