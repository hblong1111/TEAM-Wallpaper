package com.longhb.myapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.repository.ImageDetailRepository;

import java.util.ArrayList;
import java.util.List;

public class ImageDetailViewModel extends ViewModel {
    private List<ImageDetail> listImage = new ArrayList<>();

    private int numCur;
    private ImageDetail imageDetailCur;

    ImageDetailRepository repository;

    public ImageDetailViewModel(Application application) {
        repository = new ImageDetailRepository(application);
    }

    public List<ImageDetail> getListImage() {
        return listImage;
    }

    public void setListImage(List<ImageDetail> listImage) {
        this.listImage.addAll(listImage);
    }

    public int getNumCur() {
        return numCur;
    }

    public void setNumCur(int numCur) {
        this.numCur = numCur;
    }

    public boolean checkImage(int id) {
        if (repository.checkImage(id) == 0) {
            return false;
        }
        return true;
    }

    public ImageDetail getImageDetailCur() {
        return listImage.get(getNumCur());
    }

    public void deleteImage(ImageDetail image){
        repository.delete(image);
    }

    public void insertImage(ImageDetail image){
        repository.insert(image);
    }
}
