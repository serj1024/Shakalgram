package com.ShakalStudio.shakalgram;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity{
    private RecyclerView imagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagesList = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        imagesList.setLayoutManager(layoutManager);
        //imagesList.setAdapter(new ImagesAdapter(new PickUpImageParser()));
        imagesList.setAdapter(new ImagesAdapter(new FlikrParser()));
    }
}