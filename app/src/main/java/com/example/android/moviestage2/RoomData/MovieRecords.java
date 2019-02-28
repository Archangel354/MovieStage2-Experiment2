package com.example.android.moviestage2.RoomData;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "movierecords",indices =  @Index(value = {"movieid"}, unique = true))
public class MovieRecords {

    @PrimaryKey (autoGenerate = true)
    private int id;
    private String movieid;
    private String movietitle;
    private String releasedate;
    private String voteaverage;
    private String synopsis;
    private String posterpath;




    @Ignore
    public MovieRecords(String movieid, String movietitle, String releasedate, String voteaverage, String synopsis, String posterpath) {
        this.movieid = movieid;
        this.movietitle = movietitle;
        this.releasedate = releasedate;
        this.voteaverage = voteaverage;
        this.synopsis = synopsis;
        this.posterpath = posterpath;
    }

    public MovieRecords(int id, String movieid, String movietitle, String releasedate, String voteaverage, String synopsis, String posterpath) {
        this.id = id;
        this.movieid = movieid;
        this.movietitle = movietitle;
        this.releasedate = releasedate;
        this.voteaverage = voteaverage;
        this.synopsis = synopsis;
        this.posterpath = posterpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieid() {
        return movieid;
    }

    public void setMovieid(String movieid) {
        this.movieid = movieid;
    }

    public String getMovietitle() {
        return movietitle;
    }

    public void setMovietitle(String movietitle) {
        this.movietitle = movietitle;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getVoteaverage() {
        return voteaverage;
    }

    public void setVoteaverage(String voteaverage) {
        this.voteaverage = voteaverage;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }
}

