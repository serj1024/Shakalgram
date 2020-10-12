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

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
    private ImageParser _imageParser;
    public  ImagesAdapter(ImageParser imageParser)
    {
        _imageParser = imageParser;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForImagesList = R.layout.images_list;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForImagesList, parent, false);
        return  new ImageViewHolder(view, new LikeController(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        _imageParser.CheckToLoadNextPageImages(position);
        holder.bind(_imageParser.GetImagesURL().get(position));
    }

    @Override
    public int getItemCount() {
        return _imageParser.GetImagesURL().size()+1;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView _mainImageView;
        private ImageView _likeImageView;
        private LikeController _likeController;
        private int _currentIndex;
        private String _currentUrl;

        public ImageViewHolder(View itemView, LikeController likeController) {
            super(itemView);
            _likeController = likeController;
            _mainImageView = itemView.findViewById(R.id.mainImageView);
            _likeImageView = itemView.findViewById(R.id.likeImageView);

            _likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetLike();
                }

                private void SetLike() {
                    Log.d("LIKE", _currentUrl);
                    _likeController.Insert(_currentUrl);
                    Picasso.get().load(R.drawable.filled_heart).into(_likeImageView);
                }
            });
        }

        void bind(String url)
        {
            _currentIndex = getAdapterPosition();
            _currentUrl = _imageParser.GetImagesURL().get(_currentIndex);
            Picasso.get().load(url).into(_mainImageView);

            if (_likeController.FindLikeToURL(_currentUrl))
                Picasso.get().load(R.drawable.filled_heart).into(_likeImageView);
            else
                Picasso.get().load(R.drawable.unfilled_heart).into(_likeImageView);
        }
    }
}
