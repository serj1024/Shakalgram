package com.ShakalStudio.shakalgram;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
    private ArrayList<String> _imagesURL;

    public ImagesAdapter(ArrayList<String> imagesURL) {
        _imagesURL = imagesURL;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForImagesList = R.layout.images_list;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForImagesList, parent, false);
        return  new ImageViewHolder(view, new LikeHandler(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(_imagesURL.get(position));
    }

    @Override
    public int getItemCount() {
        return _imagesURL.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView _mainImageView;
        private ImageView _likeImageView;
        private LikeHandler _likeHandler;
        private String _currentUrl;

        public ImageViewHolder(View itemView, LikeHandler likeHandler) {
            super(itemView);
            _likeHandler = likeHandler;
            _mainImageView = itemView.findViewById(R.id.mainImageView);
            _likeImageView = itemView.findViewById(R.id.likeImageView);

            _likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean successLike = _likeHandler.TrySetLike(_currentUrl);
                    if(successLike)
                        Picasso.get().load(R.drawable.filled_heart).into(_likeImageView);
                    else
                        Picasso.get().load(R.drawable.unfilled_heart).into(_likeImageView);
                }
            });
        }

        void bind(String url)
        {
            _currentUrl = _imagesURL.get(getAdapterPosition());
            Picasso.get().load(url).into(_mainImageView);

            if (_likeHandler.FindLikeToURL(_currentUrl))
                Picasso.get().load(R.drawable.filled_heart).into(_likeImageView);
            else
                Picasso.get().load(R.drawable.unfilled_heart).into(_likeImageView);
        }
    }
}
