package com.longhb.myapplication.repository;

import com.longhb.myapplication.model.ImageDetailCategory;
import com.longhb.myapplication.model.RetrofitModul;
import com.longhb.myapplication.network.RetrofitService;
import com.longhb.myapplication.utils.Conts;

import java.util.List;

import retrofit2.Call;

public class ListImageCategoryDetailRepository {
    private RetrofitService retrofitService;


    public ListImageCategoryDetailRepository() {
        this.retrofitService = RetrofitModul.getINSTANCE();
    }

    public Call<List<ImageDetailCategory>> getAllCategory(String idCategory,String offset) {
        return retrofitService.getListImageDetail(Conts.ACTION_GET_CATEGORY_DETAIL, idCategory, offset);
    }
}
