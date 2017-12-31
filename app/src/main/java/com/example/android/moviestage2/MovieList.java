package com.example.android.moviestage2;

/**
 * Created by Owner on 10/14/2017.
 */

public class MovieList {

    // items for a particular movie
    private String mPosterPath;
    private String mMovieTitle;
    private String mReleaseDate;
    private String mVoteAverage;
    private String mSynopsis;
    private String mMovieID;


    public MovieList(String PosterPath, String MovieTitle, String ReleaseDate, String VoteAverage, String Synopsis, String MovieID) {
        mPosterPath = PosterPath;
        mMovieTitle = MovieTitle;
        mReleaseDate = ReleaseDate;
        mVoteAverage = VoteAverage;
        mSynopsis = Synopsis;
        mMovieID = MovieID;
    }


    public String getmPosterPath() {
        return mPosterPath;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmVoteAverage() {
        return mVoteAverage;
    }

    public String getmSynopsis() {
        return mSynopsis;
    }

    public String getmMovieID() {
        return mMovieID;
    }
}