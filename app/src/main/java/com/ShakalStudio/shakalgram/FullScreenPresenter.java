package com.ShakalStudio.shakalgram;

public class FullScreenPresenter {
    private ImageParser _imageParser;
    private LikeHandler _likeHandler;
    private FullScreenActView _fullScreenActView;

    public FullScreenPresenter(FullScreenActView fullScreenActView, ImageParser imageParser, LikeHandler likeHandler) {
        _imageParser = imageParser;
        _likeHandler = likeHandler;

        String mainImageURL = getMainImageURL();
        _fullScreenActView = fullScreenActView;
        _fullScreenActView.showMainImage(mainImageURL);
        _fullScreenActView.setLike(_likeHandler.findLikeToURL(mainImageURL));
    }

    public boolean trySetLike(){
        String imageURL = _imageParser.getImagesURL().get(ServiceLocator.getInstance().getSelectedImagePosition());
        return _likeHandler.trySetLike(imageURL);
    }

    public String getMainImageURL() {
        return  _imageParser.getImagesURL().get(ServiceLocator.getInstance().getSelectedImagePosition());
    }
}