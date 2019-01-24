package com.example.android.moviestage2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.moviestage2.Utils.movies;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context mContext;
    private List<MovieList> imageUrls = new ArrayList<>(); // so far so good 9/25/17
    public static List<MovieList> mMovieList;
    private OnItemClickListener mListener;
    private static final String TAG = "RecyclerViewAdapter";


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public MoviesAdapter(Context context, ArrayList<MovieList> movieList) {
        mContext = context;
        mMovieList = movieList;
    }

    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.movie_list_items, parent, false);
        return new MoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MoviesViewHolder holder, final int position) {
        final MovieList currentItem = mMovieList.get(position);

        String imageUrl = currentItem.getmPosterPath();
        String creatorName = currentItem.getmMovieTitle();

        holder.mTextViewCreator.setText(creatorName);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mMovieList.get(position));

                Toast.makeText(mContext, (CharSequence) mMovieList.get(position), Toast.LENGTH_SHORT).show();;

                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("MBUNDLE_TITLE", movies.get(position).getmMovieTitle());
                mBundle.putString("MBUNDLE_DATE", movies.get(position).getmReleaseDate());
                mBundle.putString("MBUNDLE_VOTE", movies.get(position).getmVoteAverage());
                mBundle.putString("MBUNDLE_SYNOPSIS", movies.get(position).getmSynopsis());
                mBundle.putString("MBUNDLE_POSTER", movies.get(position).getmPosterPath());
                mBundle.putString("MBUNDLE_MOVIEID", movies.get(position).getmMovieID());
                intent.putExtras(mBundle);
                mContext.startActivity(intent);
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
            //ImageView imageView = (ImageView) itemView.findViewById(R.id.imgPosterPath);
            mTextViewCreator = itemView.findViewById(R.id.txtMovieTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }


    }

    public void setMovieData(List<MovieList> movieData){
        mMovieList = movieData;
        notifyDataSetChanged();
    }
}