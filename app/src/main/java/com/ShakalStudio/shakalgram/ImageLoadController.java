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

    public void StartLoad()
    {
        _imagesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) _imagesRecyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == _imageParser.GetImagesURL().size() - 1) {
                    new DownloadImages().execute();
                }
            }
        });

        new DownloadImages().execute();
    }

    public class DownloadImages extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            _imageParser.DownloadNewPageImages();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            _imagesRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}


