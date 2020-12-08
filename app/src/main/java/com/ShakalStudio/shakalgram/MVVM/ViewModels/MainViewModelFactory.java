package com.ShakalStudio.shakalgram.MVVM.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ShakalStudio.shakalgram.MVVM.DataSources.FlikrDataSource;
import com.ShakalStudio.shakalgram.MVVM.Repositories.PostRepository;
import com.ShakalStudio.shakalgram.MVVM.ViewModels.MainViewModel;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(PostRepository.getInstance(new FlikrDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
