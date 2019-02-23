package com.example.android.moviestage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.moviestage2.RoomData.MovieRecords;

import java.util.List;

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
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        MovieRecords movieRecords = mFavoritesList.get(position);
        String movieId = movieRecords.getMovieid();
        String movietitle = movieRecords.getMovietitle();
        String releasedate = movieRecords.getReleasedate();
        String voteaverage = movieRecords.getVoteaverage();
        String synopsis = movieRecords.getSynopsis();
        String posterpath = movieRecords.getPosterpath();

        //Set values
        holder.favoriteIdView.setText(movieId);
        holder.favoriteTitleView.setText(movietitle);
    }

    @Override
    public int getItemCount() {
        if (mFavoritesList == null) return 0;
        return mFavoritesList.size();
    }

    public class FavoritesViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Variables for favorite movie ID and favorite movie title only.
        TextView favoriteIdView;
        TextView favoriteTitleView;

        public FavoritesViewHolder(View favoriteView) {
            super(favoriteView);
            favoriteIdView = favoriteView.findViewById(R.id.txtMovieID);
            favoriteTitleView = favoriteView.findViewById(R.id.txtMovieTitle);
            favoriteView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mFavoritesList.get(getAdapterPosition()).getId();
            //mFavoriteClickListener.onFavoriteClickListener(elementId);
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
}
