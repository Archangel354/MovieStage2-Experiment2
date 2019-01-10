package com.example.android.moviestage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context mContext;
    private List<MovieList> imageUrls = new ArrayList<>(); // so far so good 9/25/17
    private ArrayList<MovieList> mMovieList;
    private OnItemClickListener mListener;

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
    public void onBindViewHolder(MoviesAdapter.MoviesViewHolder holder, int position) {
        MovieList currentItem = mMovieList.get(position);

        String imageUrl = currentItem.getmPosterPath();
        String creatorName = currentItem.getmMovieTitle();

        holder.mTextViewCreator.setText(creatorName);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;

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
}
