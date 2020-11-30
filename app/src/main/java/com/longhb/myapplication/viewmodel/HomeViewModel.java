package com.longhb.myapplication.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.myapplication.model.Category;
import com.longhb.myapplication.model.ImageDetail;
import com.longhb.myapplication.repository.CategoryRepository;
import com.longhb.myapplication.repository.ImageDetailRepository;
import com.longhb.myapplication.utils.Conts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Category>> listCategoryLiveData;
    private MutableLiveData<List<ImageDetail>> listFeature;
    private MutableLiveData<List<ImageDetail>> listRecent;
    private MutableLiveData<List<ImageDetail>> listRanDom;
    private MutableLiveData<List<ImageDetail>> listPopular;
    private MutableLiveData<List<ImageDetail>> listFavorite;

    private CategoryRepository categoryRepository;
    private ImageDetailRepository imageDetailRepository;

    public HomeViewModel(Application application) {
        listCategoryLiveData = new MutableLiveData<>();
        listFeature = new MutableLiveData<>();
        listRecent = new MutableLiveData<>();
        listRanDom = new MutableLiveData<>();
        listPopular = new MutableLiveData<>();
        listFavorite = new MutableLiveData<>();

        categoryRepository = new CategoryRepository();
        imageDetailRepository = new ImageDetailRepository(application);
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
        imageDetailRepository.getImageDetailOther(action, offset).enqueue(new Callback<List<ImageDetail>>() {
            @Override
            public void onResponse(Call<List<ImageDetail>> call, Response<List<ImageDetail>> response) {
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
            public void onFailure(Call<List<ImageDetail>> call, Throwable t) {
                Log.e("longhb", t.getMessage());
            }
        });
    }


    public MutableLiveData<List<ImageDetail>> getListFeature() {
        return listFeature;
    }

    public void setListFeature(List<ImageDetail> list) {
        listFeature.postValue(list);
    }

    public MutableLiveData<List<ImageDetail>> getListRecent() {
        return listRecent;
    }

    public void setListRecent(List<ImageDetail> list) {
        listRecent.postValue(list);
    }

    public MutableLiveData<List<ImageDetail>> getListRanDom() {
        return listRanDom;
    }

    public void setListRanDom(List<ImageDetail> list) {
        listRanDom.postValue(list);
    }

    public MutableLiveData<List<ImageDetail>> getListPopular() {
        return listPopular;
    }

    public void setListPopular(List<ImageDetail> list) {
        listPopular.postValue(list);
    }

    public MutableLiveData<List<ImageDetail>> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(List<ImageDetail> listFavorite) {
        this.listFavorite.postValue(listFavorite);
    }

    public void getAllFavorite() {
        setListFavorite(imageDetailRepository.getAll());
    }
}
