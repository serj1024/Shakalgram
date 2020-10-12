package com.ShakalStudio.shakalgram;

import java.util.ArrayList;

public interface ImageParser {
    public ArrayList<String> GetImagesURL();
    public void CheckToLoadNextPageImages(int position);
}
