package com.ShakalStudio.shakalgram.MVP;

import java.util.ArrayList;

public interface ImageParser {
    ArrayList<String> getImagesURL();
    void downloadNewPageImages();
}
