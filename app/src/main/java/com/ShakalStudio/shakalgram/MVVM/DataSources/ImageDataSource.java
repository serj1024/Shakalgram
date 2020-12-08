package com.ShakalStudio.shakalgram.MVVM.DataSources;

import java.util.ArrayList;

public interface ImageDataSource {
    ArrayList<String> getDownloadedImagesURL();
    void downloadNewPageImages();
}

