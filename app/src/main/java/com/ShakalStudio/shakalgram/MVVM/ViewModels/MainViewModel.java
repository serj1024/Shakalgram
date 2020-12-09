package com.ShakalStudio.shakalgram.MVVM.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ShakalStudio.shakalgram.MVVM.Models.PostType;
import com.ShakalStudio.shakalgram.MVVM.Views.ImageHolderView;
import com.ShakalStudio.shakalgram.MVVM.Models.Post;
import com.ShakalStudio.shakalgram.MVVM.Repositories.PostRepository;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    public MutableLiveData<Integer> OnMainImageClicked = new MutableLiveData<>();
    public MainViewModel(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private PostRepository postRepository;

    public void downloadImagesAsync() {
        postRepository.downloadNewPostImagesAsync();
    }

    public int getPostsCount() {
        return postRepository.getPostsCount();
    }

    public void onBindImageViewAtPosition(int position, ImageHolderView imageHolderView) {
        String imageURL = postRepository.getPost(position).getImageURL();
        imageHolderView.setMainImage(imageURL);

        switch (getCurrentPostType(position)) {
            case AD:
                imageHolderView.disableLike();
                break;
            case DEFAULT:
                imageHolderView.setLike(postRepository.getPost(position).getLike());
                break;
        }
    }

    public LiveData<ArrayList<Post>> getPostsLiveData() {
        return postRepository.getPostsLiveData();
    }

    public void setContext(Context baseContext) {
        postRepository.initLocalDataBase(baseContext);
    }

    public void onMainImageClicked(Integer indexImage) {
        OnMainImageClicked.setValue(indexImage);
    }

    public void onLikeButtonClicked(int index) {
        postRepository.setLike(index);
    }

    public boolean getLike(int index){
        return postRepository.getPost(index).getLike();
    }

    public String getImageURL(int index){
        return postRepository.getPost(index).getImageURL();
    }

    public PostType getCurrentPostType(int indexMainImage){
        return postRepository.getPost(indexMainImage).getType();
    }
}