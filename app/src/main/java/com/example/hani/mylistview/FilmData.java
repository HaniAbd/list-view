package com.example.hani.mylistview;

import android.os.Handler;

public class FilmData {

        private FilmsListAdapter movieAdapter;
        private Films movie;
        private String url;
        private Handler handlerUI;



        public FilmData(FilmsListAdapter movieAdapter, Films movie, String url, Handler handlerUI) {
            this.movieAdapter = movieAdapter;
            this.movie = movie;
            this.url = url;
            this.handlerUI = handlerUI;
        }

        public FilmsListAdapter getMovieAdapter() {
            return movieAdapter;
        }

        public void setMovieAdapter(FilmsListAdapter movieAdapter) {
            this.movieAdapter = movieAdapter;
        }

        public Films getMovie() {
            return movie;
        }

        public void setMovie(Films movie) {
            this.movie = movie;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Handler getHandlerUI() {
            return handlerUI;
        }

        public void setHandlerUI(Handler handlerUI) {
            this.handlerUI = handlerUI;
        }

}
