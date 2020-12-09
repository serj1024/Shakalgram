package com.ShakalStudio.shakalgram.MVVM.Models;

public class Post {
    private String imageURL;
    private boolean isLiked;
    private PostType type;

    public Post(String imageURL, boolean isLiked, PostType type) {
        this.imageURL = imageURL;
        this.isLiked = isLiked;
        this.type = type;
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

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }
}