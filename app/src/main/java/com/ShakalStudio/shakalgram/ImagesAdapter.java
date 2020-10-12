package com.ShakalStudio.shakalgram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
    ImageParser imageParser;
    public  ImagesAdapter(ImageParser imageParser)
    {
        this.imageParser = imageParser;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForImagesList = R.layout.images_list;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForImagesList, parent, false);
        return  new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        imageParser.CheckToLoadNextPageImages(position);
        holder.bind(imageParser.GetImagesURL().get(position));
    }

    @Override
    public int getItemCount() {
        return imageParser.GetImagesURL().size()+1;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView mainImageView;
        ImageView likeImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mainImageView = itemView.findViewById(R.id.mainImageView);
            likeImageView = itemView.findViewById(R.id.likeImageView);
        }

        void bind(String url)
        {
            Picasso.get().load(url).into(mainImageView);
            Picasso.get().load(R.drawable.unfilled_heart).into(likeImageView);
        }
    }
}
