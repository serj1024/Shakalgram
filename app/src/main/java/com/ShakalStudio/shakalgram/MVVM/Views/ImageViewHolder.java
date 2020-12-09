package com.ShakalStudio.shakalgram.MVVM.Views;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.ShakalStudio.shakalgram.MVVM.ViewModels.MainViewModel;
import com.ShakalStudio.shakalgram.R;
import com.squareup.picasso.Picasso;

public class ImageViewHolder extends RecyclerView.ViewHolder implements ImageHolderView {
    private ImageView _mainImageView;
    private ImageView _likeImageView;

    public ImageViewHolder(final View itemView, final MainViewModel mainViewModel) {
        super(itemView);
        _mainImageView = itemView.findViewById(R.id.mainImageView);
        _likeImageView = itemView.findViewById(R.id.likeImageView);

        _mainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.onMainImageClicked(getAdapterPosition());
            }
        });

        _likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getAdapterPosition();
                mainViewModel.onLikeButtonClicked(index);
                setLike(mainViewModel.getLike(index));
            }
        });
    }

    @Override
    public void setMainImage(String imageURL) {
        Picasso.get().load(imageURL).into(_mainImageView);
    }

    @Override
    public void setLike(boolean filled) {
        _likeImageView.setVisibility(View.VISIBLE);
        if (filled)
            Picasso.get().load(R.drawable.filled_heart).into(_likeImageView);
        else
            Picasso.get().load(R.drawable.unfilled_heart).into(_likeImageView);
    }

    @Override
    public void disableLike() {
        _likeImageView.setVisibility(View.GONE);
    }
}
