package com.ShakalStudio.shakalgram.MVVM.Models;

public class Post {
    private String imageURL;
    private boolean isLiked;

    public Post(String imageURL, boolean isLiked) {
        this.imageURL = imageURL;
        this.isLiked = isLiked;
    }

    public String getImageURL() {
        return imageURL;
    }

    public boolean getLike() {
        return isLiked;
    }
    public void setLike(boolean isLiked) {
        this.isLiked = isLiked;
    }
}