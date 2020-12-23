package com.ShakalStudio.shakalgram.MVVM.DataSources;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Extras;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;

import java.util.ArrayList;

import static com.flickr4java.flickr.photos.SearchParameters.RELEVANCE;

public class FlikrDataSource implements ImageDataSource {
    private String apiKey = "6a57d554703bfffb264f029d63ab81c1";
    private String sharedSecret = "b35d3ec4118c4eea";
    private String searchTile = "car";
    private Integer itemInPage = 15;
    private Integer page = 1;
    private ArrayList<String> downloadImagesURL = new ArrayList();
    private Flickr flickr;
    private Integer startedPage;

    public FlikrDataSource(Integer page) {
        this.page = page;
        startedPage = page;
        flickr = new Flickr(apiKey, sharedSecret, new REST());
    }

    @Override
    public ArrayList<String> getDownloadedImagesURL() {
        return downloadImagesURL;
    }

    @Override
    public void downloadNewPageImages() {
        try {
            downloadImagesURL.clear();
            PhotosInterface photos = flickr.getPhotosInterface();
            SearchParameters params = new SearchParameters();
            params.setMedia("photos");
            params.setText(searchTile);
            params.setSort(RELEVANCE);
            params.setExtras(Extras.ALL_EXTRAS);
            PhotoList<Photo> resultsPhoto = photos.search(params, itemInPage, ++page);
            for (Photo p: resultsPhoto){
                downloadImagesURL.add(p.getMediumUrl());
            }
        } catch (FlickrException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer getStartedPage() {
        return startedPage;
    }

    @Override
    public int getCountItemInPage() {
        return itemInPage;
    }
}