package com.ShakalStudio.shakalgram.MVVM.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ShakalStudio.shakalgram.MVVM.ViewModels.MainViewModel;
import com.ShakalStudio.shakalgram.MVVM.ViewModels.MainViewModelFactory;
import com.ShakalStudio.shakalgram.R;
import com.squareup.picasso.Picasso;

public class FullScreenActivity extends AppCompatActivity {
    private View likeImageView;
    private MainViewModel mainViewModel;
    private int selectedImagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        likeImageView = findViewById(R.id.likeImageView);

        mainViewModel = new ViewModelProvider(this, new MainViewModelFactory())
                .get(MainViewModel.class);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            selectedImagePosition = arguments.getInt("index");

        showMainImage(mainViewModel.getImageURL(selectedImagePosition));
        setLike(mainViewModel.getLike(selectedImagePosition));

        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.setLike(selectedImagePosition);
                setLike(mainViewModel.getLike(selectedImagePosition));
            }
        });
    }

    public void setLike(boolean filled) {
        if (filled)
            Picasso.get().load(R.drawable.filled_heart).into((ImageView) likeImageView);
        else
            Picasso.get().load(R.drawable.unfilled_heart).into((ImageView) likeImageView);
    }

    public void showMainImage(String imageURL) {
        Picasso.get().load(imageURL).into((ImageView) findViewById(R.id.mainImageView));
    }
}