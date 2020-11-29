package com.ShakalStudio.shakalgram;

import android.content.Context;
import android.os.AsyncTask;

public class MainPresenter
{
    private MainActView _mainActView;
    private ImageParser _imageParser;
    private LikeHandler _likeHandler;
    private AdManager _adManager;

    public MainPresenter(MainActView mainActView, ImageParser imageParser, LikeHandler likeHandler, AdManager adManager) {
        _mainActView = mainActView;
        _imageParser = imageParser;
        _likeHandler = likeHandler;
        _adManager = adManager;
    }

    public void DownloadImages() {
        new DownloadImagesAsync(_imageParser, _mainActView, _adManager).execute();
    }

    public void onBindImageViewAtPosition(int position, ImageHolderView imageHolderView) {
        String imageURL = _imageParser.GetImagesURL().get(position);
        imageHolderView.setMainImage(imageURL);
        imageHolderView.setLike(_likeHandler.FindLikeToURL(imageURL));
        if(imageURL == _adManager.AdImageURL)
            imageHolderView.disableLike();
    }

    public boolean TrySetLike(int imageIndex){
        String imageURL = _imageParser.GetImagesURL().get(imageIndex);
        return _likeHandler.TrySetLike(imageURL);
    }

    public int getImagesCount(){
        return _imageParser.GetImagesURL().size();
    }

    public void onMainImageClicked(int imagePosition, Context context) {
        if(_imageParser.GetImagesURL().get(imagePosition) == _adManager.AdImageURL)
            _adManager.ShowAdApp(context);
    }
}

class DownloadImagesAsync extends AsyncTask<Void, Void, Void> {
    private ImageParser _imageParser;
    private MainActView _mainActView;
    private AdManager _adManager;

    public DownloadImagesAsync(ImageParser imageParser, MainActView mainActView, AdManager adManager) {
        _imageParser = imageParser;
        _mainActView = mainActView;
        _adManager = adManager;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        _imageParser.DownloadNewPageImages();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        _imageParser.GetImagesURL().add(_adManager.AdImageURL);
        _mainActView.updateData();
    }
}