package com.ShakalStudio.shakalgram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ImagesAdapter extends RecyclerView.Adapter<ImageViewHolder>{
    private MainPresenter _mainPresenter;

    public ImagesAdapter(MainPresenter mainPresenter) {
        _mainPresenter = mainPresenter;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForImagesList = R.layout.images_list;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForImagesList, parent, false);
        return new ImageViewHolder(view, _mainPresenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        _mainPresenter.onBindImageViewAtPosition(position,holder);
    }

    @Override
    public int getItemCount() {
        return _mainPresenter.getImagesCount();
    }
}