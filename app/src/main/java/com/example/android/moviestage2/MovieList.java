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

    public void setmPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public void setmMovieTitle(String mMovieTitle) {
        this.mMovieTitle = mMovieTitle;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public void setmVoteAverage(String mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public void setmSynopsis(String mSynopsis) {
        this.mSynopsis = mSynopsis;
    }

    public void setmMovieID(String mMovieID) {
        this.mMovieID = mMovieID;
    }
}