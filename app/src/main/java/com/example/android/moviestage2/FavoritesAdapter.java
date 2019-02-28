package com.example.android.moviestage2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.moviestage2.RoomData.MovieRecords;

import java.util.List;

import static com.example.android.moviestage2.Utils.movies;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private List<MovieRecords> mFavoritesList;
    private static final String TAG = "FavoritesAdapter";
    private Context mContext;
    // Member variable to handle item clicks
    //final private FavoriteClickListener mFavoriteClickListener;

    public FavoritesAdapter(Context context, List<MovieRecords> movieList) {
        mFavoritesList = movieList;
        mContext = context;
        //mFavoriteClickListener = listener;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.favorite_list_items, parent, false);
        return new FavoritesViewHolder(v);    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, final int position) {
        MovieRecords movieRecords = mFavoritesList.get(position);
        final String movieId = movieRecords.getMovieid();
        final String movietitle = movieRecords.getMovietitle();
        final String releasedate = movieRecords.getReleasedate();
        final String voteaverage = movieRecords.getVoteaverage();
        final String synopsis = movieRecords.getSynopsis();
        final String posterpath = movieRecords.getPosterpath();

        //Set values
        holder.favoriteIdView.setText(movieId);
        holder.favoriteTitleView.setText(movietitle);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, DetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("MBUNDLE_TITLE", movietitle);
                mBundle.putString("MBUNDLE_DATE", releasedate);
                mBundle.putString("MBUNDLE_VOTE", voteaverage);
                mBundle.putString("MBUNDLE_SYNOPSIS", synopsis);
                mBundle.putString("MBUNDLE_POSTER", posterpath);
                mBundle.putString("MBUNDLE_MOVIEID", movieId);
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mFavoritesList == null) return 0;
        return mFavoritesList.size();
    }

    public class FavoritesViewHolder  extends RecyclerView.ViewHolder {

        // Variables for favorite movie ID and favorite movie title only.
        TextView favoriteIdView;
        TextView favoriteTitleView;
        RelativeLayout parentLayout;


        public FavoritesViewHolder(View favoriteView) {
            super(favoriteView);
            favoriteIdView = favoriteView.findViewById(R.id.txtMovieID);
            favoriteTitleView = favoriteView.findViewById(R.id.txtMovieTitle);
            parentLayout = favoriteView.findViewById(R.id.father_layout);
        }
    }

    public void setMovieFavorites(List<MovieRecords> movieData){
        mFavoritesList = movieData;
        notifyDataSetChanged();
    }

    public interface FavoriteClickListener {
        void onFavoriteClickListener(int itemId);
    }

    public final void clear() {
        if (mFavoritesList == null)
        {
            return;
        }
        final int size = mFavoritesList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mFavoritesList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
        else {
            return;
        }
    }

    public List<MovieRecords> getFavorites() {
        return mFavoritesList;
    }
}
