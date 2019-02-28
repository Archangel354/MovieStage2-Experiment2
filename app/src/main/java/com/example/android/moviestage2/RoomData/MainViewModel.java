package com.example.android.moviestage2.RoomData;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<MovieRecords>> favorites;


    public MainViewModel(Application application) {
        super(application);
        MoviesDatabase database = MoviesDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the items from the DataBase");
        favorites = database.movieDao().loadAllMovies();
    }

    public LiveData<List<MovieRecords>> getFavorites() {
        return favorites;
    }

}