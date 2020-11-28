package com.ShakalStudio.shakalgram;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ImageViewHolder extends RecyclerView.ViewHolder implements ImageHolderView{
    private ImageView _mainImageView;
    private ImageView _likeImageView;

    public ImageViewHolder(View itemView, final MainPresenter mainPresenter) {
        super(itemView);
        _mainImageView = itemView.findViewById(R.id.mainImageView);
        _likeImageView = itemView.findViewById(R.id.likeImageView);

        _likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLike(mainPresenter.TrySetLike(getAdapterPosition()));
            }
        });
    }

    @Override
    public void setMainImage(String imageURL) {
        Picasso.get().load(imageURL).into(_mainImageView);
    }

    @Override
    public void setLike(boolean filled) {
        if (filled)
            Picasso.get().load(R.drawable.filled_heart).into(_likeImageView);
        else
            Picasso.get().load(R.drawable.unfilled_heart).into(_likeImageView);
    }
}
