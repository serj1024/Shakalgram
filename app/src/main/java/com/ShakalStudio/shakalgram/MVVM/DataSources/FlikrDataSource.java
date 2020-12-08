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
    private String _apiKey = "6a57d554703bfffb264f029d63ab81c1";
    private String _sharedSecret = "b35d3ec4118c4eea";
    private String _searchTile = "car";
    private Integer _itemInPage = 15;
    private Integer _page = 1;
    private ArrayList<String> _downloadImagesURL = new ArrayList();
    Flickr flickr;
    public FlikrDataSource() {
        flickr = new Flickr(_apiKey, _sharedSecret, new REST());
    }

    @Override
    public ArrayList<String> getDownloadedImagesURL() {
        return _downloadImagesURL;
    }

    @Override
    public void downloadNewPageImages() {
        try {
            _downloadImagesURL.clear();
            PhotosInterface photos = flickr.getPhotosInterface();
            SearchParameters params = new SearchParameters();
            params.setMedia("photos");
            params.setText(_searchTile);
            params.setSort(RELEVANCE);
            params.setExtras(Extras.ALL_EXTRAS);
            PhotoList<Photo> resultsPhoto = photos.search(params, _itemInPage, ++_page);
            for (Photo p: resultsPhoto){
                _downloadImagesURL.add(p.getMediumUrl());
            }
        } catch (FlickrException e) {
            e.printStackTrace();
        }
    }
}
