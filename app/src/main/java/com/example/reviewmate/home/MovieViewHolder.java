package com.example.reviewmate.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewmate.R;
import com.example.reviewmate.databinding.MovieDetailsBinding;
import com.example.reviewmate.model.Movie;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    private final MovieDetailsBinding binding;

    public MovieViewHolder(@NonNull MovieDetailsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void updateMovie(Movie movie) {
        binding.titleTextView.setText(movie.getTitle());
        binding.ratingTextView.setText("IMDb Rating: " + movie.getImdbRating());

        if (movie.getPosterUrl() != null && !movie.getPosterUrl().isEmpty()) {
            new ImageLoadTask(movie.getPosterUrl(), binding.posterImageView).execute();
        } else {
            binding.posterImageView.setImageResource(R.drawable.ic_default_poster); // Placeholder if URL is null or empty
        }
    }

    public void bind(Movie movie, MovieRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        itemView.setOnClickListener(v -> onItemClickListener.onItemClick(movie));
    }

    // Custom AsyncTask to load images from URL
    private static class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            } else {
                imageView.setImageResource(R.drawable.ic_default_poster);
            }
        }
    }
}
