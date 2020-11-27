package com.longhb.myapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.myapplication.model.ImageDetailCategory;
import com.longhb.myapplication.repository.ImageDetailRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListImageViewModel extends ViewModel {
    private MutableLiveData<List<ImageDetailCategory>> mutableLiveDataListDetail;

    private ImageDetailRepository repository;


    public ListImageViewModel() {
        mutableLiveDataListDetail = new MutableLiveData<>();

        repository = new ImageDetailRepository( );
    }



    public MutableLiveData<List<ImageDetailCategory>> getMutableLiveDataListDetail() {
        return mutableLiveDataListDetail;
    }

    public void setMutableLiveDataListDetail(List<ImageDetailCategory> list) {
        mutableLiveDataListDetail.postValue(list);
    }


    public void getList(String idCategory,String offset) {
        repository.getAllCategory(idCategory,offset).enqueue(new Callback<List<ImageDetailCategory>>() {
            @Override
            public void onResponse(Call<List<ImageDetailCategory>> call, Response<List<ImageDetailCategory>> response) {
                setMutableLiveDataListDetail(response.body());
            }

            @Override
            public void onFailure(Call<List<ImageDetailCategory>> call, Throwable t) {
                Log.e("longhb", t.getMessage());
            }
        });
    }
}
