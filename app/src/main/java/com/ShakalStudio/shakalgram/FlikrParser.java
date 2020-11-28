package com.ShakalStudio.shakalgram;

import android.os.AsyncTask;
import android.util.Log;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Extras;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;

import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;

import static com.flickr4java.flickr.photos.SearchParameters.RELEVANCE;
import static java.lang.Thread.sleep;

public class FlikrParser implements ImageParser {
    private String _apiKey = "6a57d554703bfffb264f029d63ab81c1";
    private String _sharedSecret = "b35d3ec4118c4eea";
    private String _searchTile = "car";
    private Integer _itemInPage = 15;
    private Integer _page = 1;
    private ArrayList<String> _imagesURL = new ArrayList();
    Flickr flickr;
    public FlikrParser() {
        flickr = new Flickr(_apiKey, _sharedSecret, new REST());
    }

    @Override
    public ArrayList<String> GetImagesURL() {
        return _imagesURL;
    }

    @Override
    public void DownloadNewPageImages() {
        try {
            PhotosInterface photos = flickr.getPhotosInterface();
            SearchParameters params = new SearchParameters();
            params.setMedia("photos");
            params.setText(_searchTile);
            params.setSort(RELEVANCE);
            params.setExtras(Extras.ALL_EXTRAS);
            PhotoList<Photo> resultsPhoto = photos.search(params, _itemInPage, ++_page);
            for (Photo p: resultsPhoto){
                _imagesURL.add(p.getSmallUrl());
            }
        } catch (FlickrException e) {
            e.printStackTrace();
        }
    }
}