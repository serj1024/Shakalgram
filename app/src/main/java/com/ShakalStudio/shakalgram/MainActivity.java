package com.ShakalStudio.shakalgram;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity{
    private RecyclerView _imagesRecyclerView;
    ImageParser _imageParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _imagesRecyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        _imagesRecyclerView.setLayoutManager(layoutManager);
//        _imageParser = new PickUpImageParser();
        _imageParser = new FlikrParser();
        _imagesRecyclerView.setAdapter(new ImagesAdapter(_imageParser));
        new ImageLoadController(_imagesRecyclerView, _imageParser).StartLoad();
    }
}