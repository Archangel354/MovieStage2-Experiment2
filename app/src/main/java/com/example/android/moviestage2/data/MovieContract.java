package com.example.android.moviestage2.data;

/**
 * Created by Owner on 10/14/2017.
 */

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public  class MovieContract {
    // To prevent someone from accidentally instatiating the contract class,
    // give it an empty constructor.
    private MovieContract() {

    }

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.moviestage";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/moviesstage1/ is a valid path for
     * looking at movie data. */

    public static final String PATH_MOVIES = "movies";

    /**
     * Inner class that defines constant values for the moview database table.
     * Each entry in the table represents a single movie.
     */
    public static final class MovieEntry implements BaseColumns {

        /**
         * The content URI to access the inventory data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of products.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        /**
         * Name of database table for inventory
         */
        public final static String TABLE_NAME = "movies";


        public final static String MY_MOVIE_ID = BaseColumns._ID;
        public final static String COLUMN_MOVIE_TITLE ="title";
        public final static String COLUMN_MOVIE_RELEASE_DATE ="releasedate";
        public final static String COLUMN_MOVIE_POSTER_IMAGE ="image";
        public final static String COLUMN_MOVIE_VOTE_AVERAGE ="vote";
        public final static String COLUMN_MOVIE_SYNOPSIS ="synopsis";
        public final static String COLUMN_MOVIE_ID ="movieID";

    }
}