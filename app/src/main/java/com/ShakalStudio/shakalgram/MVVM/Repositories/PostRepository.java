package com.ShakalStudio.shakalgram.MVVM.Repositories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ShakalStudio.shakalgram.MVVM.DataSources.ImageDataSource;
import com.ShakalStudio.shakalgram.MVVM.DataSources.LocalDataBase;
import com.ShakalStudio.shakalgram.MVVM.Models.Post;
import com.ShakalStudio.shakalgram.MVVM.Models.PostType;
import com.ShakalStudio.shakalgram.R;

import java.util.ArrayList;

public class PostRepository {
    private static volatile PostRepository instance;

    private ImageDataSource imageDataSource;
    private LocalDataBase localDataBase;

    public String adImageURL = "https://i.ytimg.com/vi/8B8DV_k5IR0/maxresdefault.jpg";

    private ArrayList<Post> posts = new ArrayList<>();
    private MutableLiveData<ArrayList<Post>> postsLiveData = new MutableLiveData<>();

    public PostRepository(ImageDataSource imageDataSource) {
        this.imageDataSource = imageDataSource;
    }

    public static PostRepository getInstance(ImageDataSource imageDataSource) {
        if (instance == null) {
            instance = new PostRepository(imageDataSource);
        }
        return instance;
    }

    @SuppressLint("StaticFieldLeak")
    public void downloadNewPostImagesAsync() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                imageDataSource.downloadNewPageImages();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                posts.addAll(getDownloadedPosts());
                posts.add(new Post(adImageURL,false, PostType.AD));
                postsLiveData.setValue(posts);
            }

            private ArrayList<Post> getDownloadedPosts() {
                ArrayList<Post> downloadedPosts = new ArrayList<>();
                ArrayList<String> downloadedImagesURL = imageDataSource.getDownloadedImagesURL();

                for (int i = 0; i < downloadedImagesURL.size(); i++) {
                    String imageURL = downloadedImagesURL.get(i);
                    downloadedPosts.add(new Post(imageURL, localDataBase.findLikeToURL(imageURL), PostType.DEFAULT));
                }
                return downloadedPosts;
            }
        }.execute();
    }

    public LiveData<ArrayList<Post>> getPostsLiveData() {
        return postsLiveData;
    }

    public int getPostsCount() {
        return posts.size();
    }

    public void initLocalDataBase(Context baseContext) {
        localDataBase = new LocalDataBase(baseContext);
    }

    public Post getPost(int position) {
        return posts.get(position);
    }

    public void setLike(int imageIndex) {
        String imageURL = getPost(imageIndex).getImageURL();
        getPost(imageIndex).setLike(localDataBase.trySetLike(imageURL));
    }
}