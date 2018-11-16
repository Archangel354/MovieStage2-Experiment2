package com.example.android.moviestage2;

/**
 * Created by Owner on 10/14/2017.
 */

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
{
    private ArrayList<MovieList> mMovieList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private String urlImageBaseString = "https://image.tmdb.org/t/p/w185/";
    private String completeUrlString = "";

    public MovieAdapter(MainActivity mainActivity, ArrayList<MovieList> mMovieList)
    {
        //this.mContext = context;
        //this.mInflater = LayoutInflater.from(context);
        this.mMovieList = mMovieList;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public MovieViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgPosterPath);
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //View view = mInflater.inflate(R.layout.movie_list_items, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_items, parent, false);

        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final MovieList listItem = mMovieList.get(position);

        //final LauncherActivity.ListItem listItem = listitems.get(position);

        //holder.imageView.getDrawable()

        // View convertView
        Picasso.with(mContext)
                .load(listItem.getmPosterPath())
                .into(holder.imageView);


    }


    @Override
    public int getItemCount()
    {

        return (mMovieList == null) ? 0 : mMovieList.size();
    }


    public void setMovieList(List<MovieList> movieList)
    {
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }
}
