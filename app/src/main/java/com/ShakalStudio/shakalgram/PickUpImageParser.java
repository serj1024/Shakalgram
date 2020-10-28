package com.ShakalStudio.shakalgram;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PickUpImageParser implements ImageParser {
    private ArrayList<String> _imagesURL = new ArrayList();
    private String _url = "https://pickupimage.com/search.cfm?kw=&id=0&sortby=random&page=";
    private String _urlCurrentPage;
    private int _currentPage = 1;

    public PickUpImageParser()
    {
        _urlCurrentPage = _url.concat(String.valueOf(_currentPage));
    }

    private String GetUrlNextPage()
    {
        _currentPage++;
        return  _url.concat(String.valueOf(_currentPage));
    }

    @Override
    public ArrayList<String> GetImagesURL() {
        return _imagesURL;
    }

    @Override
    public void DownloadNewPageImages() {
        Document doc = null;
        try {
            doc = Jsoup.connect(_urlCurrentPage).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements media = doc.select(".cf-item.gallery-item.fw-col-sm-3");
        for (Element el : media) {
            String imageInfo = el.attr("style");
            String url = imageInfo.substring(22, imageInfo.length() - 2);
            Log.d("DOC", url);
            _imagesURL.add(url);
        }
    }
}
