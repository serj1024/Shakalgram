package com.ShakalStudio.shakalgram.MVVM.ViewModels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ShakalStudio.shakalgram.MVVM.DataSources.FlikrDataSource;
import com.ShakalStudio.shakalgram.MVVM.DataSources.ImageDataSource;
import com.ShakalStudio.shakalgram.MVVM.Models.PostType;
import com.ShakalStudio.shakalgram.MVVM.Views.ImageHolderView;
import com.ShakalStudio.shakalgram.MVVM.Models.Post;
import com.ShakalStudio.shakalgram.MVVM.Repositories.PostRepository;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<Integer> OnMainImageClicked = new MutableLiveData<>();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private PostRepository postRepository;
    private ImageDataSource imageDataSource;

    public MainViewModel(@NonNull Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences("preference_key", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        imageDataSource = new FlikrDataSource(getSavedPageIndex());
        this.postRepository = PostRepository.getInstance(imageDataSource, application.getBaseContext());
    }

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

    private int getSavedPageIndex(){
        return sharedPreferences.getInt("CurrentPageIndex", 1);
    }

    public int getCurrentImageIndexInPag(){
        return sharedPreferences.getInt("CurrentImageIndexInPage", 0);
    }

    public int getStartedPage() {
        return imageDataSource.getStartedPage();
    }

    public void saveCurrentStateInfo(int firstCompletelyVisibleItemPosition) {
        int countLoadedPage = firstCompletelyVisibleItemPosition / (postRepository.getCountImageInPage()+1);
        int currentPageIndex = getStartedPage() + countLoadedPage;
        int currentImageIndexInPage = firstCompletelyVisibleItemPosition - countLoadedPage*(postRepository.getCountImageInPage()+1);

        saveIntValue("CurrentPageIndex", currentPageIndex);
        saveIntValue("CurrentImageIndexInPage", currentImageIndexInPage);
    }

    private void saveIntValue(String key, int value){
        editor.putInt(key, value);
        editor.apply();
    }
}