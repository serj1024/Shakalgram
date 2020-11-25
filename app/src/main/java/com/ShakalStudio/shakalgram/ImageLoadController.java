package com.ShakalStudio.shakalgram;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ImageLoadController {
    RecyclerView _imagesRecyclerView;
    ImageParser _imageParser;

    public ImageLoadController(RecyclerView recyclerView, ImageParser imageParser) {
        _imagesRecyclerView = recyclerView;
        _imageParser = imageParser;
    }
}


