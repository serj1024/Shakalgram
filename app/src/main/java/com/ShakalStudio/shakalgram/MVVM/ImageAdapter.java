package com.ShakalStudio.shakalgram.MVVM;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ShakalStudio.shakalgram.MVVM.ViewModels.MainViewModel;
import com.ShakalStudio.shakalgram.MVVM.Views.ImageViewHolder;
import com.ShakalStudio.shakalgram.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder>{
    private MainViewModel mainViewModel;

    public ImageAdapter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForImagesList = R.layout.images_list;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForImagesList, parent, false);
        return new ImageViewHolder(view, mainViewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        mainViewModel.onBindImageViewAtPosition(position,holder);
    }

    @Override
    public int getItemCount() {
        return mainViewModel.getPostsCount();
    }
}
