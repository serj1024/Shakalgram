package com.ShakalStudio.shakalgram;

import android.os.AsyncTask;

import java.util.ArrayList;

public class MainPresenter
{
    private MainActView _mainActView;
    private ImageParser _imageParser;

    public MainPresenter(MainActView mainActView, ImageParser imageParser) {
        _mainActView = mainActView;
        _imageParser = imageParser;
    }

    public void DownloadImages() {
        new DownloadImages(_imageParser, _mainActView).execute();
    }

    public ArrayList<String> GetImagesURL(){
        return _imageParser.GetImagesURL();
    }

    public static class DownloadImages extends AsyncTask<Void, Void, Void> {
        private ImageParser _imageParser;
        private MainActView _mainActView;

        public DownloadImages(ImageParser imageParser, MainActView mainActView) {
            _imageParser = imageParser;
            _mainActView = mainActView;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            _imageParser.DownloadNewPageImages();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            _mainActView.updateData();
        }
    }
}
