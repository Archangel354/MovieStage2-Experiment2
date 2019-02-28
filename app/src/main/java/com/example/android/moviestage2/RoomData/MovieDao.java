package com.example.android.moviestage2.RoomData;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movierecords")
    LiveData<List<MovieRecords>> loadAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(MovieRecords movieEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(MovieRecords movieEntry);

    @Delete
    void deleteItem(MovieRecords movieEntry);
    @Query("SELECT * FROM movierecords WHERE movieid = :id")
    LiveData<List<MovieRecords>> loadMovieById(int id);

    @Query("DELETE FROM movierecords")
        public void nukeFavorites ();

}
