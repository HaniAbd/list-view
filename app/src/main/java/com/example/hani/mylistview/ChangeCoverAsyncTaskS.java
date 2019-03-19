package com.example.hani.mylistview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChangeCoverAsyncTaskS extends AsyncTask<String, Void, Bitmap> {

    private WeakReference<FilmsListAdapter> wMAdapter ;
    private Films movie;


    public ChangeCoverAsyncTaskS(FilmsListAdapter movieAdapter, Films movie) {
        this.wMAdapter = new WeakReference<FilmsListAdapter>(movieAdapter);
        this.movie = movie;
    }


    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            return downloadFromURL(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        movie.setImage(bitmap);

        FilmsListAdapter movieAdapter = wMAdapter.get();

        if(movieAdapter != null){
            movieAdapter.notifyDataSetChanged();
        }

    }


    public Bitmap downloadFromURL(String url) throws IOException {
        URL urlImage = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlImage.openConnection();
        BufferedInputStream  bufferedInputStream= new  BufferedInputStream(httpURLConnection.getInputStream());
        return BitmapFactory.decodeStream(bufferedInputStream);
    }

}
