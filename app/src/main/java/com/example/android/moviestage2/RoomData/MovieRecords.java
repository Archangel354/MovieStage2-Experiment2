package com.example.android.moviestage2.RoomData;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movierecords")
public class MovieRecords {

    @PrimaryKey(autoGenerate = true)
    private int movieid;
    private String movietitle;

    @Ignore
    public MovieRecords(String movietitle) {
        this.movietitle = movietitle;
    }

    public MovieRecords(int movieid, String movietitle) {
        this.movieid = movieid;
        this.movietitle = movietitle;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getMovietitle() {
        return movietitle;
    }

    public void setMovietitle(String movietitle) {
        this.movietitle = movietitle;
    }
}

