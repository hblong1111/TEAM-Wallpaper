package com.longhb.myapplication.repository;

import com.longhb.myapplication.model.Category;
import com.longhb.myapplication.model.RetrofitModul;
import com.longhb.myapplication.network.RetrofitService;
import com.longhb.myapplication.utils.Conts;

import java.util.List;

import retrofit2.Call;

public class CategoryRepository {
    private RetrofitService retrofitService;

    public CategoryRepository() {
        this.retrofitService = RetrofitModul.getINSTANCE();
    }

    public Call<List<Category>> getAllCategory() {
        return retrofitService.getAllCategory(Conts.ACTION_GET_CATEGORY);
    }
}
