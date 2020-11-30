package com.longhb.myapplication.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.repository.ImageDetailRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListImageViewModel extends ViewModel {
    private MutableLiveData<List<ImageDetail>> mutableLiveDataListDetail;

    private ImageDetailRepository repository;


    public ListImageViewModel(Application application) {
        mutableLiveDataListDetail = new MutableLiveData<>();

        repository = new ImageDetailRepository(application );
    }



    public MutableLiveData<List<ImageDetail>> getMutableLiveDataListDetail() {
        return mutableLiveDataListDetail;
    }

    public void setMutableLiveDataListDetail(List<ImageDetail> list) {
        mutableLiveDataListDetail.postValue(list);
    }


    public void getList(String idCategory,String offset) {
        repository.getAllCategory(idCategory,offset).enqueue(new Callback<List<ImageDetail>>() {
            @Override
            public void onResponse(Call<List<ImageDetail>> call, Response<List<ImageDetail>> response) {
                setMutableLiveDataListDetail(response.body());
            }

            @Override
            public void onFailure(Call<List<ImageDetail>> call, Throwable t) {
                Log.e("longhb", t.getMessage());
            }
        });
    }
}
