package com.example.android.moviestage2;

/**
 * Created by Owner on 10/14/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
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

public class MovieAdapter extends ArrayAdapter<MovieList> {

    

    private Context context;
    private LayoutInflater inflater;
    private String urlImageBaseString = "https://image.tmdb.org/t/p/w185/";
    private String completeUrlString = "";
    private List<MovieList> imageUrls = new ArrayList<>(); // so far so good 9/25/17


    public MovieAdapter(Activity context, ArrayList<MovieList> movieListRecords) {
        super(context,R.layout.movie_list_items,  movieListRecords);

        this.context = context;
        this.imageUrls = movieListRecords;
        inflater = LayoutInflater.from(context);
    }

    // so far so good 9/25/17
    public void UpdateMovies(List<MovieList> newList){
        this.imageUrls = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        //View listItemView = convertView;
        if (convertView == null) {
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_items, parent, false);
            convertView = inflater.inflate(R.layout.movie_list_items, parent, false);
        }

        // Get the MovieRecord jpg object located at this "position" in the list
        MovieList currentMovie = getItem(position);

        TextView txtPosterView = (TextView) convertView.findViewById(R.id.txtPoster);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgPosterPath);
        TextView txtTitleView = (TextView) convertView.findViewById(R.id.txtMovieTitle);
        imageView.setAdjustViewBounds(true);

        // Find the TextView in the movie_list_items.xml layout with the ID txtPosterView
        // Get the jpg string from the current MovieRecord object and
        // set this text on the title TextView
        // and also get the title of the movie.
        txtPosterView.setText(String.valueOf(currentMovie.getmPosterPath()));
        txtTitleView.setText(String.valueOf(currentMovie.getmMovieTitle()));

        completeUrlString = txtPosterView.getText().toString();
        Log.i("LOG.MovieAdapter","The completeUrlString is: " + completeUrlString);

        Picasso
                .with(context)
                .load(completeUrlString)
                .fit()
                .into(imageView);

        Log.i("LOG getView ","position: " + position);

        return convertView;
    }

    @Override
    public void clear() {
        super.clear();
    }


}
