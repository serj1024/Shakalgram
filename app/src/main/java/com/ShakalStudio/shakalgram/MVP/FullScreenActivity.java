package com.ShakalStudio.shakalgram.MVP;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ShakalStudio.shakalgram.R;
import com.squareup.picasso.Picasso;

public class FullScreenActivity extends AppCompatActivity implements FullScreenActView {
    View _likeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        _likeImageView = findViewById(R.id.likeImageView);

        final FullScreenPresenter fullScreenPresenter = new FullScreenPresenter( this,
                ServiceLocator.getInstance().getImageParser(),
                ServiceLocator.getInstance().getLikeHandler(this.getApplicationContext()));

        _likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLike(fullScreenPresenter.trySetLike());
            }
        });
    }

    @Override
    public void setLike(boolean filled) {
        if (filled)
            Picasso.get().load(R.drawable.filled_heart).into((ImageView) _likeImageView);
        else
            Picasso.get().load(R.drawable.unfilled_heart).into((ImageView) _likeImageView);
    }

    @Override
    public void showMainImage(String imageURL) {
        Picasso.get().load(imageURL).into((ImageView) findViewById(R.id.mainImageView));
    }
}