package com.ShakalStudio.shakalgram.MVP;

import android.content.Context;

public class ServiceLocator {

    private static ServiceLocator instance = null;
    private ImageParser _imageParser;
    private LikeHandler _likeHandler;
    private int _selectedImagePosition;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized(ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public ImageParser getImageParser() {
        if(_imageParser == null)
            _imageParser = new FlikrParser();
        return _imageParser;
    }

    public LikeHandler getLikeHandler(Context context) {
        if(_likeHandler == null)
            _likeHandler = new LikeHandler(context);
        return _likeHandler;
    }

    public int getSelectedImagePosition(){
        return _selectedImagePosition;
    }
    public void setSelectedImagePosition(int value){
        _selectedImagePosition = value;
    }
}