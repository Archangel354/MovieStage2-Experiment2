package com.example.android.moviestage2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.moviestage2.Utils.movies;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<MovieList> imageUrls = new ArrayList<>(); // so far so good 9/25/17
    public static List<MovieList> mMovieList;
    private static final String TAG = "MoviesAdapter";
    private Context mContext;



    public MoviesAdapter(Context context, ArrayList<MovieList> movieList) {
        mMovieList = movieList;
        mContext = context;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.movie_list_items, parent, false);
        return new MoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        final MovieList currentItem = mMovieList.get(position);
        final String imageUrl = currentItem.getmPosterPath();

        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: clicked on: " + imageUrls.get(position));
                //Toast.makeText(mContext, (CharSequence) imageUrls.get(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, "onClick", Toast.LENGTH_SHORT).show();

                Intent mIntent = new Intent(mContext, DetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("MBUNDLE_TITLE", movies.get(position).getmMovieTitle());
                mBundle.putString("MBUNDLE_DATE", movies.get(position).getmReleaseDate());
                mBundle.putString("MBUNDLE_VOTE", movies.get(position).getmVoteAverage());
                mBundle.putString("MBUNDLE_SYNOPSIS", movies.get(position).getmSynopsis());
                mBundle.putString("MBUNDLE_POSTER", movies.get(position).getmPosterPath());
                mBundle.putString("MBUNDLE_MOVIEID", movies.get(position).getmMovieID());
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mMovieList == null) return 0;
        return mMovieList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        RelativeLayout parentLayout;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imgPosterPath);
            mTextViewCreator = itemView.findViewById(R.id.txtMovieTitle);
            parentLayout = itemView.findViewById(R.id.mother_layout);
        }
    }

    public void setMovieData(List<MovieList> movieData){
        mMovieList = movieData;
        notifyDataSetChanged();
    }


}
