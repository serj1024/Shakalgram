package com.ShakalStudio.shakalgram;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements MainActView{
    private RecyclerView _imagesRecyclerView;
    private SwipeRefreshLayout _swipeRefreshLayout;
    private MainPresenter _mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _mainPresenter = new MainPresenter(this, new FlikrParser(), new LikeHandler(this.getApplicationContext()));
        _swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        _imagesRecyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        _imagesRecyclerView.setLayoutManager(layoutManager);
        _imagesRecyclerView.setAdapter(new ImagesAdapter(_mainPresenter));

        _imagesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) _imagesRecyclerView.getLayoutManager();
                int lastImagePosition = _mainPresenter.getImagesCount()-1;
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == lastImagePosition) {
                    _mainPresenter.DownloadImages();
                    _swipeRefreshLayout.setRefreshing(true);
                }
            }
        });

        _mainPresenter.DownloadImages();
    }

    @Override
    public void updateData() {
        _imagesRecyclerView.getAdapter().notifyDataSetChanged();
        _swipeRefreshLayout.setRefreshing(false);
    }
}