package com.longhb.myapplication.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.repository.ImageDetailRepository;
import com.longhb.myapplication.utils.Conts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private ImageDetailRepository repository;

    private MutableLiveData<List<ImageDetail>> mutableLiveDataListImage=new MutableLiveData<>();

    public SearchViewModel(Application application) {
        this.repository = new ImageDetailRepository(application);
    }

    public MutableLiveData<List<ImageDetail>> getMutableLiveDataListImage() {
        return mutableLiveDataListImage;
    }

    public void setMutableLiveDataListImage(List<ImageDetail> list) {
        this.mutableLiveDataListImage.postValue(list);
    }

    public void searchImage(String text, String offset){
        repository.searchImage(Conts.ACTION_SEARCH,text,offset).enqueue(new Callback<List<ImageDetail>>() {
            @Override
            public void onResponse(Call<List<ImageDetail>> call, Response<List<ImageDetail>> response) {
                if (response.isSuccessful()){
                    setMutableLiveDataListImage(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ImageDetail>> call, Throwable t) {
                Log.e("longhbb", "SearchViewModel | onFailure: ",t);
            }
        });
    }


}
