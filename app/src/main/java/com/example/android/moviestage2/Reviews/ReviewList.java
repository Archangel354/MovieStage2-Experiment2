package com.example.android.moviestage2.Reviews;

public class ReviewList {

    // items for a particular review of a movie
    private String rAuthor;
    private String rContent;

    public ReviewList(String Author, String Content) {
        rAuthor = Author;
        rContent = Content;
    }

    public String getrAuthor() {
        return rAuthor;
    }

    public String getrContent() {
        return rContent;
    }
}
