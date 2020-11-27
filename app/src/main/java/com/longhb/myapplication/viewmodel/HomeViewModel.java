package com.longhb.myapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.myapplication.model.Category;
import com.longhb.myapplication.model.ImageDetailCategory;
import com.longhb.myapplication.repository.CategoryRepository;
import com.longhb.myapplication.repository.ImageDetailRepository;
import com.longhb.myapplication.utils.Conts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Category>> listCategoryLiveData;
    private MutableLiveData<List<ImageDetailCategory>> listFeature;
    private MutableLiveData<List<ImageDetailCategory>> listRecent;
    private MutableLiveData<List<ImageDetailCategory>> listRanDom;
    private MutableLiveData<List<ImageDetailCategory>> listPopular;

    private CategoryRepository categoryRepository;
    private ImageDetailRepository imageDetailRepository;

    public HomeViewModel() {
        listCategoryLiveData = new MutableLiveData<>();
        listFeature = new MutableLiveData<>();
        listRecent = new MutableLiveData<>();
        listRanDom = new MutableLiveData<>();
        listPopular = new MutableLiveData<>();

        categoryRepository = new CategoryRepository();
        imageDetailRepository = new ImageDetailRepository();
    }

    public MutableLiveData<List<Category>> getListCategoryLiveData() {
        return listCategoryLiveData;
    }

    public void setListCategoryLiveData(List<Category> list) {
        listCategoryLiveData.postValue(list);
    }

    public void getAllCategory() {
        categoryRepository.getAllCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    setListCategoryLiveData(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("longhb", t.getMessage());
            }
        });
    }

    public void getImageDetailOther(String action, String offset) {
        imageDetailRepository.getImageDetailOther(action, offset).enqueue(new Callback<List<ImageDetailCategory>>() {
            @Override
            public void onResponse(Call<List<ImageDetailCategory>> call, Response<List<ImageDetailCategory>> response) {
                if (action.equals(Conts.ACTION_GET_FEATURED)) {
                    setListFeature(response.body());
                } else if (action.equals(Conts.ACTION_GET_RECENT)) {
                    setListRecent(response.body());
                } else if (action.equals(Conts.ACTION_GET_RANDOM)) {
                    setListRanDom(response.body());
                } else if (action.equals(Conts.ACTION_GET_POPULAR)) {
                    setListPopular(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ImageDetailCategory>> call, Throwable t) {
                Log.e("longhb", t.getMessage());
            }
        });
    }


    public MutableLiveData<List<ImageDetailCategory>> getListFeature() {
        return listFeature;
    }

    public void setListFeature(List<ImageDetailCategory> list) {
        listFeature.postValue(list);
    }

    public MutableLiveData<List<ImageDetailCategory>> getListRecent() {
        return listRecent;
    }

    public void setListRecent(List<ImageDetailCategory> list) {
        listRecent.postValue(list);
    }

    public MutableLiveData<List<ImageDetailCategory>> getListRanDom() {
        return listRanDom;
    }

    public void setListRanDom(List<ImageDetailCategory> list) {
        listRanDom.postValue(list);
    }

    public MutableLiveData<List<ImageDetailCategory>> getListPopular() {
        return listPopular;
    }

    public void setListPopular(List<ImageDetailCategory> list) {
        listPopular.postValue(list);
    }
}
