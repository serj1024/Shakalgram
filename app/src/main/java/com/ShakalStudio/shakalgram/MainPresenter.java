package com.ShakalStudio.shakalgram;

import android.os.AsyncTask;

public class MainPresenter
{
    private MainActView _mainActView;
    private ImageParser _imageParser;
    private LikeHandler _likeHandler;

    public MainPresenter(MainActView mainActView, ImageParser imageParser, LikeHandler likeHandler) {
        _mainActView = mainActView;
        _imageParser = imageParser;
        _likeHandler = likeHandler;
    }

    public void DownloadImages() {
        new DownloadImagesAsync(_imageParser, _mainActView).execute();
    }

    public void onBindImageViewAtPosition(int position, ImageHolderView imageHolderView) {
        String imageURL = _imageParser.GetImagesURL().get(position);
        imageHolderView.setMainImage(imageURL);
        imageHolderView.setLike(_likeHandler.FindLikeToURL(imageURL));
    }

    public boolean TrySetLike(int imageIndex){
        String imageURL = _imageParser.GetImagesURL().get(imageIndex);
        return _likeHandler.TrySetLike(imageURL);
    }

    public int getImagesCount(){
        return _imageParser.GetImagesURL().size();
    }
}

class DownloadImagesAsync extends AsyncTask<Void, Void, Void> {
    private ImageParser _imageParser;
    private MainActView _mainActView;

    public DownloadImagesAsync(ImageParser imageParser, MainActView mainActView) {
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