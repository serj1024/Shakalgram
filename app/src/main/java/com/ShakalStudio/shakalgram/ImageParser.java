package com.ShakalStudio.shakalgram;

import java.util.ArrayList;

public interface ImageParser {
    ArrayList<String> GetImagesURL();
    void DownloadNewPageImages();
}
