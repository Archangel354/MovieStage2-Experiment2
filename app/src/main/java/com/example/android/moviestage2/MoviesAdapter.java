package com.example.android.moviestage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context context;
    private List<MovieList> imageUrls = new ArrayList<>(); // so far so good 9/25/17
    private ArrayList<MovieList> mMovieList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MoviesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            TextView txtPosterView = (TextView) itemView.findViewById(R.id.txtPoster);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imgPosterPath);
            TextView txtTitleView = (TextView) itemView.findViewById(R.id.txtMovieTitle);

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
