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

import java.util.ArrayList;

public class PostRepository {
    private static volatile PostRepository instance;

    private ImageDataSource imageDataSource;
    private LocalDataBase localDataBase;
    private int countAdPost = 0;

    public String adImageURL = "https://i.ytimg.com/vi/8B8DV_k5IR0/maxresdefault.jpg";

    private ArrayList<Post> posts = new ArrayList<>();
    private MutableLiveData<ArrayList<Post>> postsLiveData = new MutableLiveData<>();
    private AsyncTask<Void, Void, Void> downloadAsyncTask;

    public PostRepository(ImageDataSource imageDataSource, Context context) {
        this.imageDataSource = imageDataSource;
        localDataBase = new LocalDataBase(context);
    }

    public static PostRepository getInstance(ImageDataSource imageDataSource, Context context) {
        if (instance == null) {
            instance = new PostRepository(imageDataSource, context);
        }
        return instance;
    }

    @SuppressLint("StaticFieldLeak")
    public void downloadNewPostImagesAsync() {
        if (downloadAsyncTask == null || downloadAsyncTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            downloadAsyncTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    imageDataSource.downloadNewPageImages();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    posts.addAll(getDownloadedPosts());
                    posts.add(new Post(adImageURL, false, PostType.AD));
                    ++countAdPost;
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
    }

    public LiveData<ArrayList<Post>> getPostsLiveData() {
        return postsLiveData;
    }

    public int getPostsCount() {
        return posts.size();
    }

    public Post getPost(int position) {
        return posts.get(position);
    }

    public void setLike(int imageIndex) {
        String imageURL = getPost(imageIndex).getImageURL();
        getPost(imageIndex).setLike(localDataBase.trySetLike(imageURL));
    }

    public int getCountAdPost() {
        return countAdPost;
    }

    public int getCountImageInPage() {
        return imageDataSource.getCountItemInPage();
    }
}