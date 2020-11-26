package com.longhb.myapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.longhb.myapplication.model.Category;
import com.longhb.myapplication.repository.CategoryRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Category>> listCategoryLiveData;

    private CategoryRepository categoryRepository;

    public HomeViewModel() {
        listCategoryLiveData = new MutableLiveData<>();

        categoryRepository = new CategoryRepository();
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
                    Log.e("longhb", response.body().size() + "");

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("longhb", t.getMessage());
            }
        });
    }


}
