package com.longhb.myapplication.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.longhb.myapplication.viewmodel.HomeViewModel;
import com.longhb.myapplication.viewmodel.ImageDetailViewModel;
import com.longhb.myapplication.viewmodel.ListImageViewModel;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public MyViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)){
            return (T) new HomeViewModel(application);
        }else  if (modelClass.isAssignableFrom(ListImageViewModel.class)){
            return (T) new ListImageViewModel(application);
        }
        return (T) new ImageDetailViewModel(application);
    }
}
