package com.longhb.myapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.myapplication.model.ImageDetailCategory;

import java.util.ArrayList;
import java.util.List;

public class ImageDetailViewModel extends ViewModel {
    private List<ImageDetailCategory> listImage = new ArrayList<>();

    private int numCur;

    public ImageDetailViewModel() {
    }

    public List<ImageDetailCategory> getListImage() {
        return listImage;
    }

    public void setListImage(List<ImageDetailCategory> listImage) {
        this.listImage.addAll(listImage);
    }

    public int getNumCur() {
        return numCur;
    }

    public void setNumCur(int numCur) {
        this.numCur = numCur;
    }
}
